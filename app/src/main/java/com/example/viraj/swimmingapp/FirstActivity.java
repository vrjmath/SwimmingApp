package com.example.viraj.swimmingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        getSupportActionBar().hide();
        System.out.println("First Activity:" + SaveSharedPreference.getUserName(FirstActivity.this));
        if(SaveSharedPreference.getUserName(FirstActivity.this).length() != 0)
        {
            startActivity(new Intent(FirstActivity.this, HomeActivity.class));
            // call Login Activity
        }
        else
        {
            // Stay at the current activity.
        }

        Button mLogIn = (Button) findViewById(R.id.button2);
        mLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstActivity.this, LoginActivity.class));
            }
        });

        Button mRegister = (Button) findViewById(R.id.button3);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstActivity.this, RegisterActivity.class));
            }
        });
    }
}
