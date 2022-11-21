package com.voidstudio.quickcashreg.firebase;

import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.EMAIL;
import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.PASSWORD;
import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.TYPE;
import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.USERNAME;
import static com.voidstudio.quickcashreg.firebase.FirebaseConstants.USERS;

import android.location.Location;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.voidstudio.quickcashreg.jobpost.Job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import users.Employee;
import users.UserConstants;

public class Firebase {
  static final String FIREBASE_URL =
          "https://quickcash-bd58f-default-rtdb.firebaseio.com/";
  private static FirebaseDatabase firebaseDB;
  private static Firebase firebase;
  private static DatabaseReference firebaseDBReference;
  private final String JOBS = "jobs";

  private static DatabaseReference users_ref;
  private static DatabaseReference jobs_ref;

  public ArrayList<Employee> recommendList = new ArrayList<>();

  public String firebaseString;
  public String pass;

  private boolean exists = false;
  public boolean employee = false;
  public Firebase() {
    firebaseDB = FirebaseDatabase.getInstance();
    firebaseDBReference = firebaseDB.getReferenceFromUrl(FIREBASE_URL);

    jobs_ref = firebaseDB.getReference("jobs");
    users_ref = firebaseDB.getReference("users");
  }

  /**
   * Singleton design pattern. Ensures only one instance of firebase across project
   * @return
   */
  public static Firebase getInstance() {
    if(firebase == null) {
      firebase = new Firebase();
    }
    return firebase;
  }

  public ArrayList<Employee> getRecommendList() {
    return recommendList;
  }

  public void setRecommendList(ArrayList<Employee> recommendList) {
    this.recommendList = recommendList;
  }

  public void initializeDatabase(){
    firebaseDB = FirebaseDatabase.getInstance(FIREBASE_URL);
    firebaseDBReference = firebaseDB.getReferenceFromUrl(FIREBASE_URL);
  }

  public String getEmailAddress(String username) {
    String email = getValueFromUser(username, EMAIL);

    return email;
  }

  public String getUserType(String username){
    return getValueFromUser(username, TYPE);
  }

  public String getPassword(String username){
    return getValueFromUser(username, PASSWORD);
  }



  /**
   * Save email address into database
   * Note: We use username as an unique ID for a user
   * @param email entered email
   * @param userName user associated with the entered email
   */
  public Task<Void> setEmailAddress(String email, String userName) {
    firebaseDBReference.child(USERS).child(userName).child(EMAIL).setValue(email);
    return null;
  }


  /**
   * Save user type into database
   * Note: We use username as an unique ID for a user
   * @param userType type of user(employee or employer) to save to DB
   * @param userName associated user
   */
  public Task<Void> setUserType(String userType, String userName) {
    firebaseDBReference.child(USERS).child(userName).child(TYPE).setValue(userType);
    return null;
  }

  /**
   * Save password into database
   * Note: We use username as an unique ID for a user
   * @param password password to save to DB
   * @param userName associated user
   */
  public Task<Void> setPassword(String password, String userName) {
    firebaseDBReference.child(USERS).child(userName).child(PASSWORD).setValue(password);
    return null;
  }

  private void existingUserHelper(String username){
    final Query users = firebaseDBReference.child(USERS);
    users.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.hasChild(username)){
          exists = true;
        }
        else{
          exists = false;
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
      }
    });
  }



  public boolean existingUser(String username){
    existingUserHelper(username);

    return exists;
  }

  private void getValueHelper(String username, String value){
    Query user = firebaseDB.getReference().child(USERS).child(username).child(value);
    user.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()) {
          Object sc = snapshot.getValue();
          if(sc != null) {
            firebaseString = sc.toString();
            if(firebaseString.equals(UserConstants.EMPLOYEE)) employee = true;
            pass = firebaseString;
          }
        }
      }
      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
  }




  /**
   * Gets the value as string
   * @param username the user
   * @param value the corresponding value
   * @return the value stored in firebase
   */
  private String getValueFromUser(String username, String value){
    getValueHelper(username,value);
    if(firebaseString != null){
      return firebaseString;
    }
    else return "";
  }

  //Both of these should be in log in(THIS IS FOR DEBUGGING)
  public boolean checkIfPasswordMatches(String username, String password){
    return getPassword(username).equals(password);
  }

  public void addUser(String username, String password, String email, String type, String minimumSalary){
    Map<String, Object> map = new HashMap<>();
    map.put(USERNAME, username);
    map.put(PASSWORD, password);
    map.put("minimumSalary", minimumSalary);
    map.put("orderFinished", "0");
    map.put(EMAIL, email);
    map.put(TYPE, type);
    firebaseDBReference.child(USERS).child(username).updateChildren(map);
  }

  public void addJob(String jobName, String jobWage, String jobTag, String userName){
    Map<String, Object> map = new HashMap<>();
    Job job = new Job(jobName,jobWage,jobTag,userName);
    map.put(jobName, job);
    firebaseDBReference.child(JOBS).updateChildren(map);
    firebaseDBReference.child(USERS).child(userName).child(JOBS).updateChildren(map);
  }

  public ArrayList<Job> getJobsFromUser(String username){
    ArrayList<Job> arrJob = new ArrayList<>();
    if(username == null){
      return arrJob;
    }
    Query query = firebaseDBReference.child(USERS).child(username).child(JOBS);
    query.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        //final String jobName;
        //final String jobWage;
        //final String jobTag;
        for(DataSnapshot sc : snapshot.getChildren()){
          Job job;
          if(sc.exists() && sc.getChildrenCount()>0) {
            job = sc.getValue(Job.class);
            if(job!=null){
              arrJob.add(job);
            }
          }
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
    return arrJob;
  }

  public ArrayList<Job> getAllJobs(){
    ArrayList<Job> arrJob = new ArrayList<>();
    Query query = firebaseDBReference.child(JOBS);
    query.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        for(DataSnapshot sc : snapshot.getChildren()){
          Job job;
          if(sc.exists() && sc.getChildrenCount()>0) {
            job = sc.getValue(Job.class);
            if(job!=null){
              arrJob.add(job);
            }
          }
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
    return arrJob;
  }
 
  public void listenerForUser_Ref() {
    users_ref.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        recommendList = new ArrayList<>();
        for (DataSnapshot ds: snapshot.getChildren()) {
          String type = ds.child(TYPE).getValue(String.class);
          if (!type.equals(UserConstants.EMPLOYEE)) {
            continue;
          }
          String name = ds.child(USERNAME).getValue(String.class);
          String email = ds.child(EMAIL).getValue(String.class);
          String miniSalary = ds.child("minimumSalary").getValue(String.class);
          String orderFinished = ds.child("orderFinished").getValue(String.class);

          String latitude = ds.child("latitude").getValue(String.class);
          String longitude = ds.child("longitude").getValue(String.class);

          Location location = new Location("name");
          location.setLongitude(Double.parseDouble(longitude));
          location.setLatitude(Double.parseDouble(latitude));


          Employee employee = new Employee(name, email, Integer.parseInt(orderFinished), Double.parseDouble(miniSalary), location);
          recommendList.add(employee);
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
      }
    });
  }

  public Map<String,String> getUserInfo(String username){
    Map<String, String> map = new HashMap<>();
    map.put("name", username);
    map.put("password", getPassword(username));
    map.put("email", getEmailAddress(username));
    map.put("type", getUserType(username));
    return map;
  }





}
