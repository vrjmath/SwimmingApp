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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AttendanceFragment extends Fragment {

    private ArrayList<String> absentData = new ArrayList<String>();
    private ArrayList<String> presentData = new ArrayList<String>();
    ArrayAdapter<String> absentAdapter;
    ArrayAdapter<String> presentAdapter;
    Button save;
    Button retake;

    int x;
    ArrayList<String> absent;
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
        absent = new ArrayList<>();
        present = new ArrayList<>();
        allNames = new ArrayList<>();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        read();

        df = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Swimmers");
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot swimmer : dataSnapshot.getChildren()) {
                    String t = swimmer.getKey() + " ";
                    boolean in = false;
                    for(int a = 0; a<presentData.size(); a++){
                        if(presentData.get(a).equals(t)){in = true;}
                    }
                    if(in == false)
                    absentData.add(t);

                    allNames.add(t);
                }
                absentAdapter.notifyDataSetChanged();
                Log.d("Data set", "It has changed");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        super.onViewCreated(view, savedInstanceState);


        final ListView lv = (ListView) view.findViewById(R.id.listview);
        final ListView plv = (ListView) view.findViewById(R.id.listviewPresent);
        presentAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item_swimmer_time, R.id.list_item_text, presentData);
        plv.setAdapter(presentAdapter);

        presentAdapter.notifyDataSetChanged();

        absentAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item_swimmer_time, R.id.list_item_text, absentData);
        lv.setAdapter(absentAdapter);

        absentAdapter.notifyDataSetChanged();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                x++;
                String temp = absentData.get(position);
                present.add(temp);
                presentData.add(temp);

                absentData.remove(position);
                absentAdapter.notifyDataSetChanged();
                presentAdapter.notifyDataSetChanged();

            }
        });

        plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                x++;
               String temp = presentData.get(position);
                absent.add(temp);
                absentData.add(temp);

                presentData.remove(position);

                System.out.println("");
                for(int a = 0; a < presentData.size(); a ++){
                    System.out.println("name:" + presentData.get(a));
                }

                FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
                FirebaseUser user1 = mAuth1.getCurrentUser();

                df2 = FirebaseDatabase.getInstance().getReference("Users").child(user1.getUid()).child("Attendance");
                df2.child(temp).setValue(null);

                absentAdapter.notifyDataSetChanged();
                presentAdapter.notifyDataSetChanged();
            }
        });



        save = (Button) view.findViewById(R.id.save);
        retake = (Button) view.findViewById(R.id.retake);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
                FirebaseUser user1 = mAuth1.getCurrentUser();

                df2 = FirebaseDatabase.getInstance().getReference("Users").child(user1.getUid()).child("Attendance");
               df2.setValue(null);
                for(int a = 0; a< presentData.size(); a ++){
                    //System.out.println("In present goes:" + present.get(a));
                df2.child(presentData.get(a)).child("A").setValue("");}

                Toast.makeText(getActivity(),
                        "Saved", Toast.LENGTH_LONG).show();
            }
        });

        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                absentData.clear();
                for(int e = 0; e < allNames.size(); e++){
                    absentData.add(allNames.get(e));
                }
                presentData.clear();
                presentAdapter.notifyDataSetChanged();
                absentAdapter.notifyDataSetChanged();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                df = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Attendance");
                df.setValue(null);
            }
        });

    }




      public void read() {
          FirebaseAuth mAuth = FirebaseAuth.getInstance();
          FirebaseUser user = mAuth.getCurrentUser();
          DatabaseReference df1 =
                  FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Attendance");
          df1.addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {
                  for (DataSnapshot swimmer : dataSnapshot.getChildren()) {
                      presentData.add(swimmer.getKey());
                  }
                  presentAdapter.notifyDataSetChanged();
              }

              @Override
              public void onCancelled(DatabaseError databaseError) {

              }
          });

      }
    }
