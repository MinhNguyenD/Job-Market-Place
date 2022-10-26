package com.voidstudio.quickcashreg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Landing page for successful login
 */
public class InAppActivityEmployer extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_app_activity_employer);

        // switch to this activity when log in successfully
        Intent thisIntent = getIntent();
        String welcomeMessage = thisIntent.getStringExtra(LogIn.WELCOME);
        TextView message = findViewById(R.id.Employer);
        message.setText(welcomeMessage);

        Button logOut = findViewById(R.id.logOutEmployer);
        logOut.setOnClickListener(InAppActivityEmployer.this);
    }

    //Exact same method exists in employee, consider making new class
    private void resetLogInStatus() {
        SharedPreferences sharedPrefs = getSharedPreferences(LogIn.PREFERENCES, Context.MODE_PRIVATE); //
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(LogIn.ISLOGGED, false);
        editor.commit();
    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.logOutEmployer){
            resetLogInStatus();
            Intent logOutIntent = new Intent(InAppActivityEmployer.this, LogIn.class);
            startActivity(logOutIntent);
        }
    }


}
