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
import com.master.quizzfront.DTO.UtilisateurDTO;
import com.master.quizzfront.Models.Utilisateur;
import com.master.quizzfront.R;

import java.util.List;

public class UserPresentAdapter extends ArrayAdapter<UtilisateurDTO> {
    private final Context context;
    private final List<UtilisateurDTO> utilisateurs;

    public UserPresentAdapter(Context context, List<UtilisateurDTO> utilisateurs) {
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

        UtilisateurDTO utilisateur = getItem(position);

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
