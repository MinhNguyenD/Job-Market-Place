package com.voidstudio.quickcashreg.jobpost;

import android.location.Location;

public class Job {
  private String jobName;
  private String wage;
  private String tag;
  private String user;
  private String expectedDuration;
  private String datePosted;
  private Location location;


  public Job(){

  }

  public Job(String jobName, String wage, String tag, String user){
    this.jobName = jobName;
    this.wage = wage;
    this.tag = tag;
    this.user = user;
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

  public Location getLocation() {
    return location;
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

  @Override
  public String toString(){
    return jobName+" "+wage+" "+tag+"  Posted by:" + user;
  }
}