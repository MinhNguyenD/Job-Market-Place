package com.voidstudio.quickcashreg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class LogInJUnitTests {
    static LogIn employeeLogIn;
    static LogIn employerLogIn;


    @BeforeClass
    public static void setup(){
        employeeLogIn = Mockito.mock(LogIn.class);
        employerLogIn = Mockito.mock(LogIn.class);
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
                LogIn.logIn(fakeUser,"password"));
    }
    /*** UAT-4 ***/
    @Test
    public void incorrectPass(){
        //Will always fail
        String realUser = "RealUser123";
        String fakePass = "LordOfCyberSecurityImpenetrableSuperServerDefenseNoHackingHere123456789!@#$%^&*()";
        assertFalse("Password is incorrect",LogIn.logIn(realUser,fakePass));
    }

    /**Test if isEmployeeMethodWorks**/
    @Test
    public void checkIfEmployeeIsEmployee(){
        Mockito.when(employeeLogIn.isEmployee()).thenReturn(true);
        assertTrue(employeeLogIn.isEmployee());
    }

    @Test public void checkIfEmployer(){
        Mockito.when(employerLogIn.isEmployee()).thenReturn(false);
        assertFalse(employerLogIn.isEmployee());
    }







}
