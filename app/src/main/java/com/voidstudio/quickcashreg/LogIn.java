package com.voidstudio.quickcashreg;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.widget.Button;
import android.content.SharedPreferences;


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
    public static final String WELCOME = "Welcome to In App!";

    private static String alertMessage = "BROKEN";
    private static String databaseUser;


    //Success message may be replaced with switch of activities in future iteration
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

    private String firebaseString;

    //static SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Button backToRegisterScreen = (Button)findViewById(R.id.logInRegisterButton);
        backToRegisterScreen.setOnClickListener(LogIn.this);

        Button continueButton = (Button)findViewById(R.id.continueButton);
        continueButton.setOnClickListener(LogIn.this);

        // Find the Show Password button and the Password Field
        Button showPassword = findViewById(R.id.showHidePassword);
        showPassword.setOnClickListener(LogIn.this);

        initializeDatabase();


    }

    public void showHidePassword(Button showPassword, EditText passwordText) {
        if (passwordText.getText().toString().isEmpty()) {
            passwordText.setError("Please enter a password!");
        } else if (showPassword.getText().toString().equals("Show Password")) {
            showPassword.setText("Hide Password");
            passwordText.setTransformationMethod(null);
        } else {
            showPassword.setText("Show Password");
            passwordText.setTransformationMethod(new PasswordTransformationMethod());
        }
    }


    /*
    @Override
    protected void onResume() {
        super.onResume();
        sp = getSharedPreferences("login", MODE_PRIVATE);
        if (sp.getBoolean("logged", true)) {
            gotoInAppActivity();
        } else {
            Button login = findViewById(R.id.continueButton);
            login.setOnClickListener(LogIn.this);
        }
    }

    public void gotoInAppActivity() {
        Intent inApp = new Intent(this, InAppActivity.class);
        startActivity(inApp);
    }
    */


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
        EditText passwordBox = findViewById(R.id.textPassword);
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
        firebaseDBReference.child("users").child(getUserName()).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        firebaseString = snapshot.child(password).getKey();
                        if(password.equals(snapshot.child(password).getKey())){
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

            /*
            // Add username and password to login sharedPreferences state
            sp.edit().putString("Username", username).apply();
            sp.edit().putString("Password", password).apply();
            sp.edit().putBoolean("logged", true).apply();
            */

            return true;
        }
    }

    protected void switchToRegisterWindow(){
        Intent registerSwitch = new Intent(LogIn.this, MainActivity.class);
        startActivity(registerSwitch);
    }

    @Override
    public void onClick(View view) {
        emptyCredentials();
        existingUser(getUserName());
        passwordMatch(getPassword());
        if (view.getId() == R.id.logInRegisterButton) {
            switchToRegisterWindow();
        }else if (view.getId() == R.id.showHidePassword) {
            showHidePassword(findViewById(R.id.showHidePassword), findViewById(R.id.textPassword));
        } else if(view.getId() == R.id.continueButton) {
            logIn(getUserName(), getPassword());
            Toast.makeText(LogIn.this, alertMessage + getPassword() + getUserName() + firebaseString, Toast.LENGTH_LONG).show();
            //Replace toast with new activity in future iterations

            if (logIn(getUserName(), getPassword())) {
                Intent inApp = new Intent(LogIn.this, InAppActivity.class);
                inApp.putExtra(WELCOME, "Welcome!!!");
                startActivity(inApp);
            }
        }
    }
}