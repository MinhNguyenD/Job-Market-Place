package com.voidstudio.quickcashreg;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView userName = (TextView) findViewById(R.id.userName);
        TextView eMail = (TextView) findViewById(R.id.eMail);
        TextView password = (TextView) findViewById(R.id.password);
        TextView passwordConfirm = (TextView) findViewById(R.id.passwordConfirm);

        TextView hintForPassWord = (TextView) findViewById(R.id.hintForPassword);
        TextView hintForEmail = (TextView) findViewById(R.id.hintForEmail);

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
}