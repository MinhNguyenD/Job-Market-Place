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

import com.voidstudio.quickcashreg.R;

import java.util.ArrayList;

public class JobBoardActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickListener {

  public static final String MY_PREFS = "MY_PREFS";
  RecyclerAdapter adapter;
  static String extractedJob;
  static String extractedWage;
  static String extractedTag;
  private static String jobItem;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.employer_job_board);
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
      if (i % 2 == 0) {
        editor.putString(tasks.get(i), "Halifax");
      } else {
        editor.putString(tasks.get(i), "Dhaka");
      }
    }
    //also add the lat long
    editor.putString("Halifax", "44.65,-63.58");
    editor.putString("Dhaka", "23.81,90.41");
    editor.apply();
  }

  protected void loadSmallTasks() {
    ArrayList<String> tasks = new ArrayList<String>();
    if(jobItem != null) tasks.add(jobItem);
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
