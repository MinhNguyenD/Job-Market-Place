package com.voidstudio.quickcashreg;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main Activity Class is the Register Page,
 * This is where the user registers for our app
 * The user can choose between being an employee or employer.
 * The user must input
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean userNameExisted;
    private Spinner roleList;
    private String selectedRole;

    private final TextReader textReader = new TextReader();

    private Firebase firebase = new Firebase();

    /**
     * On Create initializes all buttons text views and event listeners. Also chooses layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button registerButton = findViewById(R.id.buttonreg);
        Button loginButton = findViewById(R.id.loginButton);
        TextView userName = (TextView) findViewById(R.id.userName);
        TextView eMail = (TextView) findViewById(R.id.eMail);
        TextView password = (TextView) findViewById(R.id.password);
        TextView passwordConfirm = (TextView) findViewById(R.id.passwordConfirm);

        TextView hintForPassWord = (TextView) findViewById(R.id.hintForPassword);
        TextView hintForPassWordConfirm = (TextView) findViewById(R.id.hintForPasswordConfirm);
        TextView hintForEmail = (TextView) findViewById(R.id.hintForEmail);

        initializeDatabase();

        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        setUpRoleListSpinner();
        roleListSpinnerListener();


        // check e-mail form
        emailFormatChecker(eMail, hintForEmail);

        // check confirm password is same or not
        confirmPasswordChecker(password, passwordConfirm, hintForPassWordConfirm);

        // check password
        passwordChecker(password,hintForPassWord);
    }

    /**
     * @param role chosen by user, either employee
     * @return If an invalid role has not been chosen return false
     * @return If a valid role has been chosen return true
     */
    protected boolean isValidRole(String role) {
        if(role.equals("-")){
            return false;
        }

        return true;
    }


    private void roleListSpinnerListener() {
        roleList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int pos, long l) {
                selectedRole = adapterView.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setUpRoleListSpinner() {
        roleList = findViewById(R.id.roleList);
        String[] roles = new String[]{"-", "Employee", "Employer"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles);
        roleList.setAdapter(adapter);
    }

    private void emailFormatChecker(TextView eMail, TextView hintForEmail) {
        eMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String emailString = eMail.getText().toString();

                if (!isValidEmailAddress(emailString)) {
                    hintForEmail.setText("This Does Not Look Like An Email Address");
                    hintForEmail.setTextColor(Color.RED);
                } else {
                    hintForEmail.setText("");
                }
            }
        });
    }

    private void passwordChecker(TextView password, TextView hintForPassWord) {
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String passwordString  = password.getText().toString();

                if (!isValidPassword(passwordString)) {
                    hintForPassWord.setText("Password has to be at least 6 characters");
                    hintForPassWord.setTextColor(Color.RED);
                }
                else {
                    hintForPassWord.setText("");
                }
            }
        });
    }

    private void confirmPasswordChecker(TextView password, TextView passwordConfirm, TextView hintForPassWord) {
        passwordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String passwordString  = password.getText().toString();
                String passwordStringConfirm  = passwordConfirm.getText().toString();

                if (!isValidConfirmPassword(passwordString,passwordStringConfirm)) {
                    hintForPassWord.setText("Two Passwords Are Not The Same.");
                    hintForPassWord.setTextColor(Color.RED);
                } else {
                    hintForPassWord.setText("Two Passwords Are The Same.");
                    hintForPassWord.setTextColor(Color.GREEN );
                }
            }
        });
    }

        /**
            initialize the database and reference
        **/
        protected void initializeDatabase() {
           firebase.initializeDatabase();
        }

    /**
        Getter method to get user name
    **/
    protected String getUserName(){
        EditText userName = findViewById(R.id.userName);
        return textReader.getFromEditText(userName);
    }

    /**
        Getter method to get email address
    **/
    protected String getEmail(){
        EditText emailAddress = findViewById(R.id.eMail);
        return textReader.getFromEditText(emailAddress);
    }

    /**
        Getter method to get password
    **/
    protected String getPassword(){
        EditText password = findViewById(R.id.password);
        return textReader.getFromEditText(password);
    }

    /**
        Getter method to get confirm password
     **/
    protected String getConfirmPassword(){
        EditText confirmPassword = findViewById(R.id.passwordConfirm);
        return textReader.getFromEditText(confirmPassword);
    }


    /**
        Check if email address is valid
     **/
    protected static boolean isValidEmailAddress(String emailAddress) {
        /*
            Reference: OWASP Email Regex
            https://owasp.org/www-community/OWASP_Validation_Regex_Repository
         */
        Pattern p = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher m = p.matcher(emailAddress);
        if(m.find()){
            return true;
        }
        return false;
    }

    /**
        Check if password is valid
     **/
    protected static boolean isValidPassword(String password) {
        if(password.length() >= 6){
            return true;
        }
        return false;
    }

    /**
     * Check if password matches confirm password
     * @param password entered password
     * @param confirmPassword entered confirmPassword
     * @return true if password.equals(confirmPassword)
     */
    protected static boolean isValidConfirmPassword(String password, String confirmPassword) {
        if(password.equals(confirmPassword)){
            return true;
        }
        return false;
    }


    /**
     * Checks if username already exists in database
     * @param userName userName to check
     * @return boolean of userNameExisted, true if username exists
     */
    protected boolean userNameExisted(String userName){
        userNameExisted = firebase.existingUser(userName);
        return userNameExisted;
    }

    /**
     * Save email address into database
     * Note: We use username as an unique ID for a user
     * @param email entered email
     * @param userName user associated with the entered email
     */
    protected void saveEmailAddressToFirebase(String email, String userName) {
        firebase.setEmailAddress(email, userName);
    }


    /**
     * Save user type into database
     * Note: We use username as an unique ID for a user
     * @param userType type of user(employee or employer) to save to DB
     * @param userName associated user
     */
    protected void saveUserTypeToFirebase(String userType, String userName) {
        firebase.setUserType(userType, userName);
    }

    /**
     * Save password into database
     * Note: We use username as an unique ID for a user
     * @param password password to save to DB
     * @param userName associated user
     */
    protected void savePasswordToFirebase(String password, String userName) {
        firebase.setPassword(password, userName);
    }

    /**
     * Switches to log in window
     */
    public void switchToLogInWindow(){
        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
        startActivity(intent);
    }

    /**
     * Sets a status message
     * @param message the message to set as status message.
     */
    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }

    /**
     * On click method, validates entered credentials and moves to desired page
     * @param view The button pressed
     */
    public void onClick(View view){
        String userName = getUserName();
        String email = getEmail();
        String password = getPassword();
        String confirmPassword = getConfirmPassword();
        String message ="";
        String errorMessage = new String("ERROR MESSAGE");
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        if(view.getId() == R.id.buttonreg) {
            if (!isValidRole(selectedRole) || userNameExisted(userName) || !isValidPassword(password) || !isValidConfirmPassword(password, confirmPassword) || !isValidEmailAddress(email)) {
                if (!isValidPassword(password)) {
                    message = "Invalid Password";
                    errorMessage = getResources().getString(R.string.INVALID_PASSWORD).trim();
                } else if (!isValidEmailAddress(email)) {
                    message = "Invalid Email";
                    errorMessage = getResources().getString(R.string.INVALID_EMAIL).trim();
                } else if (!isValidConfirmPassword(password, confirmPassword)) {
                    message = "Password and Confirm Password are not match";
                    errorMessage = getResources().getString(R.string.INVALID_CONFIRM_PASSWORD).trim();
                } else if (userNameExisted(userName)) {
                    message = "User Name is already registered";
                    errorMessage = getResources().getString(R.string.USERNAME_EXISTED).trim();
                } else if (!isValidRole(selectedRole)) {
                    message = "Please Choose Your Role";
                    errorMessage = getResources().getString(R.string.INVALID_ROLE).trim();
                }

            } else {
                message = "User created successfully";
                errorMessage = getResources().getString(R.string.EMPTY_STRING);
                saveEmailAddressToFirebase(email, userName);
                savePasswordToFirebase(password, userName);
                saveUserTypeToFirebase(selectedRole, userName);
                switchToLogInWindow();
            }
            setStatusMessage(errorMessage);
            alertBuilder.setMessage(message);
            alertBuilder.setPositiveButton("OK", null);
            alertBuilder.create();
            alertBuilder.show();
        }
        if(view.getId() == R.id.loginButton){
            switchToLogInWindow();
        }



    }

}