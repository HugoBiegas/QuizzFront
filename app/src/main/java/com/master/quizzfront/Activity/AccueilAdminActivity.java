package com.master.quizzfront.Activity;

import android.os.Bundle;
import com.master.quizzfront.R;

public class AccueilAdminActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_admin);

        setupToolbar();
    }
}