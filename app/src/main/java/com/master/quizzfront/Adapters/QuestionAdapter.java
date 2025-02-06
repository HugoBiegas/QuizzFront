package com.master.quizzfront.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.master.quizzfront.Models.Question;
import com.master.quizzfront.R;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private List<Question> questions;
    private OnQuestionClickListener listener;

    public interface OnQuestionClickListener {
        void onQuestionClick(Question question);
        void onDeleteClick(Question question, int position);
    }

    public QuestionAdapter(List<Question> questions, OnQuestionClickListener listener) {
        this.questions = questions;
        this.listener = listener;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question_creation, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.text1.setText(question.getTexteQuestion());
        holder.text2.setText("Type: " + question.getTypeReponse().toString());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onQuestionClick(question);
            }
        });

        holder.buttonDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(question, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        TextView text2;
        ImageButton buttonDelete;

        QuestionViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}