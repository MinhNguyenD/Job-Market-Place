package com.voidstudio.quickcashreg.jobPostEspresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.location.Location;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.voidstudio.quickcashreg.InAppActivityEmployer;
import com.voidstudio.quickcashreg.R;
import com.voidstudio.quickcashreg.jobpost.Job;
import com.voidstudio.quickcashreg.jobpost.JobDetailsActivity;
import com.voidstudio.quickcashreg.jobpost.JobPostActivity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import users.Employer;
import users.User;

public class JobDetailsEspressoTesting {
  private static final String JOB_NAME = "Job";

  private static final String JOB_WAGE = "Wage";

  private static final String JOB_TAG = "Tag";

  private static final String EMPLOYER = "Scumbag";

  private static final double[] COORDINATES = {4.20,69.0};
  static Location location;
  static User employer;
  static Job job;

  @Rule
  public ActivityScenarioRule<JobPostActivity> jobPostActivityActivityScenarioRule =
          new ActivityScenarioRule<JobPostActivity>(JobPostActivity.class);

  @BeforeClass
  public static void setup(){
    employer = new Employer("Username","Email","Password");
    job = new Job(JOB_NAME,JOB_WAGE,JOB_TAG,EMPLOYER);
    location = new Location("");
    location.setLatitude(COORDINATES[0]);
    location.setLongitude(COORDINATES[1]);
    job.setLocation(location);
    Intents.init();
  }

  @Test
  public void switchJobDetails(){
    InAppActivityEmployer.employer = (Employer)employer;
    job.setLocation(location);
    ((Employer) employer).myJobs.add(job);

    onView(withId(R.id.jobTitle)).perform(typeText(JOB_NAME));
    onView(withId(R.id.wageEdit)).perform(typeText(JOB_WAGE));
    onView(withId(R.id.wageEdit)).perform(closeSoftKeyboard());
    onView(withId(R.id.postJobButton)).perform(click());
    onView(withId(R.id.myJobsButton)).perform(click());
    Espresso.onIdle();
    //Now on job board Activity.
    onView(withId(R.id.recyclerView)).perform(click());
    Espresso.onIdle();
    intended(hasComponent(JobDetailsActivity.class.getName()));
  }

  @AfterClass
  public static void tearDown(){
    System.gc();
  }
}
