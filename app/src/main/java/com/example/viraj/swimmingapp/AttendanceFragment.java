package com.example.viraj.swimmingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AttendanceFragment extends Fragment {

    private ArrayList<String> data = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    Button next;
    Button retake;

    int x;
    ArrayList<String> names;
    ArrayList<String> present;
    Bundle inputBundle;
    DatabaseReference df;
    DatabaseReference df2;
    ArrayList<String> allNames;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        names = new ArrayList<>();
        present = new ArrayList<>();
        allNames = new ArrayList<>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        df = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Swimmers");
        //df.child(ln + ", " + fn).child("Birthdate").setValue(strDate);
        //df =
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //HashMap<String, Object> dataMap = new HashMap<String, Object>();
                for (DataSnapshot swimmer : dataSnapshot.getChildren()) {
                    String t = swimmer.getKey() + " ";
                    data.add(t);//+ swimmer.getValue().toString());
                    allNames.add(t);
                    System.out.println("has been read");
                }
                //Collections.sort(data);
                adapter.notifyDataSetChanged();
                Log.d("Data set", "It has changed");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        super.onViewCreated(view, savedInstanceState);


        final ListView lv = (ListView) view.findViewById(R.id.listview);

        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item_swimmer_time, R.id.list_item_text, data);
        lv.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              /*  x++;
                String temp = data.get(position);
                present.add(temp);*/
                //lv.getItemAtPosition(position);

                ImageView imageView = (ImageView) view.findViewById(R.id.check_mark);
                System.out.println("IMAGEVIEW:" + imageView);
                if (imageView.getVisibility() == View.INVISIBLE) {
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.INVISIBLE);
                }

                //data.remove(position);
                adapter.notifyDataSetChanged();

            }
        });

    }/*
        next = (Button) view.findViewById(R.id.next);
        retake = (Button) view.findViewById(R.id.retake);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inputBundle = getArguments();

                FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
                FirebaseUser user1 = mAuth1.getCurrentUser();

                df2 = FirebaseDatabase.getInstance().getReference("Users").child(user1.getUid()).child("Attendance");
                for(int a = 0; a< present.size(); a ++)
                df2.child(present.get(a)).child("A").setValue("");

                PracticeFragment sf = new PracticeFragment();
                //Bundle bundle = new Bundle();
                //bundle.putStringArrayList("attendance", present);
                //bundle.putString("SetName", inputBundle.getString("SetName"));
                //sf.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.screen_area, sf)
                        .commit();
            }
        });

        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.clear();
                for(int e = 0; e < allNames.size(); e++){
                    data.add(allNames.get(e));
                }
                adapter.notifyDataSetChanged();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                df = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Attendance");
                df.setValue(null);
            }
        });

    }


      /*      @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    return true;
                }

                return super.onOptionsItemSelected(item);
            }*/
    }
