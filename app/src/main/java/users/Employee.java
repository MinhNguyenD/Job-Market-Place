package users;

import com.google.android.gms.tasks.Task;
import com.voidstudio.quickcashreg.Firebase;
import com.voidstudio.quickcashreg.jobpost.Job;

import java.util.ArrayList;


public class Employee extends User {

    // For observing employer
    public boolean newJobAlert;
    public boolean newJobSeen;

    public Firebase firebase;
    private static User employee;
    private static String preference;
    private ArrayList<Job> allJob;


    public Employee(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
        firebase = Firebase.getInstance();
        allJob = firebase.getAllJobs();
    }

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
        firebase = Firebase.getInstance();
    }

    protected Task<Void> search(){
        return null;
    }

    protected boolean validate(){
        return false;

    }
    public void setJob(String jobName, String jobWage, String jobTag){

    }
    public User getInstance(){
        if(employee == null){
            employee = new Employee(username, email, password);
        }
        return employee;
    }

    public void setPreference(String preference) {
        Employee.preference = preference;
    }

    public String getPreference() {
        return preference;
    }

    public ArrayList<Job> getAllJobs() {
        return allJob;
    }

}


