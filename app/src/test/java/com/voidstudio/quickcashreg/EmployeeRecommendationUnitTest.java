package com.voidstudio.quickcashreg;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.location.Location;

import com.voidstudio.quickcashreg.EmployeeRecommendation.EmployeeRecommendation;
import com.voidstudio.quickcashreg.firebase.Firebase;
import com.voidstudio.quickcashreg.jobpost.Job;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import users.Employee;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class EmployeeRecommendationUnitTest {

    static Location location1;
    static Location location2;
    static Firebase firebase;


    @BeforeClass
    public static void setup() {
        location1 = Mockito.mock(Location.class);
        location2 = Mockito.mock(Location.class);
        firebase = Mockito.mock(Firebase.class);
        Mockito.when(location2.distanceTo(location1)).thenReturn((float)30.0);
    }

    @AfterClass
    public static void tearDown() { System.gc();
    }

    // test if the employee is valid for recommendation
    @Test
    public void getRecommendationValidEmployee(){
        double maxDistance = 50;
        Employee employee1 =new Employee("steven", "steven@dal.ca", 20, 20, location1, firebase);

        Job pilot = new Job("pilot", "30", "Tag 1", "Boss");
        pilot.setLocation(location2);

       assertTrue(EmployeeRecommendation.isValidEmployee(pilot, employee1 ,maxDistance));

    }

    //test if the employee is not valid for recommendation
    //because salary of the job and the minimum salary accepted of the employee are the same (30/hour)
    @Test
    public void getRecommendationInvalidEmployee(){
        double maxDistance = 50;
        Employee employee2 = new Employee("callum", "cal@dal.ca", 30,30, location1, firebase);
        Job pilot = new Job("pilot", "30", "Tag 1", "Boss");
        pilot.setLocation(location2);
        assertFalse(EmployeeRecommendation.isValidEmployee(pilot, employee2 ,maxDistance));
    }


}
