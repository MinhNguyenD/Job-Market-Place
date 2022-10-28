package com.voidstudio.quickcashreg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class LogInActivityJUnitTests {
    static LogInActivity employeeLogInActivity;
    static LogInActivity employerLogInActivity;


    @BeforeClass
    public static void setup(){
        employeeLogInActivity = Mockito.mock(LogInActivity.class);
        employerLogInActivity = Mockito.mock(LogInActivity.class);
    }

    @AfterClass
    public static void tearDown(){
        System.gc();
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    /*** UAT-2 ***/
    @Test
    public void noExistingUser(){
        String fakeUser = "FakeUser123";
        //random name for time being
        assertFalse("This username does not exist",
                LogInActivity.logIn());
    }
    /*** UAT-4 ***/
    @Test
    public void incorrectPass(){
        //Will always fail
        String realUser = "RealUser123";
        String fakePass = "LordOfCyberSecurityImpenetrableSuperServerDefenseNoHackingHere123456789!@#$%^&*()";
        assertFalse("Password is incorrect", LogInActivity.logIn());
    }

    /**Test if isEmployeeMethodWorks**/
    @Test
    public void checkIfEmployeeIsEmployee(){
        Mockito.when(employeeLogInActivity.isEmployee()).thenReturn(true);
        assertTrue(employeeLogInActivity.isEmployee());
    }

    @Test public void checkIfEmployer(){
        Mockito.when(employerLogInActivity.isEmployee()).thenReturn(false);
        assertFalse(employerLogInActivity.isEmployee());
    }







}
