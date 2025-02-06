package com.master.quizzfront.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.master.quizzfront.Api.ApiClient;
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

    private FloatingActionButton fabAddUser;

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
    }
    private void setupListeners() {
        fabAddUser.setOnClickListener(v -> {
            // Intent vers l'activité de création de questionnaire
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
        // Implémenter le chargement de tous les utilisateurs
    }
}

