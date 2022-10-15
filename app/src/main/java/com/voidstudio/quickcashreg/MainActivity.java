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
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDB;
    private static final String FIREBASE_URL = "https://quickcash-bd58f-default-rtdb.firebaseio.com/";
    private DatabaseReference firebaseDBReference;

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

        // check e-mail form
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

        // check confirm password is same or not
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

    protected void initializeDatabase() {
        firebaseDB = FirebaseDatabase.getInstance(FIREBASE_URL);
        firebaseDBReference = firebaseDB.getReferenceFromUrl(FIREBASE_URL);
    }
}