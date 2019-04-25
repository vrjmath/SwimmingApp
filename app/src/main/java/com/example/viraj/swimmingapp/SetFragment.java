package com.example.viraj.swimmingapp;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class SetFragment extends Fragment implements Runnable {

    EditText mEtLaps;
    Button mBtnStart, mBtnLap;
    TextView mTvTimer;
    ScrollView mSvLaps;
    Bundle inputBundle;

    ArrayList<String> splits;
    int people;
    //ArrayList<String> attendance;

    Handler handler = new Handler();
    public static final long MILLIS_TO_MINUTES = 60000;
    DatabaseReference df;
    //ArrayList<String> splitsArr = new ArrayList<String>();

    long mStartTime;
    //public long current = mStartTime;
    /**
     * If the class is running or not
     */
    boolean mIsRunning;
    String event;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //inputBundle = getArguments();
        //attendance = inputBundle.getStringArrayList("attendance");

        inputBundle = getArguments();
        event = inputBundle.getString("Stroke");
        String temp = inputBundle.getString("Distance");
        String pool = inputBundle.getString("Pool");

        String poolAcr = "";

        if (event.equals("Freestyle")) {
            event = "Free";
        } else if (event.equals("Backstroke")) {
            event = "Back";
        } else if (event.equals("Breastroke")) {
            event = "Breast";
        } else if (event.equals("Butterfly")) {
            event = "Fly";
        } else if (event.equals("IM")) {
            event = "IM";
        } else if (event.equals("Best Stroke"))
            event = "Best Stroke";
        if (pool.equals("Short Course Yards")) {
            poolAcr = "Yd";
        } else if (pool.equals("Long Course Meters")) {
            poolAcr = "M";
        }
        /*if(event.equals("Best Stroke")){
            event = temp + " " + poolAcr + " " + event;
        }
        else{*/
        event = "\"" + temp + " " + poolAcr + " " + event + "\"";
        //"\"100 M Free\""
        FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
        FirebaseUser user1 = mAuth1.getCurrentUser();

        df = FirebaseDatabase.getInstance().getReference("Users").child(user1.getUid()).child("Attendance");
        df.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get total available quest
                        people = (int) dataSnapshot.getChildrenCount();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        splits  =new ArrayList<>();
        mEtLaps = (EditText)view.findViewById(R.id.swimmer_tag_number);
        mBtnStart = (Button) view.findViewById(R.id.btn_start);
        mBtnLap= (Button) view.findViewById(R.id.btn_lap);
        mTvTimer = (TextView) view.findViewById(R.id.tv_timer);
        mSvLaps = (ScrollView) view.findViewById(R.id.sv_lap);
        mEtLaps.setEnabled(false);

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartTime = 0;
                start();

                handler.postDelayed(updateTimerThread,0);
            }
        });

        mBtnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //we just simply copy the current text of tv_timer and append it to et_laps
                String ans1 =  getSplitTime();
                splits.add(ans1);
                if(splits.size() == people) {
                    Collections.sort(splits);
                    TimeSwimmerPairFragment tsp = new TimeSwimmerPairFragment();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("splits", splits);
                    //bundle.putStringArrayList("attendance", attendance);
                    //bundle.putString("SetName", inputBundle.getString("SetName"));
                    bundle.putString("SetName", event);
                    tsp.setArguments(bundle);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.screen_area, tsp)
                            .commit();

                }

                mEtLaps.append(ans1 + "\n" );
                //mEtLapsSplit.append(ans2);


                mSvLaps.post(new Runnable() {
                    @Override
                    public void run() {
                        mSvLaps.smoothScrollTo(0, mEtLaps.getBottom());
                    }
                });
            }
            //Intent intent = new Intent(ChronometerFragment.this, SaveActivity.class);
            //intent.putExtra("EXTRA_SESSION_ID", mChrono.getList());
            //startActivity(intent);
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
            int millis = (int) since % 100; //the last 3 digits of millisecs

            mTvTimer.setText(String.format("%02d:%02d:%02d"
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
            //current = mStartTime;
        }
        mIsRunning = true;
    }

    /**
     * Stops the chronometer
     */
    public void lap() {
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

    @Override
    public void run() {

    }
    public String getSplitTime() {
        long time = System.currentTimeMillis() - mStartTime;
        //System.out.println(current + " is this one");
        int seconds = (int) (time / 1000) % 60;
        int minutes = (int) ((time / (MILLIS_TO_MINUTES)) % 60);
        //int hours = (int) ((since / (MILLS_TO_HOURS)) % 24); //this resets to  0 after 24 hour!
        //int hours = (int) ((time / (MILLS_TO_HOURS))); //this does not reset to 0!
        int millis = (int) time % 100; //the last 3 digits of millisecs
        return String.format("%02d:%02d:%02d", minutes, seconds, millis);
    }

}


