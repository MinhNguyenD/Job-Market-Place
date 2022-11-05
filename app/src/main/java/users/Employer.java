package users;

import com.google.android.gms.tasks.Task;
import com.voidstudio.quickcashreg.Firebase;
import com.voidstudio.quickcashreg.jobpost.Job;

import java.util.ArrayList;

public class Employer extends User {
  private static Firebase firebase;
  private static Employer employer;
  private ArrayList<Job> myJobs;

  protected Employee employee;

  public Employer(String username, String email, String password){
    this.username = username;
    this.email = email;
    this.password = password;
    firebase = Firebase.getInstance();
    myJobs = firebase.getJobsFromUser(username);
  }

  protected Task<Void> search(){
    return null;
  }

  protected boolean validate(){
    return false;
  }


  public void setJob(String jobName, String jobWage, String jobTag){
    firebase.addJob(jobName,jobWage,jobTag,username);
  }
  public User getInstance(){
    if(employer == null){
      employer = new Employer(username, email, password);
    }
    return employer;
  }

  public ArrayList<Job> getMyJobs(){
    return myJobs;
  }

  public void notifyEmployee() {
    employee.update();
  }

}
