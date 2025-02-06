package com.master.quizzfront.Activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.master.quizzfront.Adapters.QuestionAdapter;
import com.master.quizzfront.Adapters.ReponseAdapter;
import com.master.quizzfront.Api.ApiClient;
import com.master.quizzfront.Api.QuestionApi;
import com.master.quizzfront.Api.QuestionnaireApi;
import com.master.quizzfront.Api.ReponseApi;
import com.master.quizzfront.Enum.TypeReponse;
import com.master.quizzfront.Models.Question;
import com.master.quizzfront.Models.Questionnaire;
import com.master.quizzfront.Models.Reponse;
import com.master.quizzfront.R;
import com.master.quizzfront.Utils.BaseToolbarActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class CreerQuestionnaireActivity extends BaseToolbarActivity {
    private TextInputEditText editTextTitre;
    private TextInputEditText editTextDescription;
    private RecyclerView recyclerViewQuestions;
    private MaterialButton buttonAddNewQuestion;
    private MaterialButton buttonAddExistingQuestion;
    private MaterialButton buttonSaveQuestionnaire;

    private QuestionnaireApi questionnaireApi;
    private QuestionApi questionApi;
    private ReponseApi reponseApi;
    private List<Question> questionsList;
    private QuestionAdapter questionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_questionnaire);
        setupToolbar();

        initializeViews();
        initializeApis();
        setupListeners();
        setupRecyclerView();
    }

    private void initializeViews() {
        editTextTitre = findViewById(R.id.editTextTitre);
        editTextDescription = findViewById(R.id.editTextDescription);
        recyclerViewQuestions = findViewById(R.id.recyclerViewQuestions);
        buttonAddNewQuestion = findViewById(R.id.buttonAddNewQuestion);
        buttonAddExistingQuestion = findViewById(R.id.buttonAddExistingQuestion);
        buttonSaveQuestionnaire = findViewById(R.id.buttonSaveQuestionnaire);
    }

    private void initializeApis() {
        questionnaireApi = ApiClient.getRetrofitInstance().create(QuestionnaireApi.class);
        questionApi = ApiClient.getRetrofitInstance().create(QuestionApi.class);
        reponseApi = ApiClient.getRetrofitInstance().create(ReponseApi.class);
        questionsList = new ArrayList<>();
    }

    private void setupListeners() {
        buttonAddNewQuestion.setOnClickListener(v -> showAddQuestionDialog(null));
        buttonAddExistingQuestion.setOnClickListener(v -> loadExistingQuestions());
        buttonSaveQuestionnaire.setOnClickListener(v -> saveQuestionnaire());
    }
    private void setupRecyclerView() {
        recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(this));
        questionAdapter = new QuestionAdapter(questionsList, new QuestionAdapter.OnQuestionClickListener() {
            @Override
            public void onQuestionClick(Question question) {
                showAddQuestionDialog(question);
            }

            @Override
            public void onDeleteClick(Question question, int position) {
                questionsList.remove(position);
                questionAdapter.notifyItemRemoved(position);
            }
        });
        recyclerViewQuestions.setAdapter(questionAdapter);
    }

    public void showAddQuestionDialog(Question existingQuestion) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_question, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        TextInputEditText editTextQuestion = dialogView.findViewById(R.id.editTextQuestion);
        RadioGroup radioGroupType = dialogView.findViewById(R.id.radioGroupTypeReponse);
        RecyclerView recyclerViewReponses = dialogView.findViewById(R.id.recyclerViewReponses);
        MaterialButton buttonAddReponse = dialogView.findViewById(R.id.buttonAddReponse);

        List<Reponse> reponsesList = new ArrayList<>();
        ReponseAdapter reponseAdapter = new ReponseAdapter(reponsesList);
        recyclerViewReponses.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReponses.setAdapter(reponseAdapter);

        // Si on modifie une question existante
        if (existingQuestion != null) {
            editTextQuestion.setText(existingQuestion.getTexteQuestion());
            radioGroupType.check(existingQuestion.getTypeReponse() == TypeReponse.unique ?
                    R.id.radioButtonUnique : R.id.radioButtonMultiple);
            // Charger les réponses existantes
            loadExistingReponses(existingQuestion.getId(), recyclerViewReponses);
        }

        // Gérer l'ajout de réponses
        buttonAddReponse.setOnClickListener(v -> {
            showAddReponseDialog(reponsesList, reponseAdapter);
        });

        AlertDialog dialog = builder.setView(dialogView)
                .setPositiveButton("Sauvegarder", (dialogInterface, i) -> {
                    if (validateQuestion(editTextQuestion, reponsesList)) {
                        Question question = existingQuestion != null ? existingQuestion : new Question();
                        question.setTexteQuestion(editTextQuestion.getText().toString());
                        question.setTypeReponse(radioGroupType.getCheckedRadioButtonId() == R.id.radioButtonUnique ?
                                TypeReponse.unique : TypeReponse.multiple);

                        if (existingQuestion == null) {
                            questionsList.add(question);
                        }
                        questionAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Annuler", null)
                .create();

        dialog.show();
    }

    private void showAddReponseDialog(List<Reponse> reponsesList, ReponseAdapter adapter) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_reponse, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        TextInputEditText editTextReponse = dialogView.findViewById(R.id.editTextReponse);
        CheckBox checkBoxCorrect = dialogView.findViewById(R.id.checkBoxCorrect);

        builder.setView(dialogView)
                .setTitle("Ajouter une réponse")
                .setPositiveButton("Ajouter", (dialog, which) -> {
                    String texteReponse = editTextReponse.getText().toString();
                    if (!texteReponse.isEmpty()) {
                        Reponse reponse = new Reponse();
                        reponse.setTexteReponse(texteReponse);
                        reponse.setEstCorrect(checkBoxCorrect.isChecked());
                        reponsesList.add(reponse);
                        adapter.notifyItemInserted(reponsesList.size() - 1);
                    }
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private boolean validateQuestion(TextInputEditText editTextQuestion, List<Reponse> reponses) {
        if (editTextQuestion.getText().toString().trim().isEmpty()) {
            editTextQuestion.setError("La question est requise");
            return false;
        }
        if (reponses.isEmpty()) {
            Toast.makeText(this, "Ajoutez au moins une réponse", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    private void loadExistingReponses(Integer questionId, RecyclerView recyclerView) {
//        reponseApi.getReponses(questionId).enqueue(new Callback<List<Reponse>>() {
//            @Override
//            public void onResponse(Call<List<Reponse>> call, Response<List<Reponse>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    ReponseAdapter reponseAdapter = new ReponseAdapter(response.body());
//                    recyclerView.setLayoutManager(new LinearLayoutManager(CreerQuestionnaireActivity.this));
//                    recyclerView.setAdapter(reponseAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Reponse>> call, Throwable t) {
//                Toast.makeText(CreerQuestionnaireActivity.this,
//                        "Erreur lors du chargement des réponses", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void loadExistingQuestions() {
        questionApi.getAllQuestions(null).enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    showExistingQuestionsDialog(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Toast.makeText(CreerQuestionnaireActivity.this,
                        "Erreur lors du chargement des questions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showExistingQuestionsDialog(List<Question> questions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sélectionner une question existante");

        // Créer un ListView pour afficher les questions
        ListView listView = new ListView(this);
        ArrayAdapter<Question> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, questions);
        listView.setAdapter(adapter);

        builder.setView(listView);

        AlertDialog dialog = builder.create();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Question selectedQuestion = questions.get(position);
            showAddQuestionDialog(selectedQuestion); // Réutiliser le dialogue avec la question sélectionnée
            dialog.dismiss();
        });

        dialog.show();
    }

    private void saveQuestionnaire() {
        if (!validateInput()) {
            return;
        }

        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setTitre(editTextTitre.getText().toString());
        questionnaire.setDescription(editTextDescription.getText().toString());
        questionnaire.setCreePar(utilisateur.getId());

        questionnaireApi.createQuestionnaire(questionnaire).enqueue(new Callback<Questionnaire>() {
            @Override
            public void onResponse(Call<Questionnaire> call, Response<Questionnaire> response) {
                if (response.isSuccessful() && response.body() != null) {
                    saveQuestions(response.body().getId());
                } else {
                    Toast.makeText(CreerQuestionnaireActivity.this,
                            "Erreur lors de la création du questionnaire", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Questionnaire> call, Throwable t) {
                Toast.makeText(CreerQuestionnaireActivity.this,
                        "Erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveQuestions(Integer questionnaireId) {
        for (Question question : questionsList) {
            question.setIdQuestionnaire(questionnaireId);
            questionApi.addQuestion(questionnaireId, question).enqueue(new Callback<Question>() {
                @Override
                public void onResponse(Call<Question> call, Response<Question> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Succès
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Question> call, Throwable t) {
                    Toast.makeText(CreerQuestionnaireActivity.this,
                            "Erreur lors de la sauvegarde des questions", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean validateInput() {
        if (editTextTitre.getText().toString().trim().isEmpty()) {
            editTextTitre.setError("Le titre est requis");
            return false;
        }
        if (editTextDescription.getText().toString().trim().isEmpty()) {
            editTextDescription.setError("La description est requise");
            return false;
        }
        if (questionsList.isEmpty()) {
            Toast.makeText(this, "Ajoutez au moins une question", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}