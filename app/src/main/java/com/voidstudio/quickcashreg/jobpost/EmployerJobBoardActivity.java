package com.voidstudio.quickcashreg.jobpost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.voidstudio.quickcashreg.InAppActivityEmployee;
import com.voidstudio.quickcashreg.InAppActivityEmployer;
import com.voidstudio.quickcashreg.Location.GoogleMapsActivity;
import com.voidstudio.quickcashreg.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import users.Employee;
import users.Employer;
import users.User;

public class EmployerJobBoardActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickListener {

  public static final String MY_PREFS = "MY_PREFS";
  RecyclerAdapter adapter;
  static String extractedJob;
  static String extractedWage;
  static String extractedTag;
  private static Employer employer;
  private static Employee employee;
  private static User user;
  private static Job job;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.employer_job_board);
    if(InAppActivityEmployer.employer != null){
      employer = InAppActivityEmployer.employer;
      user = employer;
    }
    if(InAppActivityEmployee.employee != null){
      employee = InAppActivityEmployee.employee;
      user = employee;
    }
    Bundle bundle = getIntent().getExtras();
    if(user != null && bundle.getString("Job")!=null&& user.isEmployee()){
      extractedJob = bundle.getString("Job");
      extractedWage = bundle.getString("Wage");
      extractedTag = bundle.getString("Tag");
      job = new Job(extractedJob,extractedWage,extractedTag,user.getUsername(),cityFromLocationGetter(employer.getLocation()));
      job.setLocation(employer.getLocation());
    }
    this.loadSmallTasks();
  }

  protected void storeTasksAndLocation2SharedPrefs(ArrayList<Job> jobs) {
    SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    for (int i = 0; i < jobs.size(); i++) {
      editor.putString("Task Location",jobs.get(i).getLocation().toString());
      editor.putString("City",jobs.get(i).getCity());
    }
    //also add the lat long (Replace with location feature)
   // editor.putString("JobCity", "44.65,-63.58");
   // editor.putString("Dhaka", "23.81,90.41");
    editor.apply();
  }

  protected void loadSmallTasks() {
    ArrayList<String> tasks = new ArrayList<String>();
    ArrayList<Job> jobList = new ArrayList<Job>();
    if(employer != null) {
      jobList = employer.getMyJobs();
      if(job != null){
        jobList.add(job);
      }
      if(employer.getMyJobs().isEmpty()) tasks.add("NO JOBS");
      for(Job job:jobList){
        job.setLocation(employer.getLocation());
        tasks.add(job.toString());
      }
    }
    this.storeTasksAndLocation2SharedPrefs(jobList);

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
    intent.putExtra("Distance to you", calculateDistanceKM(getSourceLocation(),getDestLocation()));
    startActivity(intent);
  }

  protected void showOnMap(String selectedTask) {
    double[] taskLatLng = getLongLat(selectedTask);
    Intent intent = new Intent(this, GoogleMapsActivity.class);
    intent.putExtra("taskLocation", taskLatLng);
    startActivity(intent);
  }

  public String cityFromLocationGetter(Location location){
    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    String city = "";
    try {
      List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),
              1);
      city = addresses.get(0).getAddressLine(0);

    } catch (IOException e) {
      Toast.makeText(this,"BadLocation",Toast.LENGTH_SHORT).show();
    }
    return city;
  }

  protected Location getSourceLocation() {
    Location source = user.getLocation();
    double lat = source.getLatitude();
    double longit = source.getLongitude();
    String latLong = lat+","+longit;
    double[] coord = getLongLat(latLong);
    Location location = new Location(LocationManager.GPS_PROVIDER);
    location.setLongitude(coord[0]);
    location.setLatitude(coord[1]);
    return location;
  }

  protected Location getDestLocation() {
    Location destination = employer.getLocation();//replace with job somehow
    double lat = destination.getLatitude();
    double longit = destination.getLongitude();
    String latLong = lat+","+longit;
    double[] coord = getLongLat(latLong);
    Location location = new Location(LocationManager.GPS_PROVIDER);
    location.setLongitude(coord[0]);
    location.setLatitude(coord[1]);
    return location;
  }

  protected double[] getLongLat(String locationValue) {
    String[] parts = locationValue.split(",");
    double longitude = Double.parseDouble(parts[0]);
    double latitude = Double.parseDouble(parts[1]);
    return new double[]{longitude, latitude};
  }

  public double calculateDistanceKM(Location source, Location destination){
    double sourceToDestination = source.distanceTo(destination)/1000;
    return sourceToDestination;
  }

  @Override
  public void onItemClick(View view, int position) {
    //Toast.makeText(this, this.adapter.getItem(position), Toast.LENGTH_LONG).show();
    showDetails(this.adapter.getItem(position));
    //showOnMap(this.adapter.getItem(position));
  }



}
