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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class SettingsFragment extends Fragment implements Runnable {
    DatabaseReference df;
    EditText tags;
    Button mBtnStart, mBtnStop;
    TextView mTvTimer;
    ScrollView mSvLaps;
    String name1, name2, name3, name4;
    //name3, name4;

    Handler handler = new Handler();
    public static final long MILLIS_TO_MINUTES = 60000;

    //ArrayList<String> splitsArr = new ArrayList<String>();

    long mStartTime;
    public long current = mStartTime;
    /**
     * If the class is running or not
     */
    boolean mIsRunning;

    boolean through;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name1 = "4205539777"; //Sumer
        name2 = "0083458897"; //Viraj
        name3 = "0013371094"; // Ryan
        name4 = "0012618156"; //jason

        tags = (EditText)view.findViewById(R.id.swimmer_tag_number);
        mBtnStart = (Button) view.findViewById(R.id.btn_start);
        mBtnStop = (Button) view.findViewById(R.id.btn_stop);
        mTvTimer = (TextView) view.findViewById(R.id.tv_timer);
        mSvLaps = (ScrollView) view.findViewById(R.id.sv_lap);

        tags.setEnabled(false);
        through = false;
        df = FirebaseDatabase.getInstance().getReference("RFID Testing");
        //db=FirebaseDatabase.getInstance().getReference().child("mp");
        //Query query = df.orderByKey().limitToLast(1);
        //Query query = df.orderByChild("timestamp").start(currentTimestamp)
        /*query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    //Log.d("User key", child.getKey());
                    //Log.d("User val", child.child("message").getValue().toString());
                    tags.append(child.getValue().toString());
                   // tags.append(temp + "    ");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartTime = 0;
                start();

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

        Query query = df.orderByKey().limitToLast(1);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                long tempTime = Long.parseLong(dataSnapshot.getKey().toString());
                tempTime = tempTime - getStartTime()-1500;
                //df.child(previousChildName).
                System.out.println("THE TIME:" + tempTime);
                System.out.println("START TIME:" + getStartTime());

                int seconds = (int) (tempTime / 1000) % 60;
                int minutes = (int) ((tempTime / (MILLIS_TO_MINUTES)) % 60);
                //int hours = (int) ((since / (MILLS_TO_HOURS)) % 24); //this resets to  0 after 24 hour!
                //int hours = (int) ((since / (MILLS_TO_HOURS))); //this does not reset to 0!
                int millis = (int) tempTime % 100; //the last 3 digits of millisecs
                String name = dataSnapshot.getValue().toString();

                if(name.equals(name1)){
                    tags.append("Swimmer A: " + String.format("%02d:%02d:%02d"
                            , minutes, seconds, millis) + "\n");}
                else if(name.equals(name2)){
                    tags.append("Swimmer B: " + String.format("%02d:%02d:%02d"
                            , minutes, seconds, millis) + "\n");}
                else if(name.equals(name3)){
                    tags.append("Ryan: " + String.format("%02d:%02d:%02d"
                            , minutes, seconds, millis) + "\n");}
                else if(name.equals(name4)){
                    tags.append("Jason: " + String.format("%02d:%02d:%02d"
                            , minutes, seconds, millis) + "\n");}

                mSvLaps.post(new Runnable() {
                    @Override
                    public void run() {
                        mSvLaps.smoothScrollTo(0, tags.getBottom());
                    }
                });
                    // tags.append(temp + "    ");
                //}

                    //data.add(swimmer.getKey() + " " + swimmer.getValue().toString());
                    //System.out.println("has been read");

                //Collections.sort(data);
                //adapter.notifyDataSetChanged()
                //Log.d("Data set", "It has changed");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // If any child is removed to the database reference
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    //Log.d("User key", child.getKey());
                    //Log.d("User val", child.child("message").getValue().toString());
                    tags.append(child.getValue().toString());
                    // tags.append(temp + "    ");
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                ///Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                //Toast.makeText(mContext, "Failed to load comments.",
                       // Toast.LENGTH_SHORT).show();
            }
        });

    /*WebView webview = view.findViewById(R.id.webview);
    getActivity().setContentView(webview);
    webview.loadUrl("https://www.google.com/");*/
        /*WebView mWebView = (WebView) view.findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl("https://swimswam.com/news/");*/



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

    @Override
    public void run() {

    }
    }

