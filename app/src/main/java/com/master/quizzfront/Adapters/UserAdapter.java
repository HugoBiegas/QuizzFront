package com.master.quizzfront.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.master.quizzfront.Models.Utilisateur;
import com.master.quizzfront.R;

import java.util.List;

public class UserAdapter extends ArrayAdapter<Utilisateur> {
    private final Context context;
    private final List<Utilisateur> utilisateurs;

    public UserAdapter(Context context, List<Utilisateur> utilisateurs) {
        super(context, R.layout.list_item_user, utilisateurs);
        this.context = context;
        this.utilisateurs = utilisateurs;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_user, parent, false);
        }

        Utilisateur utilisateur = getItem(position);

        TextView tvNom = convertView.findViewById(R.id.tvNom);
        TextView tvPrenom = convertView.findViewById(R.id.tvPrenom);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);

        if (utilisateur != null) {
            tvNom.setText("Nom : " + utilisateur.getNom());
            tvPrenom.setText("Prénom : " + utilisateur.getPrenom());
            tvStatus.setText("Status : " + utilisateur.getStatut());
        }

        return convertView;
    }
}
