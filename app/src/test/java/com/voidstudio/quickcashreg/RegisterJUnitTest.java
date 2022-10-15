package com.voidstudio.quickcashreg;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RegisterJUnitTest {

    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void tearDown() { System.gc();
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    //check if email is correctly entered
    @Test
    public void checkIfEmailIsValid(){
        assertTrue(MainActivity.isValidEmailAddress("bcd456@dal.ca"));
    }

    //check if email is invalid
    @Test
    public void checkIfEmailIsInvalid() {
        assertFalse(MainActivity.isValidEmailAddress("bcd456dal.ca"));
    }

    //check if password is valid (length) : minimum 6 characters
    @Test
    public void checkIfPasswordIsValid(){
        assertTrue(MainActivity.isValidPassword("123abcd"));
        assertTrue(MainActivity.isValidPassword("123445"));
        assertFalse(MainActivity.isValidPassword("Hello"));
    }

    //check if password and confirmPassword are the same
    @Test
    public void checkIfValidConfirmPassword(){
        String v1 = "team07";
        String v2 = "team07";
        String v3 = "team70"; //wrong confirmPassword

        assertTrue(MainActivity.isValidConfirmPassword(v1,v2));
        assertFalse(MainActivity.isValidConfirmPassword(v1,v3));
    }

}
