package com.master.quizzfront.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.master.quizzfront.Api.ApiClient;
import com.master.quizzfront.Api.AuthApi;
import com.master.quizzfront.DTO.LoginRequestDTO;
import com.master.quizzfront.Models.Utilisateur;
import com.master.quizzfront.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;

import okhttp3.ResponseBody;
import org.json.JSONObject;

import static com.master.quizzfront.Enum.UtilisateurStatut.Profeseur;

public class MainActivity extends AppCompatActivity {
    private EditText champEmail;
    private EditText champMotDePasse;
    private Button boutonConnexion;
    private Button boutonInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiserVues();
        configurerEcouteurs();
    }

    private void initialiserVues() {
        champEmail = findViewById(R.id.editTextEmail);
        champMotDePasse = findViewById(R.id.editTextPassword);
        boutonConnexion = findViewById(R.id.buttonLogin);
        boutonInscription = findViewById(R.id.buttonRegister);
    }

    private void configurerEcouteurs() {
        boutonConnexion.setOnClickListener(v -> traiterConnexion());
        boutonInscription.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private boolean validerEmail(String email) {
        if (email.isEmpty()) {
            champEmail.setError("L'email est requis");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            champEmail.setError("Veuillez entrer une adresse email valide");
            return false;
        }
        return true;
    }

    private boolean validerMotDePasse(String motDePasse) {
        if (motDePasse.isEmpty()) {
            champMotDePasse.setError("Le mot de passe est requis");
            return false;
        }
        if (motDePasse.length() < 6) {
            champMotDePasse.setError("Le mot de passe doit contenir au moins 6 caractères");
            return false;
        }
        return true;
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
        return "Une erreur est survenue";
    }

    private void traiterConnexion() {
        String email = champEmail.getText().toString().trim();
        String motDePasse = champMotDePasse.getText().toString().trim();

        if (!validerEmail(email) || !validerMotDePasse(motDePasse)) {
            return;
        }

        LoginRequestDTO demandeConnexion = new LoginRequestDTO(email, motDePasse);
        AuthApi authApi = ApiClient.getRetrofitInstance().create(AuthApi.class);
        Call<Utilisateur> appel = authApi.login(demandeConnexion);

        appel.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Utilisateur utilisateur = response.body();
                    Toast.makeText(MainActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();

                    // Vérifier le statut de l'utilisateur
                    if (Profeseur.equals(utilisateur.getStatut())) {
                        Intent intent = new Intent(MainActivity.this, AccueilAdminActivity.class);
                        intent.putExtra("utilisateur", utilisateur);
                        startActivity(intent);
                        finish(); // Ferme l'activité de connexion
                    }else {
                        Toast.makeText(MainActivity.this, "Vous n'êtes pas autorisé à accéder à cette application", Toast.LENGTH_LONG).show();
                    }
                } else {
                    String messageErreur = extraireMessageErreur(response.errorBody());
                    Toast.makeText(MainActivity.this, messageErreur, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                String messageErreur = "Erreur de connexion : " +
                        (t.getMessage() != null ? t.getMessage() : "Impossible de joindre le serveur");
                Toast.makeText(MainActivity.this, messageErreur, Toast.LENGTH_LONG).show();
            }
        });
    }
}