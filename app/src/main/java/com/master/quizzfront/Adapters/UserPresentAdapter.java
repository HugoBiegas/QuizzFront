package com.master.quizzfront.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.master.quizzfront.Api.ApiClient;
import com.master.quizzfront.Api.UtilisateurApi;
import com.master.quizzfront.DTO.UtilisateurDTO;
import com.master.quizzfront.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class UserPresentAdapter extends ArrayAdapter<UtilisateurDTO> {
    private final Context context;
    private final List<UtilisateurDTO> utilisateurs;
    private final UtilisateurApi utilisateurApi;

    public UserPresentAdapter(Context context, List<UtilisateurDTO> utilisateurs) {
        super(context, R.layout.list_item_user_admin, utilisateurs);
        this.context = context;
        this.utilisateurs = utilisateurs;
        this.utilisateurApi = ApiClient.getRetrofitInstance().create(UtilisateurApi.class);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_user_admin, parent, false);
        }

        UtilisateurDTO utilisateur = getItem(position);

        TextView tvNom = convertView.findViewById(R.id.tvNom);
        TextView tvPrenom = convertView.findViewById(R.id.tvPrenom);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);
        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);
        LinearLayout userInfoContainer = convertView.findViewById(R.id.userInfoContainer);
        userInfoContainer.setOnClickListener(v -> {
            ((ListView) parent).performItemClick(v, position, utilisateur.getId());
        });

        if (utilisateur != null) {
            tvNom.setText("Nom : " + utilisateur.getNom());
            tvPrenom.setText("Prénom : " + utilisateur.getPrenom());
            tvStatus.setText("Status : " + utilisateur.getStatut());

            btnDelete.setOnClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setTitle("Confirmation")
                        .setMessage("Voulez-vous vraiment supprimer cet utilisateur ?")
                        .setPositiveButton("Oui", (dialog, which) -> deleteUser(utilisateur, position))
                        .setNegativeButton("Non", null)
                        .setOnDismissListener(dialog -> ((ListView) parent).setEnabled(true))
                        .show();
            });
        }

        return convertView;
    }
    private void deleteUser(UtilisateurDTO utilisateur, int position) {
        utilisateurApi.deleteUser(utilisateur.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    utilisateurs.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Utilisateur supprimé avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}