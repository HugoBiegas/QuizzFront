package com.master.quizzfront.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
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

import java.util.Arrays;
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
        loadData();
        return view;
    }

    private void initialiserVue(View view) {
        listViewPendingUsers = view.findViewById(R.id.listViewPendingUsers);
        listViewAllUsers = view.findViewById(R.id.listViewAllUsers);
        fabAddUser = view.findViewById(R.id.fab_add_user);
    }

    private void initialiserApi() {
        adminApi = ApiClient.getRetrofitInstance().create(AdminApi.class);
        utilisateurApi = ApiClient.getRetrofitInstance().create(UtilisateurApi.class);
        authApi = ApiClient.getRetrofitInstance().create(AuthApi.class);
    }

    private void loadData() {
        loadPendingUsers();
        loadAllUsers();
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
        Spinner spinnerStatus = dialogView.findViewById(R.id.spinnerStatus);
        spinnerStatus.setVisibility(View.GONE);

        editPassword.setVisibility(View.VISIBLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Nouvel utilisateur")
                .setView(dialogView)
                .setPositiveButton("Créer", null)
                .setNegativeButton("Annuler", null);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String nom = editNom.getText().toString().trim();
                String prenom = editPrenom.getText().toString().trim();

                if (validateFields(email, password, nom, prenom)) {
                    Utilisateur newUser = new Utilisateur();
                    newUser.setEmail(email);
                    newUser.setMotDePasse(password);
                    newUser.setNom(nom);
                    newUser.setPrenom(prenom);
                    createUser(newUser);
                    dialog.dismiss();
                }
            });
        });

        dialog.show();
    }

    private void showUpdateDialog(UtilisateurDTO user) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_user_form, null);
        EditText editEmail = dialogView.findViewById(R.id.editEmail);
        EditText editNom = dialogView.findViewById(R.id.editNom);
        EditText editPrenom = dialogView.findViewById(R.id.editPrenom);
        EditText editPassword = dialogView.findViewById(R.id.editPassword);
        Spinner spinnerStatus = dialogView.findViewById(R.id.spinnerStatus);

        editEmail.setText(user.getEmail());
        editNom.setText(user.getNom());
        editPrenom.setText(user.getPrenom());
        editPassword.setVisibility(View.GONE);

        // Configuration du Spinner
        ArrayAdapter<UtilisateurStatut> statusAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                UtilisateurStatut.values()
        );
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);

        // Sélectionner le statut actuel
        int statusPosition = Arrays.asList(UtilisateurStatut.values())
                .indexOf(UtilisateurStatut.valueOf(user.getStatut()));
        spinnerStatus.setSelection(statusPosition);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Modifier utilisateur")
                .setView(dialogView)
                .setPositiveButton("Modifier", null)
                .setNegativeButton("Annuler", null);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String email = editEmail.getText().toString().trim();
                String nom = editNom.getText().toString().trim();
                String prenom = editPrenom.getText().toString().trim();
                UtilisateurStatut selectedStatus = (UtilisateurStatut) spinnerStatus.getSelectedItem();

                if (validateFields(email, null, nom, prenom)) {
                    Utilisateur updatedUser = new Utilisateur();
                    updatedUser.setId(user.getId());
                    updatedUser.setEmail(email);
                    updatedUser.setNom(nom);
                    updatedUser.setPrenom(prenom);
                    updatedUser.setStatut(selectedStatus);
                    updateUser(updatedUser);
                    dialog.dismiss();
                }
            });
        });

        dialog.show();
    }

    private boolean validateFields(String email, String password, String nom, String prenom) {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getContext(), "Email invalide", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password != null && password.length() < 6) {
            Toast.makeText(getContext(), "Le mot de passe doit contenir au moins 6 caractères", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (nom.isEmpty()) {
            Toast.makeText(getContext(), "Le nom est requis", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (prenom.isEmpty()) {
            Toast.makeText(getContext(), "Le prénom est requis", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void showStatusDialog() {
        String[] statuts = {UtilisateurStatut.Etudiant.name(), UtilisateurStatut.Profeseur.name()};
        new AlertDialog.Builder(getContext())
                .setTitle("Choisir un statut")
                .setItems(statuts, (dialog, which) -> {
                    selectedUser.setStatut(UtilisateurStatut.valueOf(statuts[which]));
                    updateUserStatus(selectedUser);
                })
                .show();
    }

    private void createUser(Utilisateur newUser) {
        authApi.register(newUser).enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Utilisateur créé avec succès", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(getContext(), "Erreur lors de la création", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUser(Utilisateur user) {
        utilisateurApi.updateUser(user.getId(), user).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Utilisateur mis à jour avec succès", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(getContext(), "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserStatus(Utilisateur user) {
        adminApi.updateUserStatus(user.getId(), user.getStatut()).enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Statut mis à jour avec succès", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(getContext(), "Erreur lors de la mise à jour du statut", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPendingUsers() {
        adminApi.getPendingUsers().enqueue(new Callback<List<Utilisateur>>() {
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
                if (response.isSuccessful() && response.body() != null) {
                    UserPresentAdapter adapter = new UserPresentAdapter(getContext(), response.body());
                    listViewAllUsers.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<UtilisateurDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur de chargement", Toast.LENGTH_SHORT).show();
            }
        });
    }
}