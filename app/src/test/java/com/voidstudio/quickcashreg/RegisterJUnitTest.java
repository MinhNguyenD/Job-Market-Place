package com.voidstudio.quickcashreg;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RegisterUnitTest {
    static MainActivity mainActivity;

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
        assertTrue(mainActivity.isValidEmailAddress("bcd456@dal.ca"));
    }

    //check if email is invalid
    @Test
    public void checkIfEmailIsInvalid() {
        assertFalse(mainActivity.isValidEmailAddress("bcd456dal.ca"));
    }

    //check if password is valid (length) : minimum 6 characters
    @Test
    public void checkIfPasswordIsValid(){
        assertTrue(mainActivity.isValidPassword("123abcd"));
        assertTrue(mainActivity.isValidPassword("123445"));
        assertFalse(mainActivity.isValidPassword("Hello"));
    }

    //check if password and confirmPassword are the same
    @Test
    public void checkIfValidConfirmPassword(){
        String v1 = "team07";
        String v2 = "team07";
        String v3 = "team70"; //wrong confirmPassword

        assertTrue(mainActivity.isValidConfirmPassword(v1,v2));
        assertFalse(mainActivity.isValidConfirmPassword(v1,v3));
    }

}
