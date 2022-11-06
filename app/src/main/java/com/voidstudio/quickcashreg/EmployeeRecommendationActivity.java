package com.voidstudio.quickcashreg;

import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.voidstudio.quickcashreg.jobpost.Job;

import java.util.ArrayList;

import users.Employee;

public class EmployeeRecommendationActivity extends AppCompatActivity {
    ListView recommendEmployeeList;
    SeekBar seekBarForDistance;
    TextView distanceInput;
    EmployeeRecommendation employeeRecommendation = new EmployeeRecommendation();
    Firebase firebase;
    Job job;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_recommendation);
        firebase = employeeRecommendation.getFirebase();

        Bundle input = getIntent().getExtras();

//        String name = input.getString("Name");
//        String tag = input.getString("Tag");
//        String wage = input.getString("Wage");
//        String title = input.getString("Title");

        String name = "Emma";
        String tag = "tag";
        String wage = "100";
        String title = "Smart";

        job = new Job(title, wage, tag, name);
        Location tt = new Location(name);
        tt.setLatitude(0);
        tt.setLongitude(0);
        job.setLocation(tt);

        recommendEmployeeList = findViewById(R.id.recommendationEmployeeList);
        seekBarForDistance = (SeekBar)findViewById(R.id.seekBarForDistance);
        distanceInput = (TextView)findViewById(R.id.distanceInput);

        setupSeekBarListener();
        firebase.listenerForUser_Ref();
    }


    public void setupSeekBarListener() {
        seekBarForDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                distanceInput.setText(String.valueOf(getMaxDistanceInKM()));
                getRecommendInfo(getMaxDistanceInKM());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });
    }

    private void getRecommendInfo(double maxDistance) {
        ArrayList<Employee> employees= firebase.getRecommendList();
//        ArrayList<Employee> recommendList = employeeRecommendation.getRecommendation(job, employees, maxDistance);

        ArrayList<Employee> recommendList = employeeRecommendation.getRecommendation(job, employees, getMaxDistanceInKM());
        String[] recommendInfoList = new String[recommendList.size()];

        for (int i = 0; i < recommendList.size(); i++) {
            recommendInfoList[i] = recommendList.get(i).recommendInfo();
        }
        setList(recommendInfoList);
    }

    private void setList(String[] recommendInfoList) {
        ArrayAdapter<String> arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, recommendInfoList);
        recommendEmployeeList.setAdapter(arr);
    }

    public int getSeekBarForDistanceValue() {
        int progress = seekBarForDistance.getProgress();
        return progress;
    }

    public int getMaxDistanceInKM() {
        return getSeekBarForDistanceValue() * 3000;
    }


}





























