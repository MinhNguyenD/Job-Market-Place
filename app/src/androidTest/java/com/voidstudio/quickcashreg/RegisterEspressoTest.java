package com.voidstudio.quickcashreg;

import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RegisterEspressoTest {


    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);
    public IntentsTestRule<MainActivity> myIntentRule = new IntentsTestRule<>(MainActivity.class);


    @BeforeClass
    public static void setup(){
        Intents.init();
    }

    @AfterClass
    public static void tearDown(){
        System.gc();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.voidstudio.quickcashreg", appContext.getPackageName());
    }

    @Test
    public void checkIfRegistrationPageIsVisible() {
        onView(withId(R.id.roleList)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employee"))).perform(click());
        onView(withId(R.id.userName)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.eMail)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.password)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.passwordConfirm)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfPasswordIsValid() {
        onView(withId(R.id.roleList)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employee"))).perform(click());
        onView(withId(R.id.userName)).perform(typeText("helloWorld"));
        onView(withId(R.id.eMail)).perform(typeText("hello@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("1234567"));
        onView(withId(R.id.passwordConfirm)).perform(typeText("1234567"));
        onView(withId(R.id.buttonreg)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfEmailAddressIsValid(){
        onView(withId(R.id.roleList)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employee"))).perform(click());
        onView(withId(R.id.userName)).perform(typeText("dogisnotcat"));
        onView(withId(R.id.eMail)).perform(typeText("bcd.456@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("1234567"));
        onView(withId(R.id.passwordConfirm)).perform(typeText("1234567"));
        onView(withId(R.id.buttonreg)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfConfirmPasswordIsValid(){
        onView(withId(R.id.roleList)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employee"))).perform(click());
        onView(withId(R.id.userName)).perform(typeText("catisnotdog"));
        onView(withId(R.id.eMail)).perform(typeText("bcd.123@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("456123"));
        onView(withId(R.id.passwordConfirm)).perform(typeText("456123"));
        onView(withId(R.id.buttonreg)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfConfirmPasswordIsInvalid(){
        onView(withId(R.id.roleList)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employee"))).perform(click());
        onView(withId(R.id.userName)).perform(typeText("rainy123"));
        onView(withId(R.id.eMail)).perform(typeText("abc.456@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("456123"));
        onView(withId(R.id.passwordConfirm)).perform(typeText("123456"));
        onView(withId(R.id.buttonreg)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_CONFIRM_PASSWORD)));
    }

    @Test
    public void checkIfRegisterWithUsernameNotExisted(){
        onView(withId(R.id.roleList)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employee"))).perform(click());
        onView(withId(R.id.userName)).perform(typeText("student1"));
        onView(withId(R.id.eMail)).perform(typeText("bcd.456@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("456123"));
        onView(withId(R.id.passwordConfirm)).perform(typeText("456123"));
        onView(withId(R.id.buttonreg)).perform(click());
        onView(withId(R.id.userName)).perform(typeText("student2"));
        onView(withId(R.id.eMail)).perform(typeText("minh@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("456123"));
        onView(withId(R.id.passwordConfirm)).perform(typeText("456123"));
        onView(withId(R.id.buttonreg)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfRegisterWithUsernameExisted(){
        onView(withId(R.id.userName)).perform(typeText("student1"));
        onView(withId(R.id.eMail)).perform(typeText("bcd.456@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("456123"));
        onView(withId(R.id.passwordConfirm)).perform(typeText("456123"));
        onView(withId(R.id.buttonreg)).perform(click());
        onView(withId(R.id.userName)).perform(typeText("student1"));
        onView(withId(R.id.eMail)).perform(typeText("minh@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("456123"));
        onView(withId(R.id.passwordConfirm)).perform(typeText("456123"));
        onView(withId(R.id.buttonreg)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.USERNAME_EXISTED)));
    }

    @Test
    public void checkUserType(){
        onView(withId(R.id.roleList)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employee"))).perform(click());
        onView(withId(R.id.userName)).perform(typeText("student1"));
        onView(withId(R.id.eMail)).perform(typeText("bcd.456@dal.ca"));
        onView(withId(R.id.password)).perform(typeText("456123"));
        onView(withId(R.id.passwordConfirm)).perform(typeText("456123"));
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_STRING)));
    }

//    @Test
//    public void checkIfMoved2WelcomePage() {
//        onView(withId(R.id.userName)).perform(typeText("myNameisSun"));
//        onView(withId(R.id.eMail)).perform(typeText("sunny@dal.ca"));
//        onView(withId(R.id.password)).perform(typeText("456123"));
//        onView(withId(R.id.passwordConfirm)).perform(typeText("456123"));
//        onView(withId(R.id.buttonreg)).perform(click());
//        intended(hasComponent(LogIn.class.getName()));
//    }


}
