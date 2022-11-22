package com.voidstudio.quickcashreg.jobpost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.voidstudio.quickcashreg.firebase.Firebase;
import com.voidstudio.quickcashreg.InAppActivityEmployee;
import com.voidstudio.quickcashreg.R;

import java.util.ArrayList;

import users.Employee;

public class EmployeeJobBoardActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickListener {

  public static final String MY_PREFS = "MY_PREFS";
  RecyclerAdapter adapter;
  static String extractedJob;
  static String extractedWage;
  static String extractedTag;
  private static Employee employee;
  private static String preference;
  private static Job job;
  private static Firebase firebase;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.employee_job_board);
    employee = InAppActivityEmployee.employee;
//    Bundle bundle = getIntent().getExtras();
//    if(employer != null && bundle.getString("Job")!=null){
//      extractedJob = bundle.getString("Job");
//      extractedWage = bundle.getString("Wage");
//      extractedTag = bundle.getString("Tag");
//      job = new Job(extractedJob,extractedWage,extractedTag,employer.getUsername());
//    }
    this.loadSmallTasks();
  }

//  @Override
//  protected void onResume() {
//    super.onResume();
//    employee = InAppActivityEmployee.employee;
//    if (employee == null) {
//      Toast.makeText(this, "Employee is null", Toast.LENGTH_SHORT).show();
//    } else {
//      Toast.makeText(this, "Employee is not null", Toast.LENGTH_SHORT).show();
//    }
//    //preference = employee.getPreference();
//    Toast.makeText(this, "Im back!", Toast.LENGTH_SHORT).show();
//    loadSmallTasks();
//  }

  protected void storeTasksAndLocation2SharedPrefs(ArrayList<String> tasks) {
    SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    for (int i = 0; i < tasks.size(); i++) {
      if (i % 2 == 0) {
        //Replace with location feature
        editor.putString(tasks.get(i), "Halifax");
      } else {
        editor.putString(tasks.get(i), "Dhaka");
      }
    }
    //also add the lat long (Replace with location feature)
    editor.putString("JobCity", "44.65,-63.58");
    editor.putString("Dhaka", "23.81,90.41");
    editor.apply();
  }

  protected void loadSmallTasks() {
    ArrayList<String> tasks = new ArrayList<>();
    ArrayList<Job> jobList = employee.getAllJobs();
    String preference = employee.getPreference();

    if (preference != null && !preference.equals("")) {
      for (Job job: jobList) {
        if (job.getTag().equals(employee.getPreference())) {
          Toast.makeText(this, job.getTag(), Toast.LENGTH_SHORT).show();
          tasks.add(job.toString());
        }
      }
    }
    else {
      for (Job job: jobList) {
        tasks.add(job.toString());
      }
    }
//    if(employer != null) {
//      ArrayList<Job> jobList = employer.getMyJobs();
//      if(job != null) jobList.add(job);
//      if(employer.getMyJobs().isEmpty()) tasks.add("NO JOBS");
//      for(Job job:jobList){
//        tasks.add(job.toString());
//      }
//    }
    this.storeTasksAndLocation2SharedPrefs(tasks);

    RecyclerView recyclerView = findViewById(R.id.employeeJobs);
    LinearLayoutManager loManager = new LinearLayoutManager(this);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
            loManager.getOrientation());
    recyclerView.setLayoutManager(loManager);
    recyclerView.addItemDecoration(dividerItemDecoration);
    this.adapter = new RecyclerAdapter(this, tasks);
    this.adapter.setItemClickListener(this);
    recyclerView.setAdapter(adapter);
  }

  protected void showDetails(String selectedTask) {
    Intent intent = new Intent(this, JobDetailsActivity.class);
    intent.putExtra("selectedItem", selectedTask);
    startActivity(intent);
  }

 // protected void showOnMap(String selectedTask) {
 //   Intent intent = new Intent(this, GoogleMapsActivity.class);
 //   intent.putExtra("taskLocation", "-44,63");
 //   startActivity(intent);
 // }

  @Override
  public void onItemClick(View view, int position) {
    //Toast.makeText(this, this.adapter.getItem(position), Toast.LENGTH_LONG).show();
    showDetails(this.adapter.getItem(position));
    //showOnMap(this.adapter.getItem(position));
  }



}
