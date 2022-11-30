package com.voidstudio.quickcashreg.usertests;

import static org.junit.Assert.assertTrue;

import android.location.Location;

import com.voidstudio.quickcashreg.Location.ILocation;
import com.voidstudio.quickcashreg.firebase.Firebase;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import users.Employee;
import users.User;

public class EmployeeJUnitTest {


  static Firebase firebase;
  static ILocation locater;

  static Location location;

  static User employee;

  public static final double SALARY1 = 10.50;
  public static final double SALARY2 = 21.25;
  public static final String USERNAME = "Rick";
  public static final String EMAIL = "rick@roller.gotcha";
  public static final int ORDER_FINISHED_1 = 10;
  public static final int ORDER_FINISHED_2 = -5;



  @BeforeClass
  public static void setup(){
    firebase = Mockito.mock(Firebase.class);
    locater= Mockito.mock(ILocation.class);
    location = Mockito.mock(Location.class);
    employee = new Employee(USERNAME,EMAIL,ORDER_FINISHED_1,SALARY1,location);
  }

  @Test
  public void setsCorrectSalary(){
    employee.setMinimumSalaryAccepted(SALARY2);
    assertTrue(employee.getMinimumSalaryAccepted() == SALARY2);
  }


}
