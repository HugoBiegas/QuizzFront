package com.master.quizzfront.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.master.quizzfront.Models.Reponse;
import com.master.quizzfront.R;

import java.util.List;

public class ReponseAdapter extends RecyclerView.Adapter<ReponseAdapter.ReponseViewHolder> {
    private List<Reponse> reponses;

    public ReponseAdapter(List<Reponse> reponses) {
        this.reponses = reponses;
    }

    @Override
    public ReponseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reponse_creation, parent, false);
        return new ReponseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReponseViewHolder holder, int position) {
        Reponse reponse = reponses.get(position);
        holder.textViewReponse.setText(reponse.getTexteReponse());
        holder.checkBoxCorrect.setChecked(reponse.isEstCorrect());
    }

    @Override
    public int getItemCount() {
        return reponses.size();
    }

    class ReponseViewHolder extends RecyclerView.ViewHolder {
        TextView textViewReponse;
        CheckBox checkBoxCorrect;

        ReponseViewHolder(View itemView) {
            super(itemView);
            textViewReponse = itemView.findViewById(R.id.textViewReponse);
            checkBoxCorrect = itemView.findViewById(R.id.checkBoxCorrect);
        }
    }
}