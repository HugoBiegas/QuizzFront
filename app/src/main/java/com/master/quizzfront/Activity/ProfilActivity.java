package com.master.quizzfront.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.master.quizzfront.Api.ApiClient;
import com.master.quizzfront.Api.UtilisateurApi;
import com.master.quizzfront.Enum.UtilisateurStatut;
import com.master.quizzfront.Models.Utilisateur;
import com.master.quizzfront.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;
import org.json.JSONObject;
import okhttp3.ResponseBody;

public class ProfilActivity extends BaseToolbarActivity {
    private EditText champNom;
    private EditText champPrenom;
    private EditText champEmail;
    private TextView textViewStatut;
    private Button buttonSauvegarder;
    private Utilisateur utilisateur;
    private Button buttonRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        setupToolbar();

        // Récupérer l'utilisateur depuis l'intent
        utilisateur = (Utilisateur) getIntent().getSerializableExtra("utilisateur");
        if (utilisateur == null) {
            Toast.makeText(this, "Erreur: Utilisateur non trouvé", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initialiserVues();
        remplirChamps();
        configurerEcouteurs();
    }

    private void initialiserVues() {
        champNom = findViewById(R.id.editTextNom);
        champPrenom = findViewById(R.id.editTextPrenom);
        champEmail = findViewById(R.id.editTextEmail);
        textViewStatut = findViewById(R.id.textViewStatut);
        buttonSauvegarder = findViewById(R.id.buttonSauvegarder);
    }

    private void remplirChamps() {
        champNom.setText(utilisateur.getNom());
        champPrenom.setText(utilisateur.getPrenom());
        champEmail.setText(utilisateur.getEmail());
        textViewStatut.setText("Statut : " + utilisateur.getStatut());
        buttonRetour = findViewById(R.id.buttonRetour);

    }

    private void configurerEcouteurs() {
        buttonSauvegarder.setOnClickListener(v -> sauvegarderModifications());
        buttonRetour.setOnClickListener(v -> retournerAccueil());
    }

    private boolean validerChamps() {
        boolean estValide = true;

        String nom = champNom.getText().toString().trim();
        if (nom.isEmpty()) {
            champNom.setError("Le nom est requis");
            estValide = false;
        }

        String prenom = champPrenom.getText().toString().trim();
        if (prenom.isEmpty()) {
            champPrenom.setError("Le prénom est requis");
            estValide = false;
        }

        String email = champEmail.getText().toString().trim();
        if (email.isEmpty()) {
            champEmail.setError("L'email est requis");
            estValide = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            champEmail.setError("Adresse email invalide");
            estValide = false;
        }

        return estValide;
    }

    private String extraireMessageErreur(ResponseBody errorBody) {
        try {
            if (errorBody != null) {
                String reponse = errorBody.string();
                try {
                    JSONObject jsonObject = new JSONObject(reponse);
                    if (jsonObject.has("message")) {
                        return jsonObject.getString("message");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return reponse;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Une erreur est survenue lors de la mise à jour";
    }

    private void sauvegarderModifications() {
        if (!validerChamps()) {
            return;
        }

        // Mettre à jour l'objet utilisateur avec les nouvelles valeurs
        utilisateur.setNom(champNom.getText().toString().trim());
        utilisateur.setPrenom(champPrenom.getText().toString().trim());
        utilisateur.setEmail(champEmail.getText().toString().trim());

        UtilisateurApi api = ApiClient.getRetrofitInstance().create(UtilisateurApi.class);
        Call<Utilisateur> appel = api.updateUser(utilisateur.getId(), utilisateur);

        appel.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(ProfilActivity.this,
                            "Profil mis à jour avec succès", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfilActivity.this, AccueilAdminActivity.class);
                    intent.putExtra("utilisateur", response.body());
                    startActivity(intent);
                    finish();
                } else {
                    String messageErreur = extraireMessageErreur(response.errorBody());
                    Toast.makeText(ProfilActivity.this, messageErreur, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                String messageErreur = "Erreur de connexion : " +
                        (t.getMessage() != null ? t.getMessage() : "Impossible de joindre le serveur");
                Toast.makeText(ProfilActivity.this, messageErreur, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void retournerAccueil() {
        if (UtilisateurStatut.Profeseur.equals(utilisateur.getStatut())) {
            Intent intent = new Intent(ProfilActivity.this, AccueilAdminActivity.class);
            intent.putExtra("utilisateur", utilisateur);
            startActivity(intent);
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ProfilActivity.this, AccueilAdminActivity.class);
            intent.putExtra("utilisateur", utilisateur);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}