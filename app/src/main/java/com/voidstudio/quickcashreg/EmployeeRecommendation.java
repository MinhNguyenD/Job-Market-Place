package com.voidstudio.quickcashreg;

import android.location.Location;

import com.voidstudio.quickcashreg.Location.GPS;
import com.voidstudio.quickcashreg.jobpost.Job;

import java.util.ArrayList;

import users.Employee;

public class EmployeeRecommendation {
    private final Firebase firebase;
    GPS gps ;

    public EmployeeRecommendation(){
        firebase = new Firebase();
    }

    public Firebase getFirebase() {
        return firebase;
    }

    public ArrayList<Employee> getRecommendation (Job job, ArrayList<Employee> employees, double maxDistance) {
        ArrayList<Employee> res = new ArrayList<>();


        for (Employee employee: employees) {
            
            if (isValidEmployee(job, employee, maxDistance)) {
                res.add(employee);
            }
        }

        return res;
    }

    public static boolean isValidEmployee(Job job, Employee employee, double maxDistance){
        Location jobLocation = job.getLocation();
        Location employeeLocation = employee.getLocation();
        double distance = jobLocation.distanceTo(employeeLocation);

        boolean validSalary = employee.getMinimumSalaryAccepted() < Double.parseDouble(job.getWage());
        boolean validExperience = employee.getOrderFinished() > 3;
        boolean validDistance = distance < maxDistance;

        return validDistance & validExperience & validSalary;
    }
}
