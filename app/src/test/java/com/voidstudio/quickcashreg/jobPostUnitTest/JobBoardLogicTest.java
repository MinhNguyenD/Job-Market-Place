package com.voidstudio.quickcashreg.jobPostUnitTest;

import static org.junit.Assert.assertEquals;

import android.location.Location;

import com.voidstudio.quickcashreg.jobpost.EmployerJobBoardActivity;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class JobBoardLogicTest {

  static Location l1;
  static Location l2;
  private static final Float DISTANCE = 5000F;
  static EmployerJobBoardActivity j;

  @BeforeClass
  public static void setup(){
    l1 = Mockito.mock(Location.class);
    l2 = Mockito.mock(Location.class);
    j = Mockito.mock(EmployerJobBoardActivity.class);
  }

  @AfterClass
  public static void tearDown(){
    System.gc();
  }

  @Test
  public void addition_isCorrect() {
    assertEquals(4, 2 + 2);
  }

  @Test
  public void loadSmallTasksListIsEmpty(){

  }

  @Test
  public void calculateDistanceKMTest(){
    Mockito.when(l1.distanceTo(l2)).thenReturn(DISTANCE);
    Mockito.when(j.calculateDistanceKM(l1,l2)).thenReturn(5.0);
    double res  = j.calculateDistanceKM(l1,l2);
    assertEquals(DISTANCE / 1000, res, 0.0);
  }






}
