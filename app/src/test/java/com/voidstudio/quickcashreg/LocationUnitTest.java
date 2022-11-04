package com.voidstudio.quickcashreg;

import static org.junit.Assert.assertTrue;

import android.location.Location;

import com.voidstudio.quickcashreg.Location.GPS;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class LocationUnitTest {
    static GPS gps;
    static Location loc;
    @BeforeClass
    public static void setup() {
        gps = Mockito.mock(GPS.class);
        loc = Mockito.mock(Location.class);
        loc.setLongitude(0.0);
        loc.setLatitude(0.0);
    }
    @Test
    public void locationIsCorrect(){
        Mockito.when(gps.getLocation()).thenReturn(loc);
        Location tempLoc = gps.getLocation();
        assertTrue(loc.getLatitude() == tempLoc.getLatitude() && loc.getLongitude() == tempLoc.getLongitude());
    }
}

