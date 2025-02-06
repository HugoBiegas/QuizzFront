package com.master.quizzfront.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.master.quizzfront.Adapters.UserPresentAdapter;
import com.master.quizzfront.Api.ApiClient;
import com.master.quizzfront.Api.AuthApi;
import com.master.quizzfront.Api.UtilisateurApi;
import com.master.quizzfront.DTO.UtilisateurDTO;
import com.master.quizzfront.Enum.UtilisateurStatut;
import com.master.quizzfront.R;
import com.master.quizzfront.Api.AdminApi;
import com.master.quizzfront.Models.Utilisateur;
import com.master.quizzfront.Adapters.UserAdapter;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment {
    private ListView listViewPendingUsers;
    private ListView listViewAllUsers;
    private AdminApi adminApi;

    private AuthApi authApi;

    private UtilisateurApi utilisateurApi;

    private FloatingActionButton fabAddUser;

    private Utilisateur selectedUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        initialiserVue(view);
        initialiserApi();

        setupListeners();
        loadPendingUsers();
        loadAllUsers();

        return view;
    }

    private void initialiserVue(View view){
        listViewPendingUsers = view.findViewById(R.id.listViewPendingUsers);
        listViewAllUsers = view.findViewById(R.id.listViewAllUsers);
        fabAddUser = view.findViewById(R.id.fab_add_user);
    }

    private void initialiserApi(){
        adminApi = ApiClient.getRetrofitInstance().create(AdminApi.class);
        utilisateurApi = ApiClient.getRetrofitInstance().create(UtilisateurApi.class);
        authApi = ApiClient.getRetrofitInstance().create(AuthApi.class);
    }
    private void setupListeners() {
        listViewPendingUsers.setOnItemClickListener((parent, view, position, id) -> {
            selectedUser = (Utilisateur) parent.getItemAtPosition(position);
            showStatusDialog();
        });

        listViewAllUsers.setOnItemClickListener((parent, view, position, id) -> {
            UtilisateurDTO user = (UtilisateurDTO) parent.getItemAtPosition(position);
            showUpdateDialog(user);
        });

        fabAddUser.setOnClickListener(v -> showCreateUserDialog());
    }

    private void showCreateUserDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_user_form, null);
        EditText editEmail = dialogView.findViewById(R.id.editEmail);
        EditText editPassword = dialogView.findViewById(R.id.editPassword);
        EditText editNom = dialogView.findViewById(R.id.editNom);
        EditText editPrenom = dialogView.findViewById(R.id.editPrenom);

        new AlertDialog.Builder(getContext())
                .setTitle("Nouvel utilisateur")
                .setView(dialogView)
                .setPositiveButton("Créer", (dialog, which) -> {
                    Utilisateur newUser = new Utilisateur();
                    newUser.setEmail(editEmail.getText().toString());
                    newUser.setMotDePasse(editPassword.getText().toString());
                    newUser.setNom(editNom.getText().toString());
                    newUser.setPrenom(editPrenom.getText().toString());
                    createUser(newUser);
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void createUser(Utilisateur newUser) {
        authApi.register(newUser).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                if (response.isSuccessful()) {
                    loadPendingUsers();
                    loadAllUsers();
                    Toast.makeText(getContext(), "Utilisateur créé", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur lors de la création", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showUpdateDialog(UtilisateurDTO user) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_user_form, null);
        EditText editEmail = dialogView.findViewById(R.id.editEmail);
        EditText editNom = dialogView.findViewById(R.id.editNom);
        EditText editPrenom = dialogView.findViewById(R.id.editPrenom);

        editEmail.setText(user.getEmail());
        editNom.setText(user.getNom());
        editPrenom.setText(user.getPrenom());

        new AlertDialog.Builder(getContext())
                .setTitle("Modifier utilisateur")
                .setView(dialogView)
                .setPositiveButton("Modifier", (dialog, which) -> {
                    Utilisateur updatedUser = new Utilisateur();
                    updatedUser.setId(user.getId());
                    updatedUser.setEmail(editEmail.getText().toString());
                    updatedUser.setNom(editNom.getText().toString());
                    updatedUser.setPrenom(editPrenom.getText().toString());
                    updateUser(updatedUser);
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void showStatusDialog() {
        UtilisateurStatut[] statuts = {UtilisateurStatut.Etudiant, UtilisateurStatut.Profeseur};
        new AlertDialog.Builder(getContext())
                .setTitle("Choisir un statut")
                .setItems(new String[]{statuts[0].name(), statuts[1].name()}, (dialog, which) -> {
                    selectedUser.setStatut(statuts[which]);
                    updateUser(selectedUser);
                })
                .show();
    }

    private void updateUser(Utilisateur user) {
        utilisateurApi.updateUser(user.getId(), user).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                if (response.isSuccessful()) {
                    loadPendingUsers();
                    loadAllUsers();
                    Toast.makeText(getContext(), "Statut mis à jour", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPendingUsers() {
        adminApi.getPendingUsers().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Utilisateur>> call, Response<List<Utilisateur>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserAdapter adapter = new UserAdapter(getContext(), response.body());
                    listViewPendingUsers.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Utilisateur>> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur de chargement", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllUsers() {
        utilisateurApi.getAllUsers().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<UtilisateurDTO>> call, Response<List<UtilisateurDTO>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_RESPONSE", "Code: " + response.code());
                    if (response.body() != null) {
                        Log.d("API_RESPONSE", "Data: " + response.body().size());
                        UserPresentAdapter adapter = new UserPresentAdapter(getContext(), response.body());
                        listViewAllUsers.setAdapter(adapter);
                    } else {
                        Log.d("API_RESPONSE", "Body is null");
                    }
                } else {
                    Log.d("API_RESPONSE", "Not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<UtilisateurDTO>> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Erreur de chargement", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

