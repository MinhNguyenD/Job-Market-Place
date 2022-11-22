package com.voidstudio.quickcashreg.Location;

import android.location.Location;
import android.location.LocationManager;

import com.voidstudio.quickcashreg.firebase.Firebase;

public class JobLocation implements ILocation{
  private final Firebase firebase;
  private String jobName;
  public JobLocation(String jobName){
    firebase = Firebase.getInstance();
    this.jobName = jobName;
  }
  @Override
  public Location getMyLocation() {
    Location jobLocation = new Location(LocationManager.GPS_PROVIDER);
    double[] coords = getLatLong();
    if(coords != null) {
      jobLocation.setLongitude(getLatLong()[0]);
      jobLocation.setLatitude(getLatLong()[1]);
      return jobLocation;
    }
    else return null;
  }

  @Override
  public void setLocation(Location location) {
      firebase.setJobCoordinates(jobName,location);
  }

  @Override
  public double[] getLatLong() {
    return firebase.getJobCoordinates(jobName);
  }

  @Override
  public double calculateDistance(Location l1, Location l2) {
    return 0;
  }

  @Override
  public boolean hasLocationAccessPermission() {
    return false;
  }
}
