package com.master.quizzfront.Activity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.master.quizzfront.R;
import com.master.quizzfront.Utils.BaseToolbarActivity;
import com.master.quizzfront.Fragments.QuestionnairesFragment;
import com.master.quizzfront.Fragments.UsersFragment;

public class AccueilAdminActivity extends BaseToolbarActivity {
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_admin);
        setupToolbar();
        initialiserNav();
    }

    private void initialiserNav(){
        navView = findViewById(R.id.nav_view);
        initiliserEcouteurs();
    }

    private void initiliserEcouteurs(){
        navView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.navigation_questionnaires) {
                selectedFragment = new QuestionnairesFragment();
            } else if (item.getItemId() == R.id.navigation_users) {
                selectedFragment = new UsersFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });
        fragmentParDefault();
    }

    private void fragmentParDefault(){
        // Charger le fragment par défaut
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new QuestionnairesFragment())
                .commit();
    }
}
