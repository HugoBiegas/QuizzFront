package com.master.quizzfront.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.master.quizzfront.Adapters.QuestionResponseAdapter;
import com.master.quizzfront.Api.ApiClient;
import com.master.quizzfront.Api.TentativeApi;
import com.master.quizzfront.DTO.QuestionnaireDTO;
import com.master.quizzfront.DTO.TentativeDetailsDTO;
import com.master.quizzfront.R;
import com.master.quizzfront.Utils.BaseToolbarActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponseQuestionnaireActivity extends BaseToolbarActivity {

    private RecyclerView recyclerViewQuestions;
    private TextView textViewScore;

    private Button buttonRetour;
    private TentativeApi tentativeApi;
    private int tentativeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reponse_questionnaire);
        setupToolbar();

        tentativeId = getIntent().getIntExtra("tentativeId", -1);
        if (tentativeId == -1) {
            Toast.makeText(this, "Erreur: identifiant de tentative invalide", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initializeViews();
        setupListeners();
        setupApi();
        loadTentativeDetails();
    }

    private void initializeViews() {
        recyclerViewQuestions = findViewById(R.id.recyclerViewQuestions);
        textViewScore = findViewById(R.id.textViewScore);
        buttonRetour = findViewById(R.id.buttonRetourHomeUtilisateur);

        recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(this));
    }
    private void setupListeners() {
        buttonRetour.setOnClickListener(v -> {
            Intent intent = new Intent(this, AccueilUtilisateurActivity.class);
            intent.putExtra("utilisateur", utilisateur);
            startActivity(intent);
            finish();
        });
    }

    private void setupApi() {
        tentativeApi = ApiClient.getRetrofitInstance().create(TentativeApi.class);
    }

    private void loadTentativeDetails() {
        tentativeApi.getTentativeDetails(tentativeId).enqueue(new Callback<TentativeDetailsDTO>() {
            @Override
            public void onResponse(Call<TentativeDetailsDTO> call, Response<TentativeDetailsDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayTentativeDetails(response.body());
                } else {
                    Toast.makeText(ResponseQuestionnaireActivity.this,
                            "Erreur lors du chargement des détails",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<TentativeDetailsDTO> call, Throwable t) {
                Toast.makeText(ResponseQuestionnaireActivity.this,
                        "Erreur de connexion",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void displayTentativeDetails(TentativeDetailsDTO details) {
        // Configurer l'en-tête
        textViewScore.setText(String.format("Score : %d%%", details.getScore()));

        // Configurer le RecyclerView avec l'adaptateur
        QuestionResponseAdapter adapter = new QuestionResponseAdapter(this, details);
        recyclerViewQuestions.setAdapter(adapter);
    }
}