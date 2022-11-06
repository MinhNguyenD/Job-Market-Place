package com.voidstudio.quickcashreg.jobpost;

import android.location.Location;

public class Job {
  private String jobName;
  private String wage;
  private String tag;
  private String user;
  //private Location location;
  private static Location location;
  private String city;

  public Job(){

  }

  public Job(String jobName, String wage, String tag, String user, String city){
      this.jobName = jobName;
      this.wage = wage;
      this.tag = tag;
      this.user = user;
      this.city = city;

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

  public String getJobName() {
    return jobName;
  }

  public String getCity(){
    return city;
  }

  public void setLocation(Location location){
    Job.location = location;
  }
  public Location getLocation(){
    if(location == null) return null;
    return location;
  }



  @Override
  public String toString(){
    if(jobName!=null) {
      return "Title: " + jobName + "\nWage: " + wage + " Tag(s): " + tag + "\nPosted by:" + user;
    }
    else return "A job field is missing";
  }
}
