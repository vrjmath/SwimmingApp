package com.example.viraj.swimmingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class SaveActivity extends AppCompatActivity {

    ScrollView screen;
    TextView splits;
    Button save, noSave, discard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        ArrayList<String> arr = getIntent().getStringArrayListExtra("EXTRA_SESSION_ID");
        for(int a = 0; a < arr.size(); a ++)
            System.out.println(arr.get(a));

        save = (Button) findViewById(R.id.save);
        noSave = (Button) findViewById(R.id.do_not_save);
        discard = (Button) findViewById(R.id.discard);

        splits = (TextView) findViewById(R.id.splits);
        //splits.setEnabled(false); //prevent the et_laps to be editable

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        noSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}