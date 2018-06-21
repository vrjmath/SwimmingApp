package com.example.viraj.swimmingapp;

import android.app.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ChronometerActivity extends Activity {

    //Member variables to access UI Elements
    Button mBtnStart, mBtnLap, mBtnStop; //buttons
    TextView mTvTimer; //timer textview
    EditText mEtLaps; //laps text view
    ScrollView mSvLaps; //scroll view which wraps the et_laps

    //keep track of how many times btn_lap was clicked
    int mLapCounter = 1;

    //Instance of Chronometer
    Chronometer mChrono;

    //Thread for chronometer
    Thread mThreadChrono;

    //Reference to the MainActivity (this class!)
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);

        //Instantiating all member variables

        mContext = this;

        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnLap = (Button) findViewById(R.id.btn_lap);
        mBtnStop = (Button) findViewById(R.id.btn_stop);

        mTvTimer = (TextView) findViewById(R.id.tv_timer);
        mEtLaps = (EditText) findViewById(R.id.et_laps);
        mEtLaps.setEnabled(false); //prevent the et_laps to be editable

        mSvLaps = (ScrollView) findViewById(R.id.sv_lap);


        //btn_start click handler
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the chronometer has not been instantiated before...
                if(mChrono == null) {
                    //instantiate the chronometer
                    mChrono = new Chronometer(mContext);
                    //run the chronometer on a separate thread
                    mThreadChrono = new Thread(mChrono);
                    mThreadChrono.start();

                    //start the chronometer!
                    mChrono.start();

                    //clear the perilously populated et_laps
                    mEtLaps.setText(""); //empty string!

                    //reset the lap counter
                    mLapCounter = 1;
                }
            }
        });

        //btn_stop click handler
        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the chronometer had been instantiated before...
                if(mChrono != null) {
                    //stop the chronometer
                    mChrono.stop();
                    //stop the thread
                    mThreadChrono.interrupt();
                    mThreadChrono = null;
                    //kill the chrono class
                    //mChrono = null;
                }
                Intent intent = new Intent(ChronometerActivity.this, SaveActivity.class);
                intent.putExtra("EXTRA_SESSION_ID", mChrono.getList());
                startActivity(intent);
            }
        });

        mBtnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if chrono is not running we shouldn't capture the lap!
                if(mChrono == null) {
                    return; //do nothing!
                }

                //we just simply copy the current text of tv_timer and append it to et_laps
                mEtLaps.append("LAP " + String.valueOf(mLapCounter++)
                        + "   " + mChrono.getSplit() + "   " + mChrono.getSplitTime() + "\n");

                //scroll to the bottom of et_laps
                mSvLaps.post(new Runnable() {
                    @Override
                    public void run() {
                        mSvLaps.smoothScrollTo(0, mEtLaps.getBottom());
                    }
                });
            }
        });
    }

    /**
     * Update the text of tv_timer
     * @param timeAsText the text to update tv_timer with
     */
    public void updateTimerText(final String timeAsText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvTimer.setText(timeAsText);
            }
        });
    }
}