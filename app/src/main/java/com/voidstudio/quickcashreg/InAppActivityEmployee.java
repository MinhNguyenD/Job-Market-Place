package com.voidstudio.quickcashreg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.voidstudio.quickcashreg.jobpost.EmployeeJobBoardActivity;
import com.voidstudio.quickcashreg.jobpost.SavePreferenceActivity;

import users.Employee;

public class InAppActivityEmployee extends AppCompatActivity implements View.OnClickListener {
  private static Firebase firebase;

  private SharedPreferences sp;
  private static String username;
  private static String password;
  private static String email;
  public static Employee employee;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.in_app_activity_employee);

    // switch to this activity when log in successfully
    Intent thisIntent = getIntent();
    String welcomeMessage = thisIntent.getStringExtra(LogInActivity.WELCOME);
    TextView message = findViewById(R.id.Employee);
    message.setText(welcomeMessage);
//    String username = thisIntent.getStringExtra("username");
//    String password = thisIntent.getStringExtra("password");


    Button logOut = findViewById(R.id.employeeLogOut);
    logOut.setOnClickListener(InAppActivityEmployee.this);

    Button jobBoard = findViewById(R.id.employeeJobBoard);
    jobBoard.setOnClickListener(InAppActivityEmployee.this);

    Button savePreference = findViewById(R.id.setPreference);
    savePreference.setOnClickListener(InAppActivityEmployee.this);

    firebase = Firebase.getInstance();
    sp = getSharedPreferences("login", MODE_PRIVATE);
    username = sp.getString("Username","");
    password = sp.getString("Password","");
    email = sp.getString("EMAIL","");

    employee = new Employee(username, email, password);
  }

  //This method could be in its own class, many different activities will need it
  private void resetLogInStatus() {
    SharedPreferences sharedPrefs = getSharedPreferences(LogInActivity.PREFERENCES, Context.MODE_PRIVATE); //
    SharedPreferences.Editor editor = sharedPrefs.edit();
    editor.putBoolean(LogInActivity.ISLOGGED, false);
    editor.commit();
  }

  @Override
  public void onClick(View view){

    if(view.getId() == R.id.employeeLogOut){
      resetLogInStatus();
      Intent logOutIntent = new Intent(InAppActivityEmployee.this, LogInActivity.class);
      startActivity(logOutIntent);
    }

    if(view.getId() == R.id.employeeJobBoard){
      Intent jobBoardIntent = new Intent(InAppActivityEmployee.this, EmployeeJobBoardActivity.class);
      jobBoardIntent.putExtra("USERNAME", username);
      jobBoardIntent.putExtra("PASSWORD", password);
      jobBoardIntent.putExtra("EMAIL",email);
      startActivity(jobBoardIntent);
    }

    if (view.getId() == R.id.setPreference) {
      Intent savePreference = new Intent(InAppActivityEmployee.this, SavePreferenceActivity.class);
      startActivity(savePreference);
    }

  }

}
