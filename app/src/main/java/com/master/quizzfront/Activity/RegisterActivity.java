package com.master.quizzfront.Activity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.master.quizzfront.Api.ApiClient;
import com.master.quizzfront.Api.AuthApi;
import com.master.quizzfront.Models.Utilisateur;
import com.master.quizzfront.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;
import org.json.JSONObject;
import okhttp3.ResponseBody;

public class RegisterActivity extends AppCompatActivity {
    private EditText champNom;
    private EditText champPrenom;
    private EditText champEmail;
    private EditText champMotDePasse;
    private EditText champConfirmationMotDePasse;
    private Button boutonInscription;
    private Button boutonRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialiserVues();
        configurerEcouteurs();
    }

    private void initialiserVues() {
        champNom = findViewById(R.id.editTextNom);
        champPrenom = findViewById(R.id.editTextPrenom);
        champEmail = findViewById(R.id.editTextEmail);
        champMotDePasse = findViewById(R.id.editTextPassword);
        champConfirmationMotDePasse = findViewById(R.id.editTextConfirmPassword);
        boutonInscription = findViewById(R.id.buttonRegister);
        boutonRetour = findViewById(R.id.buttonRetour);
    }

    private void configurerEcouteurs() {
        boutonInscription.setOnClickListener(v -> traiterInscription());
        boutonRetour.setOnClickListener(v -> finish());
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
            champEmail.setError("Veuillez entrer une adresse email valide");
            estValide = false;
        }

        String motDePasse = champMotDePasse.getText().toString();
        String confirmationMotDePasse = champConfirmationMotDePasse.getText().toString();

        if (motDePasse.isEmpty()) {
            champMotDePasse.setError("Le mot de passe est requis");
            estValide = false;
        } else if (motDePasse.length() < 6) {
            champMotDePasse.setError("Le mot de passe doit contenir au moins 6 caractères");
            estValide = false;
        }

        if (!motDePasse.equals(confirmationMotDePasse)) {
            champConfirmationMotDePasse.setError("Les mots de passe ne correspondent pas");
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
        return "Une erreur est survenue lors de l'inscription";
    }

    private void traiterInscription() {
        if (!validerChamps()) {
            return;
        }

        Utilisateur nouvelUtilisateur = new Utilisateur();
        nouvelUtilisateur.setNom(champNom.getText().toString().trim());
        nouvelUtilisateur.setPrenom(champPrenom.getText().toString().trim());
        nouvelUtilisateur.setEmail(champEmail.getText().toString().trim());
        nouvelUtilisateur.setMotDePasse(champMotDePasse.getText().toString());

        AuthApi authApi = ApiClient.getRetrofitInstance().create(AuthApi.class);
        Call<Utilisateur> appel = authApi.register(nouvelUtilisateur);

        appel.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegisterActivity.this,
                            "Compte créé avec succès", Toast.LENGTH_SHORT).show();
                    finish(); // Retour à l'écran de connexion
                } else {
                    String messageErreur = extraireMessageErreur(response.errorBody());
                    Toast.makeText(RegisterActivity.this, messageErreur, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                String messageErreur = "Erreur de connexion : " +
                        (t.getMessage() != null ? t.getMessage() : "Impossible de joindre le serveur");
                Toast.makeText(RegisterActivity.this, messageErreur, Toast.LENGTH_LONG).show();
            }
        });
    }
}