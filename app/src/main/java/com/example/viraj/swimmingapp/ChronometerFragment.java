package com.example.viraj.swimmingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;


public class ChronometerFragment extends android.app.Fragment implements Runnable {


    Handler handler = new Handler();
    public static final long MILLIS_TO_MINUTES = 60000;


    ArrayList<String> splitsArr = new ArrayList<String>();

    long mStartTime;
    public long current = mStartTime;
    /**
     * If the class is running or not
     */
    boolean mIsRunning;

    Button mBtnStart, mBtnLap, mBtnStop;
    TextView mTvTimer;
    EditText mEtLaps;
    ScrollView mSvLaps;

    //keep track of how many times btn_lap was clicked
    int mLapCounter = 1;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chronometer, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mBtnStart = (Button) view.findViewById(R.id.btn_start);
        mBtnLap = (Button) view.findViewById(R.id.btn_lap);
        mBtnStop = (Button) view.findViewById(R.id.btn_stop);

        mTvTimer = (TextView) view.findViewById(R.id.tv_timer);
        mEtLaps = (EditText) view.findViewById(R.id.et_laps);
        mEtLaps.setEnabled(false); //prevent the et_laps to be editable

        mSvLaps = (ScrollView) view.findViewById(R.id.sv_lap);


        //btn_start click handler
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartTime = 0;

                    start();


                    mEtLaps.setText(""); //empty string!

                    //reset the lap counter
                    mLapCounter = 1;
                    handler.postDelayed(updateTimerThread,0);
                }
            });

        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   stop();
                   handler.removeCallbacks(updateTimerThread);

                }
                //Intent intent = new Intent(ChronometerFragment.this, SaveActivity.class);
                //intent.putExtra("EXTRA_SESSION_ID", mChrono.getList());
                //startActivity(intent);
            });

        mBtnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //we just simply copy the current text of tv_timer and append it to et_laps
                String ans = "LAP " + String.valueOf(mLapCounter++)
                        + "   " + getSplit();
                ans = ans + "   " + getSplitTime() + "\n";
                mEtLaps.append(ans);


                mSvLaps.post(new Runnable() {
                    @Override
                    public void run() {
                        mSvLaps.smoothScrollTo(0, mEtLaps.getBottom());
                    }
                });
            }
        });
    }

    Runnable updateTimerThread = new Runnable(){
        @Override
        public void run(){
            //while(mIsRunning) {
                //We do not call ConvertTimeToString here because it will add some overhead
                //therefore we do the calculation without any function calls!

                //Here we calculate the difference of starting time and current time
                long since = System.currentTimeMillis() - mStartTime;

                //convert the resulted time difference into hours, minutes, seconds and milliseconds
                int seconds = (int) (since / 1000) % 60;
                int minutes = (int) ((since / (MILLIS_TO_MINUTES)) % 60);
                //int hours = (int) ((since / (MILLS_TO_HOURS)) % 24); //this resets to  0 after 24 hour!
                //int hours = (int) ((since / (MILLS_TO_HOURS))); //this does not reset to 0!
                int millis = (int) since % 1000; //the last 3 digits of millisecs

                mTvTimer.setText(String.format("%02d:%02d:%03d"
                        , minutes, seconds, millis));

                handler.postDelayed(this, 0);
           // }
                //Sleep the thread for a short amount, to prevent high CPU usage!
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



    }
    };

    public void start() {
        if(mStartTime == 0) { //if the start time was not set before! e.g. by second constructor
            mStartTime = System.currentTimeMillis();
            current = mStartTime;
        }
        mIsRunning = true;
    }

    /**
     * Stops the chronometer
     */
    public void stop() {
        mIsRunning = false;
    }

    /**
     * Check if the chronometer is running or not
     * @return true if running, false if not running
     */
    public boolean isRunning() {
        return mIsRunning;
    }

    /**
     * Get the start time of the class
     * @return start time in milliseconds
     */
    public long getStartTime() {
        return mStartTime;
    }

    public String getSplit() {
        long temp = current;
        current = System.currentTimeMillis();
        System.out.println(current + " is thi");
        long split =  current - temp;
        int seconds = (int) (split / 1000) % 60;
        int minutes = (int) ((split / (MILLIS_TO_MINUTES)) % 60);
        //int hours = (int) ((since / (MILLS_TO_HOURS)) % 24); //this resets to  0 after 24 hour!
        //int hours = (int) ((split / (MILLS_TO_HOURS))); //this does not reset to 0!
        int millis = (int) split % 100; //the last 3 digits of millisecs
        String ans = String.format("%02d:%02d:%03d", minutes, seconds, millis);
        splitsArr.add(ans);
        return ans ;
    }

    public String getSplitTime() {
        long time = current - mStartTime;
        System.out.println(current + " is this one");
        int seconds = (int) (time / 1000) % 60;
        int minutes = (int) ((time / (MILLIS_TO_MINUTES)) % 60);
        //int hours = (int) ((since / (MILLS_TO_HOURS)) % 24); //this resets to  0 after 24 hour!
        //int hours = (int) ((time / (MILLS_TO_HOURS))); //this does not reset to 0!
        int millis = (int) time % 1000; //the last 3 digits of millisecs
        return String.format("%02d:%02d:%03d", minutes, seconds, millis);
    }

    public ArrayList<String> getList() {
        return splitsArr;
    }

    @Override
    public void run() {

    }


}