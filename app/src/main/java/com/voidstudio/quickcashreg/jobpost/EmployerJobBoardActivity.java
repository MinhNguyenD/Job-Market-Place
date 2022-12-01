package com.voidstudio.quickcashreg.jobpost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.voidstudio.quickcashreg.InAppActivityEmployer;
import com.voidstudio.quickcashreg.R;

import java.util.ArrayList;

import users.Employer;

public class EmployerJobBoardActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickListener {

  public static final String MY_PREFS = "MY_PREFS";
  RecyclerAdapter adapter;
  static String extractedJob;
  static String extractedWage;
  static String extractedTag;
  private String jobItem;
  private ArrayList<Job> jobList;
  private Employer employer;
  protected Intent intent;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.employer_job_board);
    employer = InAppActivityEmployer.employer;
    intent = new Intent(EmployerJobBoardActivity.this,JobDetailsActivity.class);
    if(getIntent().getExtras() != null) {
      Bundle bundle = getIntent().getExtras();
      extractedJob = bundle.getString("Job");
      extractedWage = bundle.getString("Wage");
      extractedTag = bundle.getString("Tag");
      jobItem = extractedJob + " " + extractedWage + " " + extractedTag;
    }
    this.loadSmallTasks();
  }

  protected void store2SharedPrefs(ArrayList<String> tasks) {
    SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    for (int i = 0; i < tasks.size(); i++) {
      editor.putString("selectedItem",tasks.get(i));
      if(jobList != null){
        jobList.get(i).getLocation();
        if(jobList.get(i).getLocation()!= null) {
          intent.putExtra("Location",
                  (jobList.get(i).jobLocation.getLatLong()[0]) +
                          "," + jobList.get(i).jobLocation.getLatLong()[1]);
        }else{
          intent.putExtra("Location", employer.getLocation().getLatitude()+" "+
                  employer.getLocation().getLongitude());
        }
      }
    }
    //also add the lat long
    editor.apply();
  }

  protected void loadSmallTasks() {
    ArrayList<String> tasks = new ArrayList<String>();
    jobList = employer.getMyJobs();
    for(Job job:jobList){
      tasks.add(job.toString());
    }
    this.store2SharedPrefs(tasks);

    RecyclerView recyclerView = findViewById(R.id.recyclerView);
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
