package com.voidstudio.quickcashreg;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseDatabase firebaseDB;
    private static final String FIREBASE_URL = "https://quickcash-bd58f-default-rtdb.firebaseio.com/";
    private DatabaseReference firebaseDBReference;
    private boolean userNameExisted;
    private Spinner roleList;
    private String selectedRole;

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
        TextView hintForEmail = (TextView) findViewById(R.id.hintForEmail);

        initializeDatabase();

        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Switch to Login Activity (Uncomment after merge)
                //switchToLogInWindow();
            }
        });

        setUpRoleListSpinner();
        roleListSpinnerListener();


        // check e-mail form
        emailFormatChecker(eMail, hintForEmail);

        // check confirm password is same or not
        confirmPasswordChecker(password, passwordConfirm, hintForPassWord);
    }

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

                int countOfAt = 0;
                for (char c : emailString.toCharArray()) {
                    if (c == '@') {
                        countOfAt++;
                    }
                }

                if (countOfAt != 1) {
                    hintForEmail.setText("This Does Not Look Like an E-mial");
                    hintForEmail.setTextColor(Color.RED);
                } else {
                    hintForEmail.setText("");
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

                if (!passwordStringConfirm.equals(passwordString)) {
                    hintForPassWord.setText("Two Passwords Is Not Same.");
                    hintForPassWord.setTextColor(Color.RED);
                } else {
                    hintForPassWord.setText("Two Passwords Is Same.");
                    hintForPassWord.setTextColor(Color.GREEN );
                }
            }
        });
    }

    /*
        initialize the database and reference
    */
    protected void initializeDatabase() {
        firebaseDB = FirebaseDatabase.getInstance(FIREBASE_URL);
        firebaseDBReference = firebaseDB.getReferenceFromUrl(FIREBASE_URL);
    }

    /*
        Getter method to get user name
    */
    protected String getUserName(){
        EditText userName = findViewById(R.id.userName);
        return userName.getText().toString().trim();
    }

    /*
        Getter method to get email address
    */
    protected String getEmail(){
        EditText emailAddress = findViewById(R.id.eMail);
        return emailAddress.getText().toString().trim();
    }

    /*
        Getter method to get password
    */
    protected String getPassword(){
        EditText password = findViewById(R.id.password);
        return password.getText().toString().trim();
    }

    /*
        Getter method to get confirm password
     */
    protected String getConfirmPassword(){
        EditText confirmPassword = findViewById(R.id.passwordConfirm);
        return confirmPassword.getText().toString().trim();
    }


    /*
        Check if email address is valid
     */
    protected boolean isValidEmailAddress(String emailAddress) {
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

    /*
        Check if password is valid
     */
    protected boolean isValidPassword(String password) {
        if(password.length() >= 6){
            return true;
        }
        return false;
    }

    /*
        Check if password match with confirm password
     */
    protected boolean isValidConfirmPassword(String password, String confirmPassword) {
        if(password.equals(confirmPassword)){
            return true;
        }
        return false;
    }


    /*
        Check if user name existed in current database
     */
    protected boolean userNameExisted(String userName){
        firebaseDBReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(userName)){
                    userNameExisted = true;
                }
                else{
                    userNameExisted = false;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return userNameExisted;
    }

    /*
    Save email address into database
    Note: We use username as an unique ID for a user
*/
    protected Task<Void> saveEmailAddressToFirebase(String email, String userName) {
        firebaseDBReference.child("users").child(userName).child("email").setValue(email);
        return null;
    }

    /*
        Save password into database
        Note: We use username as an unique ID for a user
     */
    protected Task<Void> savePasswordToFirebase(String password, String userName) {
        firebaseDBReference.child("users").child(userName).child("password").setValue(password);
        return null;
    }

    /*
        TODO: Uncomment after merge
        Switch to Login window from Register window
    */
    public void switchToLogInWindow(){
        //Intent intent = new Intent(MainActivity.this, LogIn.class);
        //startActivity(intent);
    }

    public void onClick(View view){
        String userName = getUserName();
        String email = getEmail();
        String password = getPassword();
        String confirmPassword = getConfirmPassword();
        String message ="";
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);


        if(userNameExisted(userName) || !isValidPassword(password) || !isValidConfirmPassword(password,confirmPassword)||!isValidEmailAddress(email)){
            if(!isValidPassword(password)){
                message = "Invalid Password";
            }
            else if(!isValidEmailAddress(email)){
                message = "Invalid Email";
            }
            else if(!isValidConfirmPassword(password,confirmPassword)){
                message = "Password and Confirm Password are not match";
            }

            else if(userNameExisted(userName)){
                message = "User Name is already registered";
            }

        }
        else{
            message = "User created successfully";
            saveEmailAddressToFirebase(email,userName);
            savePasswordToFirebase(password,userName);
            finish();
            //TODO: SWITCH TO LOGIN ACTIVITY (Uncomment after merge)
            //switchToLogInWindow();
        }


        alertBuilder.setMessage(message);
        alertBuilder.setPositiveButton("OK", null);
        alertBuilder.create();
        alertBuilder.show();
    }

}