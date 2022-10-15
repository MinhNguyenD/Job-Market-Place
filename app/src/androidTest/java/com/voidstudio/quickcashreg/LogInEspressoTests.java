package com.voidstudio.quickcashreg;

import android.content.Context;
import android.support.test.espresso.intent.Intents;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;


public class LogInEspressoTests {
    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<MainActivity>
            (MainActivity.class);
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

    /** AT-I **/
    public void isPasswordShowed() {
        onView(withId(R.id.textPassword)).perform(typeText("password123"));
        onView(withId(R.id.showHidePassword)).perform(click());
        onView(withId(R.id.textPassword)).check(matches(withText("password123")));
        onView(withId(R.id.showHidePassword)).check(matches(withText("Hide Password")));
    }
    /** AT-II **/
    public void isPasswordHiddenAfterClickButtonTwice() {
        onView(withId(R.id.textPassword)).perform(typeText("password123"));
        onView(withId(R.id.showHidePassword)).perform(click());
        onView(withId(R.id.showHidePassword)).perform(click());
        onView(withId(R.id.textPassword)).check(matches(isPasswordHidden()));
    }

}
