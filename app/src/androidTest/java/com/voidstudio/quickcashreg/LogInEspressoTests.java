package com.voidstudio.quickcashreg;

import android.content.Context;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;



public class LogInEspressoTests {
    @Rule
    public ActivityScenarioRule<LogIn> myRule = new ActivityScenarioRule<LogIn>
            (LogIn.class);
    @BeforeClass
    public static void setup(){
        Intents.init();
    }

    @AfterClass
    public static void tearDown(){
        System.gc();
    }

    /**
     * Method check the input type of password
     * Reference: https://stackoverflow.com/questions/48395282/how-to-get-input-type-of-edittext-in-espresso-testing
     * @return true if password is hidden; false otherwise
     */
    private Matcher<View> isPasswordHidden() {
        return new BoundedMatcher<View, EditText>(EditText.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("Password is hidden");
            }

            @Override
            public boolean matchesSafely(EditText editText) {
                //returns true if password is hidden
                return editText.getTransformationMethod() instanceof PasswordTransformationMethod;
            }
        };
    }

    @Test
    /** AT-I **/
    public void isPasswordShowed() {
        Espresso.onView(withId(R.id.textPassword)).perform(typeText("password123"));
        Espresso.onView(withId(R.id.showHidePassword)).perform(click());
        Espresso.onView(withId(R.id.textPassword)).check(matches(withText("password123")));
        Espresso.onView(withId(R.id.showHidePassword)).check(matches(withText("Hide Password")));
    }

    @Test
    /** AT-II **/
    public void isPasswordHiddenAfterClickButtonTwice() {
        Espresso.onView(withId(R.id.textPassword)).perform(typeText("password123"));
        Espresso.onView(withId(R.id.showHidePassword)).perform(click());
        Espresso.onView(withId(R.id.showHidePassword)).perform(click());
        Espresso.onView(withId(R.id.textPassword)).check(matches(isPasswordHidden()));
    }

    @Test
    /** AT-III **/
    public void isPasswordHiddenBeforeToggle() {
        Espresso.onView(withId(R.id.textPassword)).perform(typeText("password123"));
        Espresso.onView(withId(R.id.textPassword)).check(matches(isPasswordHidden()));
    }


  /*** UAT-I (LogIn)***/
  @Test
  public void checkIfMovedToLogInPage() {
    Espresso.onView(withId(R.id.logInRegisterButton)).perform(click());
    Espresso.onView(withId(R.id.loginButton)).perform(click());
    intended(hasComponent(LogIn.class.getName()));
  }

  /** UAT-III (LogIN) **/
  @Test
  public void checkIfMovedToRegisterPage(){
    Espresso.onView(withId(R.id.logInRegisterButton)).perform(click());
    intended(hasComponent(MainActivity.class.getName()));
  }





}
