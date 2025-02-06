package com.master.quizzfront.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.master.quizzfront.R;
import com.master.quizzfront.Utils.BaseToolbarActivity;

public class ModifiactionQuestionnaireActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifiaction_questionnaire);
        setupToolbar();
    }
}