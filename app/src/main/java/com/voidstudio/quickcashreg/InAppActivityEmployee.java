package com.voidstudio.quickcashreg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InAppActivityEmployee extends AppCompatActivity implements View.OnClickListener {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.in_app_activity_employee);

    // switch to this activity when log in successfully
    Intent thisIntent = getIntent();
    String welcomeMessage = thisIntent.getStringExtra(LogInActivity.WELCOME);
    TextView message = findViewById(R.id.Employee);
    message.setText(welcomeMessage);

    Button logOut = findViewById(R.id.employeeLogOut);
    logOut.setOnClickListener(InAppActivityEmployee.this);

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

  }

}
