package com.voidstudio.quickcashreg;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class InAppActivityEmployee extends AppCompatActivity implements View.OnClickListener {
  String channelID = "Notification";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.in_app_activity_employee);

    // switch to this activity when log in successfully
    Intent thisIntent = getIntent();
    String welcomeMessage = thisIntent.getStringExtra(LogInActivity.WELCOME);
    TextView message = findViewById(R.id.Employee);
    message.setText(welcomeMessage);
    String username = thisIntent.getStringExtra("username");
    String password = thisIntent.getStringExtra("password");


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

  @RequiresApi(api = Build.VERSION_CODES.O)
  protected void addJobNotification() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID);
    builder.setContentTitle("New Job Posting Alert!");
    builder.setContentText("");
    builder.setTicker("");
    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

    // change this to job board activity later
    Intent jobBoardIntent = new Intent(this, InAppActivityEmployee.class);
    PendingIntent pIntent = PendingIntent.getActivity(getBaseContext(), 0, jobBoardIntent, PendingIntent.FLAG_IMMUTABLE);

    builder.setContentIntent(pIntent);
    NotificationManagerCompat manager = NotificationManagerCompat.from(this);
    manager.createNotificationChannel(new NotificationChannel(channelID, "custom", NotificationManager.IMPORTANCE_HIGH));
    manager.notify(0, builder.build());
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
