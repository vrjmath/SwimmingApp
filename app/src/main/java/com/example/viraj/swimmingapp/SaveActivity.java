package com.example.viraj.swimmingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ScrollView;

import java.util.ArrayList;

public class SaveActivity extends AppCompatActivity {

    ScrollView screen;
    EditText splits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        ArrayList<String> arr = getIntent().getStringArrayListExtra("EXTRA_SESSION_ID");
        for(int a = 0; a < arr.size(); a ++)
            System.out.println(arr.get(a));

        splits = (EditText) findViewById(R.id.splits);
        splits.setEnabled(false); //prevent the et_laps to be editable

        screen = (ScrollView) findViewById(R.id.screen);

        for(int a = 0; a < arr.size(); a ++) {
            splits.append("LAP " + a
                    + "   " + arr.get(a) + "\n");
            screen.post(new Runnable() {
                @Override
                public void run() {
                    screen.smoothScrollTo(0, splits.getBottom());
                }
            });
        }
    }
}
