package com.voidstudio.quickcashreg.jobpost;

public class Job {
  private String jobName;
  private String wage;
  private String tag;
  private String user;

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

  public String getJobName() {
    return jobName;
  }

  @Override
  public String toString(){
    return jobName+" "+wage+" "+tag+"  Posted by:" + user;
  }
}
