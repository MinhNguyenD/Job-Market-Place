package com.voidstudio.quickcashreg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InAppActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_app_activity);

        // switch to this activity when log in successfully
        Intent thisIntent = getIntent();
        String welcomeMessage = thisIntent.getStringExtra(LogIn.WELCOME);
        TextView message = findViewById(R.id.message);
        message.setText(welcomeMessage);
    }
}
