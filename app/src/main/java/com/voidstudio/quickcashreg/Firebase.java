package com.voidstudio.quickcashreg;

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

public class Firebase {
  static final String FIREBASE_URL =
          "https://quickcash-bd58f-default-rtdb.firebaseio.com/";
  private static FirebaseDatabase firebaseDB;
  private static Firebase firebase;
  private static DatabaseReference firebaseDBReference;
  private final String USERS = "users";
  private final String JOBS = "jobs";



  protected String firebaseString;
  protected String pass;

  private boolean exists = false;
  protected boolean employee = false;
  private Firebase() {
    firebaseDB = FirebaseDatabase.getInstance();
    firebaseDBReference = firebaseDB.getReferenceFromUrl(FIREBASE_URL);
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



  public void initializeDatabase(){
      firebaseDB = FirebaseDatabase.getInstance(FIREBASE_URL);
      firebaseDBReference = firebaseDB.getReferenceFromUrl(FIREBASE_URL);
  }

  protected String getEmailAddress(String username) {
   String email = getValueFromUser(username, "email");

   return email;
  }

  protected String getUserType(String username){
    return getValueFromUser(username, "type");
  }

  protected String getPassword(String username){
    return getValueFromUser(username, "password");
  }



  /**
   * Save email address into database
   * Note: We use username as an unique ID for a user
   * @param email entered email
   * @param userName user associated with the entered email
   */
  protected Task<Void> setEmailAddress(String email, String userName) {
    firebaseDBReference.child("users").child(userName).child("email").setValue(email);
    return null;
  }


  /**
   * Save user type into database
   * Note: We use username as an unique ID for a user
   * @param userType type of user(employee or employer) to save to DB
   * @param userName associated user
   */
  protected Task<Void> setUserType(String userType, String userName) {
    firebaseDBReference.child("users").child(userName).child("userType").setValue(userType);
    return null;
  }

  /**
   * Save password into database
   * Note: We use username as an unique ID for a user
   * @param password password to save to DB
   * @param userName associated user
   */
  protected Task<Void> setPassword(String password, String userName) {
    firebaseDBReference.child("users").child(userName).child("password").setValue(password);
    return null;
  }

  private void existingUserHelper(String username){
    final Query users = firebaseDBReference.child("users");
    users.
            addListenerForSingleValueEvent(new ValueEventListener() {
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



  protected boolean existingUser(String username){
      existingUserHelper(username);

      return exists;
  }

  private void getValueHelper(String username, String value){
    final Query user = firebaseDB.getReference().child("users").child(username).child(value);
    user.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()) {
          Object sc = snapshot.getValue();
          if(sc != null) {
            firebaseString = sc.toString();
            if(firebaseString.equals("Employee")) employee = true;
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
  protected boolean checkIfPasswordMatches(String username, String password){
     return getPassword(username).equals(password);
  }

  protected void addUser(String username, String password, String email, String type){
    Map<String, Object> map = new HashMap<>();
    map.put("password", password);
    map.put("email", email);
    map.put("type", type);
    firebaseDBReference.child(USERS).child(username).updateChildren(map);

  }

  public void addJob(String jobName, String jobWage, String jobTag, String userName){
    Map<String, Object> map = new HashMap<>();
    Job job = new Job(jobName,jobWage,jobTag,userName);
    map.put(jobName, job);
    map.put("Posted by", userName);
    firebaseDBReference.child(JOBS).child(jobName).updateChildren(map);
    map.remove("Posted by");
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






}