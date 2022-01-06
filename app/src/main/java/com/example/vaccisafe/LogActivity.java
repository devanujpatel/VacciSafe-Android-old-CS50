package com.example.vaccisafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import static android.content.ContentValues.TAG;

public class LogActivity extends AppCompatActivity {

    EditText email_gui;
    EditText password_gui;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
    }
    
    public void log_in_user(View view) {
         email_gui = (EditText) findViewById(R.id.email);
         password_gui = (EditText) findViewById(R.id.password_log_in);
         
         String email = email_gui.getText().toString();
         String password = password_gui.getText().toString();
         
         // TODO: get the email's corresponding password from sqlite database
         // TODO: if email is not found stored then show try registering message
        
        String stored_password = "dummy_password"; // value to be changed after implementing above's TODO
        
        if (stored_password.equals(password)) {
            // TODO: start intent to home activity
            Log.d(TAG, "log_in_user: user logged  in");
        } else {
            // TODO: show try again message
            Log.d(TAG, "log_in_user: user out");
        }
    }
    
}