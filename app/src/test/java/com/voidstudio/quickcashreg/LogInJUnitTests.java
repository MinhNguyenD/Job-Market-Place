package com.voidstudio.quickcashreg;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class LogInJUnitTests {
    /*** UAT-2 ***/
    @Test
    void noExistingUser(){
        String fakeUser = "FakeUser123";
        //random name for time being
        assertTrue("This username does not exist",logInMethod(fakeUser,"password"));
    }
}
