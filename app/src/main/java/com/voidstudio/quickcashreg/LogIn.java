package com.voidstudio.quickcashreg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for log in.
 * Users password and username are required to log in. Tests if user enters correct credentials
 * Logs in if credentials are correct. Implements switch to register button.
 * Is able to keep user logged in and hide/show the password they enter.
 */
public class LogIn extends AppCompatActivity implements View.OnClickListener {

    private static final String EMPTY_CREDENTIALS = "Username or password is empty";
    private static final String USER_DOES_NOT_EXIST = "This username does not exist";
    private static final String INCORRECT_PASSWORD = "Incorrect Password!";
    public static final String WELCOME = "Welcome to In App!";
    public static final String PREFERENCES = "login";
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
    public static final String ISLOGGED = "logged";

    private static String alertMessage = "BROKEN";
    private static String databaseUser;


    //Success message may be replaced with switch of activities in future iteration
    private static final String SUCCESS = "Success";

    private static boolean empty;
    private static boolean userExists;
    private static boolean correctPassword;
    protected static boolean employee;// if false then user is an employer

    private static String user;
    private static String pass;
    //Edit text reader helper method using delegation
    private final TextReader textReader = new TextReader();
    private final Firebase firebase = new Firebase();

    public SharedPreferences sp;


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

        // logic for stay log in
        sp = getSharedPreferences(PREFERENCES, MODE_PRIVATE);

        if (sp.getBoolean(ISLOGGED, false)) {
            goToInAppActivityEmployer();
        }
        else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(ISLOGGED, false);
            editor.commit();
        }
        firebase.initializeDatabase();

    }

    /**
     * Method to change the text in the 'Show Password' button and show the password in password field
     * @param showPassword is the button
     * @param passwordText is the password field
     */
    public void showHidePassword(Button showPassword, EditText passwordText) {
        if (textReader.getFromEditText(passwordText).isEmpty()) {
            passwordText.setError("Please enter a password!");
        } else if (showPassword.getText().toString().equals("Show Password")) {
            showPassword.setText("Hide Password");
            passwordText.setTransformationMethod(null);
        } else {
            showPassword.setText("Show Password");
            passwordText.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    /**
     * On successful login from employer, this method switches activity to inAppActivityEmployer
     */
    public void goToInAppActivityEmployer() {
        Intent inAppEmployer = new Intent(this, InAppActivityEmployer.class);
        inAppEmployer.putExtra(WELCOME, "Hi Employer, you logged in");
        startActivity(inAppEmployer);
    }

    public void goToInAppActivityEmployee() {
        Intent inAppEmployee = new Intent(this, InAppActivityEmployee.class);
        inAppEmployee.putExtra(WELCOME, "Hi Employee, you logged in");
        startActivity(inAppEmployee);
    }

    /**
     * If the user wants to register, this method switches the activity to register activity
     */
    protected void switchToRegisterWindow(){
        Intent registerSwitch = new Intent(LogIn.this, MainActivity.class);
        startActivity(registerSwitch);
    }

    /**
     * Database initializer for log in activity
     */
    protected void initializeDatabase(){
        firebase.initializeDatabase();
    }


    private String getUserName(){
        EditText usernameBox = findViewById(R.id.logInUserName);
        return textReader.getFromEditText(usernameBox);
    }

    private String getPassword(){
        EditText passwordBox = findViewById(R.id.textPassword);
        return textReader.getFromEditText(passwordBox);
    }


    private boolean isEmptyUsername(String username){
        return username.isEmpty();
    }

    private boolean isEmptyPassword(String password){
        return password.isEmpty();
    }

    private void emptyCredentials(){
        String username = getUserName();
        String password = getPassword();
        if(isEmptyPassword(password)||isEmptyUsername(username)){
            empty = true;
        }
        else empty = false;
    }

    /**
     * Checks if user that is logging in is an employee
     * @return True if the user is an employee
     */
    protected boolean isEmployee(){
       if(firebase.getUserType(getUserName()).equals("Employee")){
           employee = true;
       }
       else {
           employee = false;
       }
       return employee;
    }


    /**
     *
     * @param username entered username
     * @return userExists if username exists in the database
     */
    protected boolean existingUser(String username){
        //This method is identical to method in register, Consider refactor
        userExists =firebase.existingUser(username);
        return userExists;
    }

    /**
     * Checks if the entered password matches the password associated to the user
     * @param password password entered by the user about to log in
     * @return boolean that is true if the password matches the password associated to the user
     */
    protected boolean passwordMatch(String password){

        if(firebase.checkIfPasswordMatches(getUserName(),password)) correctPassword = true;
        else correctPassword = false;
        return correctPassword;
    }

    /**
     * Log In method
     * @param username login username
     * @param password login password
     * @return true if log in is successful.
     */
    public static boolean logIn(String username, String password){
        //Refactor Later
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

    private void stayLoggedIn() {
        sp = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sp.edit();
        // Add username and password to login sharedPreferences state
        editor1.putString(USERNAME, getUserName());
        editor1.putString(PASSWORD, getPassword());
        editor1.putBoolean(ISLOGGED, true);
        editor1.commit();
    }

    /**
     * On Click method, 3 on click cases:
     * If the user clicks on the continue button, attempt log in with entered credentials,
     * When log in is successful switch to InAppActivity
     * If the user clicks register button, they are switched to register window
     * If the user clicks the show password button, the password entered password is shown
     * in plain text
     * @param view The button being pressed
     */
    @Override
    public synchronized void onClick(View view) {
        emptyCredentials();
        isEmployee();
        if (view.getId() == R.id.logInRegisterButton) {
            switchToRegisterWindow();
        }else if (view.getId() == R.id.showHidePassword) {
            showHidePassword(findViewById(R.id.showHidePassword), findViewById(R.id.textPassword));
        } else if(view.getId() == R.id.continueButton) {
            existingUser(getUserName());
            passwordMatch(getPassword());
            logIn(getUserName(), getPassword());
            Toast.makeText(LogIn.this, alertMessage+firebase.pass, Toast.LENGTH_LONG).show();
            //Replace toast with new activity in future iterations
            if (logIn(getUserName(), getPassword())) {
                stayLoggedIn();
                Toast.makeText(LogIn.this, alertMessage, Toast.LENGTH_LONG).show();
                if(employee){
                    goToInAppActivityEmployee();
                }
                else{
                    goToInAppActivityEmployer();
                }
            }
        }
    }


}
