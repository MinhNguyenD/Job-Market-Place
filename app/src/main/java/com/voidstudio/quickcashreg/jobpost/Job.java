package com.voidstudio.quickcashreg.jobpost;

import android.location.Location;

import com.voidstudio.quickcashreg.Location.ILocation;
import com.voidstudio.quickcashreg.Location.JobLocation;
import com.voidstudio.quickcashreg.firebase.Firebase;

public class Job {
  private String jobName;
  private String wage;
  private String tag;
  private String user;
  private String expectedDuration;
  private String datePosted;
  private Location location;
  private String hardLocation;
  public ILocation jobLocation;



  public Job(){

  }

  public Job(String jobName, String wage, String tag, String user){
    this.jobName = jobName;
    this.wage = wage;
    this.tag = tag;
    this.user = user;
    jobLocation = new JobLocation(jobName);
  }


  public void setLocation(Location location) {
    if(location != null) {
      jobLocation.setLocation(location);
    }
  }

  public Location getLocation() {
    if(jobLocation != null) {
      return jobLocation.getMyLocation();
    }
    else{
      return null;
    }
  }



  public String getUser() {
    return user;
  }

  public String getTag() {
    return tag;
  }

  public String getWage() {
    return wage;
  }



  public String getDuration() {
    return expectedDuration;
  }

  public String getDatePosted() {
    return datePosted;
  }

  public String getJobName() {
    return jobName;
  }
  public static Job getFromString(String jobString){
    Firebase firebase = Firebase.getInstance();
    String jName = jobString.split(" ")[0];
    return firebase.getJob(jName);
  }

  @Override
  public String toString(){
    return jobName+" "+wage+" "+tag+"  Posted by:" + user;
  }
}
