package users;

import android.location.Location;

import com.google.android.gms.tasks.Task;
import com.voidstudio.quickcashreg.Firebase;

public class Employee extends User {
    protected int orderFinished;
    protected double minimumSalaryAccepted;
    Firebase firebase;
    private User employee;

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

    public Employee(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
        firebase = new Firebase();
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
            employee = new Employer(username, email, password);
        }
        return employee;
    }

    public String recommendInfo() {
        return "Name: " + this.username + " Email: " + this.email;
    }
}
