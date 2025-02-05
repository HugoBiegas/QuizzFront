package com.master.quizzfront.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import com.master.quizzfront.DTO.TentativeDTO;
import com.master.quizzfront.Utils.BaseToolbarActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.master.quizzfront.Adapters.QuestionnaireAdapter;
import com.master.quizzfront.Adapters.TentativeAdapter;
import com.master.quizzfront.Api.ApiClient;
import com.master.quizzfront.Api.QuestionnaireApi;
import com.master.quizzfront.Api.TentativeApi;
import com.master.quizzfront.DTO.QuestionnaireDTO;
import com.master.quizzfront.Models.Tentative;
import com.master.quizzfront.R;
import java.util.List;

public class AccueilUtilisateurActivity extends BaseToolbarActivity {
    private ListView listViewQuestionnaires;
    private ListView listViewTentatives;
    private QuestionnaireApi questionnaireApi;
    private TentativeApi tentativeApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_utilisateur);
        setupToolbar();

        listViewQuestionnaires = findViewById(R.id.listViewQuestionnaires);
        listViewTentatives = findViewById(R.id.listViewTentatives);
        listViewTentatives.setEnabled(false);

        questionnaireApi = ApiClient.getRetrofitInstance().create(QuestionnaireApi.class);
        tentativeApi = ApiClient.getRetrofitInstance().create(TentativeApi.class);

        loadQuestionnaires();
        loadTentatives();
        setupListeners();
    }

    private void setupListeners() {
        listViewQuestionnaires.setOnItemClickListener((parent, view, position, id) -> {
            QuestionnaireDTO questionnaire = (QuestionnaireDTO) parent.getItemAtPosition(position);
            Intent intent = new Intent(AccueilUtilisateurActivity.this, QuestionnaireActivity.class);
            intent.putExtra("questionnaireId", questionnaire.getId());
            intent.putExtra("utilisateur", utilisateur);
            startActivity(intent);
        });
    }

    private void loadQuestionnaires() {
        questionnaireApi.getAllQuestionnaires().enqueue(new Callback<List<QuestionnaireDTO>>() {
            @Override
            public void onResponse(Call<List<QuestionnaireDTO>> call, Response<List<QuestionnaireDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    QuestionnaireAdapter adapter = new QuestionnaireAdapter(
                            AccueilUtilisateurActivity.this,
                            response.body()
                    );
                    listViewQuestionnaires.setAdapter(adapter);
                } else {
                    Toast.makeText(AccueilUtilisateurActivity.this,
                            "Erreur lors du chargement des questionnaires",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<QuestionnaireDTO>> call, Throwable t) {
                Toast.makeText(AccueilUtilisateurActivity.this,
                        "Erreur de connexion",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTentatives() {
        if (utilisateur != null) {
            tentativeApi.getUserHistory(utilisateur.getId()).enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<List<TentativeDTO>> call, Response<List<TentativeDTO>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        TentativeAdapter adapter = new TentativeAdapter(
                                AccueilUtilisateurActivity.this,
                                response.body()
                        );
                        listViewTentatives.setAdapter(adapter);
                    } else {
                        Toast.makeText(AccueilUtilisateurActivity.this,
                                "Erreur lors du chargement des tentatives",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<TentativeDTO>> call, Throwable t) {
                    Toast.makeText(AccueilUtilisateurActivity.this,
                            "Erreur de connexion",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}