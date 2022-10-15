package com.voidstudio.quickcashreg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity implements View.OnClickListener {

  private static final String EMPTY_CREDENTIALS = "Username or password is empty";
  private static final String USER_DOES_NOT_EXIST = "This username does not exist";
  private static final String INCORRECT_PASSWORD = "Incorrect Password!";

  private static String alertMessage = "BROKEN";


  //Success message may be replaced with switch of activities in future iter
  private static final String SUCCESS = "Success";

  private static boolean empty;
  private static boolean userExists;
  private static boolean correctPassword;

  private static String user;
  private static String pass;

  private FirebaseDatabase firebaseDB;
  private static final String FIREBASE_URL =
   "https://quickcash-bd58f-default-rtdb.firebaseio.com/";
  private DatabaseReference firebaseDBReference;
  private DatabaseReference userChild;

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);

    Button backToRegisterScreen = (Button)findViewById(R.id.logInRegisterButton);
    backToRegisterScreen.setOnClickListener(LogIn.this);

    Button continueButton = (Button)findViewById(R.id.continueButton);
    continueButton.setOnClickListener(LogIn.this);

    initializeDatabase();


  }

  protected void initializeDatabase(){
    firebaseDB = FirebaseDatabase.getInstance(FIREBASE_URL);
    firebaseDBReference = firebaseDB.getReferenceFromUrl(FIREBASE_URL);
    userChild = firebaseDBReference.child("users");
  }



  protected String getUserName(){
    EditText usernameBox = findViewById(R.id.logInUserName);
    return usernameBox.getText().toString().trim();
  }

  protected String getPassword(){
    EditText passwordBox = findViewById(R.id.logInPassword);
    return passwordBox.getText().toString().trim();
  }

  protected boolean isEmptyUsername(String username){
    return username.isEmpty();
  }

  protected boolean isEmptyPassword(String password){
    return password.isEmpty();
  }

  protected void emptyCredentials(){
    String username = getUserName();
    String password = getPassword();
    if(isEmptyPassword(password)||isEmptyUsername(username)){
      empty = true;
    }
    else empty = false;
  }


  /**
   *
   * @param username entered username
   * @return userExists if username exists in the database
   */
  protected boolean existingUser(String username){
    //UNCOMMENT WHEN CONNECTED TO FIREBASE
    userChild.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.hasChild(username)){
          userExists = true;
        }
        else{
          userExists = false;
        }
      }
      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(LogIn.this,"FAIL",Toast.LENGTH_LONG).show();
      }
    });
    return userExists;
  }

  protected boolean passwordMatch(String password){
    //UNCOMMENT WHEN CONNECTED TO FIREBASE
     userChild.child(getUserName()).
     addListenerForSingleValueEvent(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot snapshot) {
         if(snapshot.hasChild(password)){
           correctPassword = true;
         }
         else{
           correctPassword = false;
         }

       }
       @Override
       public void onCancelled(@NonNull DatabaseError error) {
         Toast.makeText(LogIn.this,"FAIL",Toast.LENGTH_LONG).show();
       }
     });
     return correctPassword;
  }

  public static boolean logIn(String username, String password){
    //Check if user exists
    //Check if password is correct
    if(empty){
      alertMessage = EMPTY_CREDENTIALS;
      return false;
    }
    if(!userExists){
      alertMessage = USER_DOES_NOT_EXIST;
      return false;
    }
    if(!correctPassword){
      alertMessage = INCORRECT_PASSWORD;
      return false;
    }
    else{
      alertMessage = SUCCESS;
      return true;
    }
  }

  protected void switchToRegisterWindow(){
    Intent registerSwitch = new Intent(LogIn.this, MainActivity.class);
    startActivity(registerSwitch);
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.logInRegisterButton) {
      switchToRegisterWindow();
    } else if(view.getId() == R.id.continueButton){
      emptyCredentials();
      existingUser(getUserName());
      passwordMatch(getPassword());
      logIn(getUserName(), getPassword());
      Toast.makeText(LogIn.this, alertMessage, Toast.LENGTH_LONG).show();
      //Replace toast with new activity in future iterations
    }
  }


}
