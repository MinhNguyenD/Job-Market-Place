package com.voidstudio.quickcashreg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.voidstudio.quickcashreg.jobpost.EmployerJobBoardActivity;
import com.voidstudio.quickcashreg.jobpost.JobPostActivity;

import users.Employer;

/**
 * Landing page for successful login
 */
public class InAppActivityEmployer extends AppCompatActivity implements View.OnClickListener {
    private static String username;
    private static String password;
    private static String email;

    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";

    private static Firebase firebase;
    private SharedPreferences sp;
    public static Employer employer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_app_activity_employer);
        // switch to this activity when log in successfully
        Intent thisIntent = getIntent();
        String welcomeMessage = thisIntent.getStringExtra(LogInActivity.WELCOME);
        TextView message = findViewById(R.id.Employer);
        message.setText(welcomeMessage);

        Button logOut = findViewById(R.id.logOutEmployer);
        logOut.setOnClickListener(InAppActivityEmployer.this);

        Button jobBoard = findViewById(R.id.JobBoardButton);
        jobBoard.setOnClickListener(InAppActivityEmployer.this);

        Button jobPost = findViewById(R.id.JobPost);
        jobPost.setOnClickListener(InAppActivityEmployer.this);


        firebase = Firebase.getInstance();
        sp = getSharedPreferences("login", MODE_PRIVATE);
        username = sp.getString(USERNAME,"");
        password = sp.getString(PASSWORD,"");
        email = firebase.getEmailAddress(username);
        employer = new Employer(username,password,email);

    }

    //Exact same method exists in employee, consider making new class
    private void resetLogInStatus() {
        SharedPreferences sharedPrefs = getSharedPreferences(LogInActivity.PREFERENCES, Context.MODE_PRIVATE); //
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(LogInActivity.ISLOGGED, false);
        editor.commit();
    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.logOutEmployer){
            resetLogInStatus();
            Intent logOutIntent = new Intent(InAppActivityEmployer.this, LogInActivity.class);
            startActivity(logOutIntent);
        }
        if(view.getId() == R.id.JobBoardButton){
            Intent jobBoardIntent = new Intent(InAppActivityEmployer.this, EmployerJobBoardActivity.class);
            jobBoardIntent.putExtra("USERNAME", username);
            jobBoardIntent.putExtra("PASSWORD", password);
            jobBoardIntent.putExtra("EMAIL",email);
            startActivity(jobBoardIntent);
        }
        if(view.getId() == R.id.JobPost){
            Intent jobPostIntent = new Intent(InAppActivityEmployer.this, JobPostActivity.class);
            jobPostIntent.putExtra("USERNAME", username);
            jobPostIntent.putExtra("PASSWORD", password);
            jobPostIntent.putExtra("EMAIL",email);
            startActivity(jobPostIntent);
        }
    }


}
