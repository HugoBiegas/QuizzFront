package com.master.quizzfront.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.master.quizzfront.DTO.TentativeDTO;
import com.master.quizzfront.Models.Tentative;
import com.master.quizzfront.R;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TentativeAdapter extends ArrayAdapter<TentativeDTO> {
    private final SimpleDateFormat dateFormat;

    public TentativeAdapter(Context context, List<TentativeDTO> tentatives) {
        super(context, 0, tentatives);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TentativeDTO tentative = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tentative, parent, false);
        }

        TextView titreTextView = convertView.findViewById(R.id.textViewTitreQuestionnaire);
        TextView scoreTextView = convertView.findViewById(R.id.textViewScore);
        TextView dateTextView = convertView.findViewById(R.id.textViewDate);

        titreTextView.setText(tentative.getQuestionnaire().getTitre());
        scoreTextView.setText("Score: " + tentative.getScore() + "%");
        dateTextView.setText(dateFormat.format(tentative.getDatePassage()));

        return convertView;
    }
}