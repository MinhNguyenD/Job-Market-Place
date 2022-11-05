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

import users.Employee;
import users.Employer;

public class InAppActivityEmployee extends AppCompatActivity implements View.OnClickListener {
  private static String username;
  private static String password;
  private static String email;

  public static final String USERNAME = "Username";
  public static final String PASSWORD = "Password";

  private static Firebase firebase;

  String channelID = "Notification";
  int notificationID = 0;
  private Employee employee;

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

    // Get current Employee
    employee = new Employee(username, password);

    // Check if the employee has seen the new job posting or not, if not, pop up the notification
    SharedPreferences jobPostNoti = getSharedPreferences("jobPost", MODE_PRIVATE);
    SharedPreferences.Editor editor = jobPostNoti.edit();
    employee.newJobAlert = jobPostNoti.getBoolean("newJobAlert", true);
    employee.newJobSeen = jobPostNoti.getBoolean("newJobSeen", true);

    if (employee.newJobAlert && !employee.newJobSeen) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        jobNotification();
        editor.putBoolean("newJobSeen", true);
      }
    }


    Button logOut = findViewById(R.id.employeeLogOut);
    logOut.setOnClickListener(InAppActivityEmployee.this);

  }

  //This method could be in its own class, many different activities will need it
  private void resetLogInStatus() {
    SharedPreferences sharedPrefs = getSharedPreferences(LogInActivity.PREFERENCES, Context.MODE_PRIVATE); //
    SharedPreferences.Editor editor = sharedPrefs.edit();
    editor.putBoolean(LogInActivity.ISLOGGED, false);
    editor.commit();

    sharedPrefs = getSharedPreferences("jobPost", MODE_PRIVATE);
    sharedPrefs.edit().clear().commit();
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  protected void jobNotification() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID);
    builder.setSmallIcon(R.drawable.information);
    builder.setContentTitle("New Job Posting Alert!");
    builder.setContentText("New Job is Posted!!");
    builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

    Intent jobBoardIntent = new Intent(this, InAppActivityEmployee.class);
    PendingIntent pIntent = PendingIntent.getActivity(this, 0, jobBoardIntent, PendingIntent.FLAG_IMMUTABLE);
    builder.setContentIntent(pIntent);

    NotificationManager manager = getSystemService(NotificationManager.class);
    manager.createNotificationChannel(new NotificationChannel(channelID, "custom", NotificationManager.IMPORTANCE_HIGH));
    manager.notify(notificationID, builder.build());

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
