package com.master.quizzfront.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.master.quizzfront.Api.ApiClient;
import com.master.quizzfront.R;
import com.master.quizzfront.Api.QuestionnaireApi;
import com.master.quizzfront.DTO.QuestionnaireDTO;
import com.master.quizzfront.Adapters.QuestionnaireAdapter;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionnairesFragment extends Fragment {
    private ListView listViewQuestionnaires;
    private QuestionnaireApi questionnaireApi;
    private FloatingActionButton fabAddQuestionnaire;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questionnaires, container, false);
        initilisationVues(view);
        initialiserApi();

        setupListeners();
        loadQuestionnaires();

        return view;
    }

    private void initilisationVues(View view){
        listViewQuestionnaires = view.findViewById(R.id.listViewQuestionnaires);
        fabAddQuestionnaire = view.findViewById(R.id.fab_add_questionnaire);

    }

    private void initialiserApi(){
        questionnaireApi = ApiClient.getRetrofitInstance().create(QuestionnaireApi.class);
    }

    private void setupListeners() {
        fabAddQuestionnaire.setOnClickListener(v -> {
            // Intent vers l'activité de création de questionnaire
        });
    }

    private void loadQuestionnaires() {
        questionnaireApi.getAllQuestionnaires().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<QuestionnaireDTO>> call, Response<List<QuestionnaireDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    QuestionnaireAdapter adapter = new QuestionnaireAdapter(getContext(), response.body());
                    listViewQuestionnaires.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<QuestionnaireDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur de chargement", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

