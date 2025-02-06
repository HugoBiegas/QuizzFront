package com.master.quizzfront.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.master.quizzfront.DTO.TentativeDetailsDTO;
import com.master.quizzfront.R;

public class QuestionResponseAdapter extends RecyclerView.Adapter<QuestionResponseAdapter.QuestionViewHolder> {

    private final TentativeDetailsDTO tentativeDetails;
    private final Context context;

    public QuestionResponseAdapter(Context context, TentativeDetailsDTO tentativeDetails) {
        this.context = context;
        this.tentativeDetails = tentativeDetails;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question_response, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        TentativeDetailsDTO.QuestionDTO question = tentativeDetails.getQuestions().get(position);

        // Afficher la question
        holder.textViewQuestion.setText(String.format("Question %d: %s", position + 1, question.getTexteQuestion()));

        // Afficher le type de réponse
        holder.textViewTypeReponse.setText(question.getTypeReponse().equals("unique") ?
                "Question à choix unique" : "Question à choix multiples");

        // Vider le container des réponses
        holder.containerReponses.removeAllViews();

        // Ajouter chaque réponse
        for (TentativeDetailsDTO.ReponseDTO reponse : question.getReponses()) {
            View reponseView = LayoutInflater.from(context).inflate(
                    R.layout.item_reponse, holder.containerReponses, false);

            TextView textViewReponse = reponseView.findViewById(R.id.textViewReponse);
            ImageView imageViewStatus = reponseView.findViewById(R.id.imageViewStatus);

            textViewReponse.setText(reponse.getTexteReponse());

            boolean estSelectionnee = tentativeDetails.isReponseSelectionnee(
                    question.getId(), reponse.getId());

            // Configuration des couleurs et icônes
            if (estSelectionnee) {
                if (reponse.getEstCorrect()) {
                    // Réponse correcte sélectionnée (vert + V)
                    textViewReponse.setTextColor(Color.parseColor("#4CAF50"));
                    imageViewStatus.setImageResource(R.drawable.ic_check);
                    imageViewStatus.setColorFilter(Color.parseColor("#4CAF50"));
                } else {
                    // Réponse incorrecte sélectionnée (orange + X)
                    textViewReponse.setTextColor(Color.parseColor("#FF8C00"));
                    imageViewStatus.setImageResource(R.drawable.ic_cross);
                    imageViewStatus.setColorFilter(Color.RED);
                }
                imageViewStatus.setVisibility(View.VISIBLE);
            } else if (reponse.getEstCorrect()) {
                // Bonne réponse non sélectionnée (vert sans icône)
                textViewReponse.setTextColor(Color.parseColor("#4CAF50"));
                imageViewStatus.setVisibility(View.GONE);
            } else {
                // Réponse non sélectionnée et incorrecte (noir sans icône)
                textViewReponse.setTextColor(Color.BLACK);
                imageViewStatus.setVisibility(View.GONE);
            }

            holder.containerReponses.addView(reponseView);
        }
    }

    @Override
    public int getItemCount() {
        return tentativeDetails.getQuestions().size();
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView textViewQuestion;
        TextView textViewTypeReponse;
        LinearLayout containerReponses;

        QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.textViewQuestion);
            textViewTypeReponse = itemView.findViewById(R.id.textViewTypeReponse);
            containerReponses = itemView.findViewById(R.id.containerReponses);
        }
    }
}