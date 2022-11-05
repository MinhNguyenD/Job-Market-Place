package com.voidstudio.quickcashreg;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.voidstudio.quickcashreg.jobpost.Job;
import com.voidstudio.quickcashreg.jobpost.JobDetailsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class JobPostingActivity extends AppCompatActivity{

    //    public static final String MY_PREFS = "MY_PREFS";
//    private static String jobItem;
    private static Firebase firebase;

    private ListView listView;
    private SearchView searchView;

    private Spinner distanceSpinner;
    private String selectedDistance;

    private Spinner durationSpinner;
    private String selectedDuration;

    private Spinner dateListedSpinner;
    private String selectedDate;

    private ArrayList<String> selectedFilters = new ArrayList<String>();


    public static final String OPTION_KM_DISTANCE1 = "1 km";
    public static final String OPTION_KM_DISTANCE2 = "2 km";
    public static final String OPTION_KM_DISTANCE3 = "5 km";
    public static final String OPTION_KM_DISTANCE4 = "10 km";

    public static final String OPTION_HOUR_DURATION1 = "1 hour";
    public static final String OPTION_HOUR_DURATION2 = "2 hour";
    public static final String OPTION_HOUR_DURATION3 = "3 hour";
    public static final String OPTION_HOUR_DURATION4 = "4 hour";

    public static final String OPTION_DAY_DATE_LISTED1 = "Last 1 day";
    public static final String OPTION_DAY_DATE_LISTED2 = "Last 7 days";
    public static final String OPTION_DAY_DATE_LISTED3 = "Last 30 days";


    public static final String DEFAULT_DURATION = "Duration";
    public static final String DEFAULT_DISTANCE = "Radius";
    public static final String DEFAULT_DATE_LISTED = "Date listed";

    ArrayList<Job> jobList  = new ArrayList<>();

    private String currentSearchText = "";

    Location employeeLocation = InAppActivityEmployee.employee.getLocation();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting);
        firebase = Firebase.getInstance();


//        Button seeAllJobsButton = findViewById(R.id.see_all_jobs);
//        seeAllJobsButton.setOnClickListener(JobPostingActivity.this);
        jobList = firebase.getAllJobs();

        setUpList(jobList);
        onItemClickListener();

        searchView = findViewById(R.id.search_bar);
        onQueryTextListener();

        distanceSpinner = findViewById(R.id.distanceList);
        String[] distances = new String[]{DEFAULT_DISTANCE,OPTION_KM_DISTANCE1,OPTION_KM_DISTANCE2,OPTION_KM_DISTANCE3,OPTION_KM_DISTANCE4};


        durationSpinner = findViewById(R.id.durationList);
        String[] durations = new String[]{DEFAULT_DURATION,OPTION_HOUR_DURATION1,OPTION_HOUR_DURATION2,OPTION_HOUR_DURATION3,OPTION_HOUR_DURATION4};

        dateListedSpinner = findViewById(R.id.dayPostedList);
        String[] days = new String[]{DEFAULT_DATE_LISTED,OPTION_DAY_DATE_LISTED1,OPTION_DAY_DATE_LISTED2,OPTION_DAY_DATE_LISTED3};


        setUpDistanceListSpinner(distanceSpinner, distances);
        setUpDistanceListSpinner(durationSpinner, durations);
        setUpDistanceListSpinner(dateListedSpinner, days);


        spinnerListener();
    }


    private void spinnerListener() {
        distanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int pos, long l) {
                selectedDistance = adapterView.getItemAtPosition(pos).toString();
                try {
                    filterList(selectedDistance);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        durationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int pos, long l) {
                selectedDuration = adapterView.getItemAtPosition(pos).toString();
                try {
                    filterList(selectedDuration);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dateListedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int pos, long l) {
                selectedDate = adapterView.getItemAtPosition(pos).toString();
                try {
                    filterList(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setUpDistanceListSpinner(Spinner spinner, String[] options) {
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        spinner.setAdapter(listAdapter);
    }


    private void setUpList(ArrayList<Job> jobList)
    {
        listView = (ListView) findViewById(R.id.jobListView);
        setAdapter(jobList);
    }

//    @Override
//    public void onClick(View v) {
//        if(v.getId() == R.id.see_all_jobs){
//            Intent jobBoardIntent = new Intent(JobPostingActivity.this, EmployeeJobBoardActivity.class);
//            startActivity(jobBoardIntent);
//        }
//    }

    private void onItemClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Job selectJob = (Job) (listView.getItemAtPosition(position));
                Intent intent = new Intent(getApplicationContext(), JobDetailsActivity.class);
                intent.putExtra("selectedJob", selectJob.toString());
                startActivity(intent);
            }
        });
    }

    private void onQueryTextListener(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Job> filteredBySearchJobs = new ArrayList<Job>();
                for(Job job : jobList){
                    if(job.getJobName().toUpperCase().contains(newText.toUpperCase())){
                        filteredBySearchJobs.add(job);
                    }
                }
                setUpList(filteredBySearchJobs);
                return false;
            }
        });
    }


    //TODO: IMPLEMENT METHODS AND VARIABLES MISSING IN EMPLOYEE AND JOB CLASS. USE METHOD TO GET LOCATION OF JOB AND USER LOCATION
    //TODO: IMPLEMENT SALARY RANGE FILTER
    private void filterList(String filterParameter) throws ParseException {
        if(!selectedFilters.contains(filterParameter)){
            selectedFilters.add(filterParameter);
        }
        ArrayList<Job> filteredJobList = new ArrayList<>();
        for(Job job: jobList){
            for(String filterParam: selectedFilters){
                String[] filterId = filterParam.split(" ");
                if(filterId[filterId.length -1].contains("km")) {
                    Location jobLocation = job.getLocation();
                    double distance = jobLocation.distanceTo(employeeLocation);
                    if (distance <= Double.valueOf(filterId[0])) {
                        if (currentSearchText == "") {
                            filteredJobList.add(job);
                        } else {
                            if (job.getJobName().toUpperCase().contains(currentSearchText.toUpperCase())) {
                                filteredJobList.add(job);
                            }
                        }
                    }
                }

                else if(filterId[filterId.length -1].contains("hour")){
                    if (Integer.valueOf(job.getDuration()) <= Integer.valueOf(filterId[0])) {
                        if (currentSearchText == "") {
                            filteredJobList.add(job);
                        } else {
                            if (job.getJobName().toUpperCase().contains(currentSearchText.toUpperCase())) {
                                filteredJobList.add(job);
                            }
                        }
                    }
                }
                else if(filterId[filterId.length -1].contains("day")){
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                    Date today = new Date();;
                    Date jobDate = sdf.parse(job.getDatePosted());

                    long diffInMillies = Math.abs(today.getTime() - jobDate.getTime());
                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    if ( diff <= Integer.valueOf(filterId[0])){
                        if (currentSearchText == "") {
                            filteredJobList.add(job);
                        } else {
                            if (job.getJobName().toUpperCase().contains(currentSearchText.toUpperCase())) {
                                filteredJobList.add(job);
                            }
                        }
                    }
                }
            }
        }
        setAdapter(filteredJobList);
    }

    private void setAdapter(ArrayList<Job> jobList)
    {
        SearchAdapter adapter = new SearchAdapter(getApplicationContext(), 0, jobList);
        listView.setAdapter(adapter);
    }

    public void allFilterTapped(View view){
        selectedFilters.clear();
//        selectedFilters.add("all");
        dateListedSpinner.setSelection(0);
        durationSpinner.setSelection(0);
        distanceSpinner.setSelection(0);
        setAdapter(jobList);
    }

}