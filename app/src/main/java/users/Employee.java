package users;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.voidstudio.quickcashreg.firebase.Firebase;
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
    private String preference;
    private ArrayList<Job> allJob;

    public Employee(String username, String email, int orderFinished, double minimumSalaryAccepted, Location location){
        this.username = username;
        this.email = email;
        firebase = Firebase.getInstance();
        this.orderFinished = orderFinished;
        this.minimumSalaryAccepted = minimumSalaryAccepted;
        startLocating(location);
        setLocation(location);
    }

    public Employee(String username, String email, int orderFinished, double minimumSalaryAccepted, Location location, Firebase firebase){
        this.username = username;
        this.email = email;
        this.firebase = firebase;
        this.orderFinished = orderFinished;
        this.minimumSalaryAccepted = minimumSalaryAccepted;
        locate.setLocation(location);
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

    public void setJob(String jobName, String jobWage, String duration, String jobTag){
        // Employee cannot set a job but employer can.
        Log.d("N/A", "Operation not applicable to this user type");
    }

    public void setPreference(String preference) {
        this.preference = preference;
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

    public Location getLocationFromDatabase(){
        double[] coords = firebase.getUserCoordinates(username);
        if(coords!= null) {
            Location location = new Location(" ");
            location.setLongitude(coords[0]);
            location.setLatitude(coords[1]);
            return location;
        }
        else return null;
    }
}


