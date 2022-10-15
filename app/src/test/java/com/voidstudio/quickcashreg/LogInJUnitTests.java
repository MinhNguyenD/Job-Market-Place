package com.voidstudio.quickcashreg;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class LogInJUnitTests {



    @BeforeClass
    public static void setup(){

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
        assertTrue("This username does not exist",
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



}
