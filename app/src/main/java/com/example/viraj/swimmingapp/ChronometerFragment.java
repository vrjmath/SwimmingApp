package com.example.viraj.swimmingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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


public class ChronometerFragment extends android.app.Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chronometer, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Instantiating all member variables

        mContext = getActivity();

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
                //if the chronometer has not been instantiated before...
              /*)  if(mChrono == null) {
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
                }*/
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
                //Intent intent = new Intent(ChronometerFragment.this, SaveActivity.class);
                //intent.putExtra("EXTRA_SESSION_ID", mChrono.getList());
                //startActivity(intent);
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvTimer.setText(timeAsText);
            }
        });
    }
}