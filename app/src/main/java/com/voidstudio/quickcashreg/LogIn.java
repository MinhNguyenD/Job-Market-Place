package com.voidstudio.quickcashreg;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity implements View.OnClickListener {

    private static final String EMPTY_STRING = "";
    private static final String USER_DOES_NOT_EXIST = "This username does not exist";
    private static final String INCORRECT_PASSWORD = "Incorrect Password!";

    private static String alertMessage;

    //Success message may be replaced with switch of activities in future iter
    private static final String SUCCESS = "Success";

    private static boolean empty = false;
    private static boolean userExists = false;
    private static boolean correctPassword = false;

    //Firebase Initialization(Commented out till im allowed to use it)
    //private FirebaseDatabase firebaseDB;
    //private static final String FIREBASE_URL =
    // "https://quickcash-bd58f-default-rtdb.firebaseio.com/";
    //private DatabaseReference firebaseDBReference


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Find the Show Password button and the Password Field
        Button showPassword = findViewById(R.id.showHidePassword);
        showPassword.setOnClickListener(LogIn.this);
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

    //protected void initializeDatabase(){
    //  firebaseDB = FirebaseDatabase.getInstance(FIREBASE_URL)
    //  firebaseDBReference = firebaseDB.getReferenceFromURL(FIREBASE_URL)
    //}


    protected String getUserName() {
        EditText usernameBox = findViewById(R.id.logInUserName);
        return usernameBox.getText().toString().trim();
    }

    protected String getPassword() {
        EditText passwordBox = findViewById(R.id.textPassword);
        return passwordBox.getText().toString().trim();
    }

    protected boolean isEmptyUsername(String username) {
        return username.isEmpty();
    }

    protected boolean isEmptyPassword(String password) {
        return password.isEmpty();
    }

    protected void emptyCredentials() {
        String username = getUserName();
        String password = getPassword();
        if (isEmptyPassword(password) || isEmptyUsername(username)) {
            empty = true;
        } else empty = false;
    }


    /**
     * @param username entered username
     * @return True if username exists in the database
     */
    protected static void existingUser(String username) {
        //UNCOMMENT WHEN CONNECTED TO FIREBASE
        // firebaseDBReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
        //   @Override
        //   public void onDataChange(@NonNull DataSnapshot snapshot) {
        //     if(snapshot.hasChild(userName)){
        //       usernameExists = true;
        //     }
        //     else{
        //       usernameExists = false;
        //     }
        //   }
        //   @Override
        //   public void onCancelled(@NonNull DatabaseError error) {
        //   }
        // });
    }

    protected static void passwordMatch(String password) {
        //UNCOMMENT WHEN CONNECTED TO FIREBASE
        // firebaseDBReference.child("users").child(getUserName()).
        // addListenerForSingleValueEvent(new ValueEventListener() {
        //   @Override
        //   public void onDataChange(@NonNull DataSnapshot snapshot) {
        //     if(snapshot.hasChild(password)){
        //       correctPassword = true;
        //     }
        //     else{
        //       correctPassword = false;
        //     }
        //   }
        //   @Override
        //   public void onCancelled(@NonNull DatabaseError error) {
        //   }
        // });
    }

    public static boolean logIn(String username, String password) {
        existingUser(username);
        passwordMatch(password);
        if (empty) {
            alertMessage = EMPTY_STRING;
            return false;
        } else if (userExists) {
            alertMessage = USER_DOES_NOT_EXIST;
            return false;
        } else if (correctPassword) {
            alertMessage = INCORRECT_PASSWORD;
            return false;
        } else {
            alertMessage = SUCCESS;
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.showHidePassword) {
            showHidePassword(findViewById(R.id.showHidePassword), findViewById(R.id.textPassword));
        }

        emptyCredentials();
        logIn(getUserName(), getPassword());
        Toast.makeText(LogIn.this, alertMessage, Toast.LENGTH_LONG).show();
        //Replace toast with new activity in future iterations.
    }
}
