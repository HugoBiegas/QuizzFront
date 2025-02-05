package com.master.quizzfront.Activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.master.quizzfront.Models.Utilisateur;
import com.master.quizzfront.R;

public abstract class BaseToolbarActivity extends AppCompatActivity {
    protected Toolbar toolbar;
    protected TextView textViewNomPrenom;
    protected Button buttonProfil;
    protected Button buttonDeconnexion;
    protected Utilisateur utilisateur;

    protected void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        textViewNomPrenom = findViewById(R.id.textViewNomPrenom);
        buttonProfil = findViewById(R.id.buttonProfil);
        buttonDeconnexion = findViewById(R.id.buttonDeconnexion);

        setSupportActionBar(toolbar);

        // Récupérer l'utilisateur
        utilisateur = (Utilisateur) getIntent().getSerializableExtra("utilisateur");
        if (utilisateur != null) {
            String nomComplet = utilisateur.getPrenom() + " " + utilisateur.getNom();
            textViewNomPrenom.setText(nomComplet);
        }

        // Configuration des boutons
        if (buttonProfil != null) {
            buttonProfil.setOnClickListener(v -> {
                Intent intent = new Intent(this, ProfilActivity.class);
                intent.putExtra("utilisateur", utilisateur);
                startActivity(intent);
                finish();
            });
        }

        if (buttonDeconnexion != null) {
            buttonDeconnexion.setOnClickListener(v -> {
                // Retour à la page de connexion
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }
    }

    protected void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}