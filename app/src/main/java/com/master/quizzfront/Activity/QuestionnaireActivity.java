package com.master.quizzfront.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import com.master.quizzfront.Api.ApiClient;
import com.master.quizzfront.Api.QuestionnaireApi;
import com.master.quizzfront.Api.TentativeApi;
import com.master.quizzfront.DTO.QuestionnaireDetailDTO;
import com.master.quizzfront.DTO.ReponseUtilisateurDTO;
import com.master.quizzfront.DTO.StartTentativeRequestDTO;
import com.master.quizzfront.Enum.TypeReponse;
import com.master.quizzfront.Models.Tentative;
import com.master.quizzfront.Models.Utilisateur;
import com.master.quizzfront.R;
import com.master.quizzfront.Utils.BaseToolbarActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class QuestionnaireActivity extends BaseToolbarActivity {
    // Variables pour les vues
    private TextView textViewProgress;
    private TextView textViewQuestion;
    private LinearLayout layoutReponses;
    private Button buttonSuivant;

    // Variables de données
    private Integer questionnaireId;
    private Integer tentativeId;
    private List<QuestionnaireDetailDTO.Question> questions;
    private int currentQuestionIndex = 0;
    private QuestionnaireDetailDTO questionnaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        // Initialisation
        initializeViews();
        configurerEcouteurs();
        setupToolbar();
        getIntentData();
        startQuestionnaire();
    }

    private void initializeViews() {
        textViewProgress = findViewById(R.id.textViewProgress);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        layoutReponses = findViewById(R.id.layoutReponses);
        buttonSuivant = findViewById(R.id.buttonSuivant);
    }

    private void configurerEcouteurs() {
        buttonSuivant.setOnClickListener(v -> handleNextQuestion());
    }

    private void getIntentData() {
        questionnaireId = getIntent().getIntExtra("questionnaireId", -1);
        utilisateur = (Utilisateur) getIntent().getSerializableExtra("utilisateur");

        if (questionnaireId == -1 || utilisateur == null) {
            Toast.makeText(this, "Erreur: données manquantes", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void startQuestionnaire() {
        QuestionnaireApi api = ApiClient.getRetrofitInstance().create(QuestionnaireApi.class);
        api.getQuestionnaireDetails(questionnaireId).enqueue(new Callback<QuestionnaireDetailDTO>() {
            @Override
            public void onResponse(Call<QuestionnaireDetailDTO> call, Response<QuestionnaireDetailDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    questionnaire = response.body();
                    questions = new ArrayList<>(questionnaire.getQuestions());
                    Collections.shuffle(questions); // Mélange les questions
                    startTentative();
                } else {
                    showError("Erreur lors du chargement du questionnaire", null);
                }
            }

            @Override
            public void onFailure(Call<QuestionnaireDetailDTO> call, Throwable t) {
                showError("Erreur de connexion", t);
            }
        });
    }

    private void startTentative() {
        Log.d(TAG, "Starting tentative");

        TentativeApi api = ApiClient.getRetrofitInstance().create(TentativeApi.class);

        // Créer la requête
        StartTentativeRequestDTO requestDTO = new StartTentativeRequestDTO(
                utilisateur.getId(),
                questionnaireId
        );

        // Log de la requête
        Log.d(TAG, "Sending request with userId: " + requestDTO.getUtilisateur().getId() +
                " and questionnaireId: " + requestDTO.getQuestionnaire().getId());

        api.startTentative(requestDTO).enqueue(new Callback<Tentative>() {
            @Override
            public void onResponse(Call<Tentative> call, Response<Tentative> response) {
                Log.d(TAG, "Response code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    Tentative tentative = response.body();
                    tentativeId = tentative.getId();
                    Log.d(TAG, "Tentative created successfully with id: " + tentativeId);
                    Log.d(TAG, "Response body - utilisateurId: " +
                            tentative.getIdUtilisateur().getId() +
                            ", questionnaireId: " +
                            tentative.getIdQuestionnaire().getId());
                    displayCurrentQuestion();
                } else {
                    try {
                        String errorBody = response.errorBody() != null ?
                                response.errorBody().string() : "Unknown error";
                        Log.e(TAG, "Error response: " + errorBody);
                        showError("Erreur lors du démarrage de la tentative: " + errorBody, null);
                    } catch (Exception e) {
                        Log.e(TAG, "Error reading error body", e);
                        showError("Erreur lors du démarrage de la tentative", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Tentative> call, Throwable t) {
                Log.e(TAG, "Network call failed", t);
                showError("Erreur de connexion", t);
            }
        });
    }
    private void displayCurrentQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            Log.d(TAG, "All questions answered for tentative: " + tentativeId +
                    ". Getting final score.");
            finishQuestionnaire();
            return;
        }

        Log.d(TAG, "Displaying question " + (currentQuestionIndex + 1) + "/" +
                questions.size() + " for tentative: " + tentativeId);

        QuestionnaireDetailDTO.Question question = questions.get(currentQuestionIndex);
        textViewProgress.setText(String.format("Question %d/%d", currentQuestionIndex + 1, questions.size()));
        textViewQuestion.setText(question.getTexteQuestion());

        layoutReponses.removeAllViews();
        List<QuestionnaireDetailDTO.Reponse> reponses = new ArrayList<>(question.getReponses());
        Collections.shuffle(reponses);

        if (TypeReponse.unique.equals(question.getTypeReponse())) {
            displaySingleChoiceAnswers(reponses);
        } else {
            displayMultipleChoiceAnswers(reponses);
        }
    }
    private void displaySingleChoiceAnswers(List<QuestionnaireDetailDTO.Reponse> reponses) {
        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setLayoutParams(new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT
        ));

        for (QuestionnaireDetailDTO.Reponse reponse : reponses) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(reponse.getTexteReponse());
            radioButton.setTag(reponse.getId());
            radioGroup.addView(radioButton);
        }

        layoutReponses.addView(radioGroup);
    }

    private void displayMultipleChoiceAnswers(List<QuestionnaireDetailDTO.Reponse> reponses) {
        for (QuestionnaireDetailDTO.Reponse reponse : reponses) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(reponse.getTexteReponse());
            checkBox.setTag(reponse.getId());
            layoutReponses.addView(checkBox);
        }
    }

    private void handleNextQuestion() {
        List<Integer> selectedAnswers = collectSelectedAnswers();
        if (selectedAnswers.isEmpty()) {
            Toast.makeText(this, "Veuillez sélectionner au moins une réponse", Toast.LENGTH_SHORT).show();
            return;
        }

        sendAnswer(questions.get(currentQuestionIndex).getId(), selectedAnswers);
    }

    private List<Integer> collectSelectedAnswers() {
        List<Integer> selectedAnswers = new ArrayList<>();
        QuestionnaireDetailDTO.Question currentQuestion = questions.get(currentQuestionIndex);

        if (TypeReponse.unique.equals(currentQuestion.getTypeReponse())) {
            RadioGroup radioGroup = (RadioGroup) layoutReponses.getChildAt(0);
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedButton = findViewById(selectedId);
                selectedAnswers.add((Integer) selectedButton.getTag());
            }
        } else {
            for (int i = 0; i < layoutReponses.getChildCount(); i++) {
                CheckBox checkBox = (CheckBox) layoutReponses.getChildAt(i);
                if (checkBox.isChecked()) {
                    selectedAnswers.add((Integer) checkBox.getTag());
                }
            }
        }

        return selectedAnswers;
    }

    private void sendAnswer(Integer questionId, List<Integer> answerIds) {
        Log.d(TAG, "Sending answer for tentativeId: " + tentativeId +
                ", questionId: " + questionId +
                ", answers: " + answerIds);

        TentativeApi api = ApiClient.getRetrofitInstance().create(TentativeApi.class);
        ReponseUtilisateurDTO reponse = new ReponseUtilisateurDTO();
        reponse.setTentativeId(tentativeId);
        reponse.setQuestionId(questionId);
        reponse.setReponseIds(answerIds);

        api.traiterReponse(tentativeId, reponse).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Answer saved successfully for tentative: " + tentativeId);
                    currentQuestionIndex++;
                    displayCurrentQuestion();
                } else {
                    try {
                        String errorBody = response.errorBody() != null ?
                                response.errorBody().string() : "Unknown error";
                        Log.e(TAG, "Error saving answer: " + errorBody);
                        showError("Erreur lors de l'enregistrement de la réponse",
                                new RuntimeException(errorBody));
                    } catch (Exception e) {
                        Log.e(TAG, "Error reading error body", e);
                        showError("Erreur lors de l'enregistrement de la réponse", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Network error while saving answer", t);
                showError("Erreur de connexion", t);
            }
        });
    }
    private void finishQuestionnaire() {
        TentativeApi api = ApiClient.getRetrofitInstance().create(TentativeApi.class);
        api.getScore(tentativeId).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    showFinalScore(response.body());
                } else {
                    showError("Erreur lors de la récupération du score", null);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                showError("Erreur de connexion", t);
            }
        });
    }

    private void showFinalScore(int score) {
        new AlertDialog.Builder(this)
                .setTitle("Questionnaire terminé")
                .setMessage("Votre score final est de " + score + "%")
                .setPositiveButton("OK", (dialog, which) -> {
                    Intent intent = new Intent(QuestionnaireActivity.this, AccueilUtilisateurActivity.class);
                    intent.putExtra("utilisateur", utilisateur);
                    startActivity(intent);
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    private void showError(String message, Throwable t) {
        Log.e(TAG, message, t);
        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Erreur")
                    .setMessage(t != null ? message + "\n" + t.getMessage() : message)
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .setCancelable(false)
                    .show();
        });
    }

}