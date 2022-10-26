package com.voidstudio.quickcashreg;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Landing page for successful login
 */
public class InAppActivityEmployer extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_app_activity_employer);

        // switch to this activity when log in successfully
        Intent thisIntent = getIntent();
        String welcomeMessage = thisIntent.getStringExtra(LogIn.WELCOME);
        TextView message = findViewById(R.id.Employer);
        message.setText(welcomeMessage);
    }
}
