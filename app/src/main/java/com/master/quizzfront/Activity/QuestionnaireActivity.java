package com.master.quizzfront.Activity;

import android.os.Bundle;

import com.master.quizzfront.R;
import com.master.quizzfront.Utils.BaseToolbarActivity;

public class QuestionnaireActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        setupToolbar();
    }
}