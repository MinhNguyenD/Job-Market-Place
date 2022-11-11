package users;

import android.location.Location;

import com.google.android.gms.tasks.Task;
import com.voidstudio.quickcashreg.Firebase;
import com.voidstudio.quickcashreg.jobpost.Job;

import java.util.ArrayList;


public class Employee extends User {
    protected int orderFinished;
    protected double minimumSalaryAccepted;

        // For observing employer
    public boolean newJobAlert;
    public boolean newJobSeen;

    public Firebase firebase;
    private static User employee;
    private static String preference;
    private ArrayList<Job> allJob;

    public Employee(String username, String email, int orderFinished, double minimumSalaryAccepted, Location location){
        this.username = username;
        this.email = email;
        firebase = new Firebase();
        this.orderFinished = orderFinished;
        this.minimumSalaryAccepted = minimumSalaryAccepted;
        this.location = location;
    }

    public Employee(String username, String email, int orderFinished, double minimumSalaryAccepted, Location location, Firebase firebase){
        this.username = username;
        this.email = email;
        this.firebase = firebase;
        this.orderFinished = orderFinished;
        this.minimumSalaryAccepted = minimumSalaryAccepted;
        this.location = location;
    }

    public void setMinimumSalaryAccepted(double minimumSalaryAccepted) {
        this.minimumSalaryAccepted = minimumSalaryAccepted;
    }

    public void setOrderFinished(int orderFinished) {
        this.orderFinished = orderFinished;
    }

    public double getMinimumSalaryAccepted() {
        return minimumSalaryAccepted;
    }

    public int getOrderFinished() {
        return orderFinished;
    }

    protected boolean validate(){
        return false;

    }

    public User getInstance(){
        if(employee == null){
            employee = new Employee(username, email, password);
        }
        return employee;
    }


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

    public Employee(String username, String email, String userType, String password){
    this.username = username;
    this.email = email;
    this.password = password;
    this.userType = userType;
//    firebase = Firebase.getInstance();
    }

    protected Task<Void> search(){
        return null;
    }

    public void setJob(String jobName, String jobWage, String jobTag){

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

    public String recommendInfo() {
        return "Name: " + this.username + " Email: " + this.email;
    }
}


