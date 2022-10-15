package com.voidstudio.quickcashreg;

import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
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


  /*** UAT-I ***/
  @Test
  public void checkIfMovedToLogInPage() {
    Espresso.onView(withId(R.id.loginButton)).perform(click());
    intended(hasComponent(LogIn.class.getName()));
  }

  /** UAT-III **/
  @Test
  public void checkIfMovedToRegisterPage(){
    Espresso.onView(withId(R.id.loginButton)).perform(click());
    Espresso.onView(withId(R.id.logInRegisterButton)).perform(click());
    intended(hasComponent(MainActivity.class.getName()));
  }





}
