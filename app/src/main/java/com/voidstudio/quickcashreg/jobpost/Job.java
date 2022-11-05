package com.voidstudio.quickcashreg.jobpost;

public class Job {
  private String jobName;
  private String wage;
  private String tag;
  private String user;
  //private Location location;
  private String hardLocation;

  public Job(){

  }

  public Job(String jobName, String wage, String tag, String user){
      this.jobName = jobName;
      this.wage = wage;
      this.tag = tag;
      this.user = user;
      hardLocation = "Location";
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

  @Override
  public String toString(){
    if(jobName!=null) {
      return "Title: " + jobName + "\nWage: " + wage + " Tag(s): " + tag + "\nPosted by:" + user;
    }
    else return "A job field is missing";
  }
}
