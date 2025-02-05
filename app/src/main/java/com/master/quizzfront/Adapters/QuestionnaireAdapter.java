package com.master.quizzfront.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.master.quizzfront.DTO.QuestionnaireDTO;
import com.master.quizzfront.R;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class QuestionnaireAdapter extends ArrayAdapter<QuestionnaireDTO> {
    private final SimpleDateFormat dateFormat;

    public QuestionnaireAdapter(Context context, List<QuestionnaireDTO> questionnaires) {
        super(context, 0, questionnaires);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QuestionnaireDTO questionnaire = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_questionnaire, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.textViewTitre);
        TextView descriptionTextView = convertView.findViewById(R.id.textViewDescription);
        TextView authorTextView = convertView.findViewById(R.id.textViewAuthor);
        TextView dateTextView = convertView.findViewById(R.id.textViewDate);

        titleTextView.setText(questionnaire.getTitre());
        descriptionTextView.setText(questionnaire.getDescription());

        if (questionnaire.getCreePar() != null) {
            String auteur = String.format("Créé par: %s %s",
                    questionnaire.getCreePar().getPrenom(),
                    questionnaire.getCreePar().getNom());
            authorTextView.setText(auteur);
        }

        if (questionnaire.getDateCreation() != null) {
            dateTextView.setText("Créé le: " + dateFormat.format(questionnaire.getDateCreation()));
        }

        return convertView;
    }
}