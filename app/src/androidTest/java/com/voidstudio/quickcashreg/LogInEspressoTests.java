package com.voidstudio.quickcashreg;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import android.content.Context;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.intent.Intents.intended;
//import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;


public class LogInEspressoTests {

  public static String EMPTY_STRING = "";
  @Rule
  public ActivityScenarioRule<LogIn> myRule = new ActivityScenarioRule<LogIn>
          (LogIn.class);
  public IntentsTestRule<LogIn> myIntentRule = new IntentsTestRule<>
          (LogIn.class);

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
    onView(withId(R.id.backToLog)).perform(click());
    intended(hasComponent(LogIn.class.getName()));
  }


}
