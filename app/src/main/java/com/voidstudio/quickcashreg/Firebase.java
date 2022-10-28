package com.voidstudio.quickcashreg;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Firebase {
  static final String FIREBASE_URL =
          "https://quickcash-bd58f-default-rtdb.firebaseio.com/";
  public FirebaseDatabase firebaseDB = FirebaseDatabase.getInstance(FIREBASE_URL);

  private DatabaseReference firebaseDBReference = firebaseDB.getReferenceFromUrl(FIREBASE_URL);
  private DatabaseReference userChild;
  protected String firebaseString;
  protected String pass;
  private boolean exists = false;
  private boolean matches = false;
    public Firebase() {

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
    return getValueFromUser(username, "userType");
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

  private Task<Void> existingUserHelper(String username){
    DatabaseReference users = firebaseDBReference.child("users");
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
    return null;
  }



  protected boolean existingUser(String username){
      existingUserHelper(username);

      return exists;
  }

  private Task<Void> getValueHelper(String username, String value){
    DatabaseReference user = firebaseDB.getReference().child("users").child(username)
            .child(value);
    user.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()) {
          if(snapshot.getValue() != null) {
            firebaseString = snapshot.getValue().toString();
            pass = firebaseString;
          }
        }
      }
      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
    return null;
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






}