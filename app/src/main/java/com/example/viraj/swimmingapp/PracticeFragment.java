package com.example.viraj.swimmingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



public class PracticeFragment extends Fragment{
    private ArrayList<String> data = new ArrayList<String>();
    DatabaseReference df;
    ArrayAdapter<String> adapter;
    MaterialSearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_practice, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView lv = (ListView) view.findViewById(R.id.listview);
       /* Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Search for Set");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        searchView = (MaterialSearchView) view.findViewById(R.id.search_view);
        setHasOptionsMenu(true);*/
        //View view1 = inflater.inflate(R.layout.sample, null);

        Button mMakeLanes = (Button) view.findViewById(R.id.MakeLanesButton);
        mMakeLanes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.screen_area, new LaneFragment())
                        .commit();
            }
        });
        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, R.id.list_item_text, data);
        lv.setAdapter(adapter);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        df = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("SwimPractice");
        //df.child(ln + ", " + fn).child("Birthdate").setValue(strDate);
        //df =
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //HashMap<String, Object> dataMap = new HashMap<String, Object>();
                for(DataSnapshot swimmer: dataSnapshot.getChildren()){
                    data.add(swimmer.getKey() + " " );//+ swimmer.getValue().toString());
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
        //data.add("10 100's Freestyle");
        adapter.notifyDataSetChanged();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SetFragment sf = new SetFragment();
                Bundle bundle = new Bundle();
                bundle.putString("SetName", data.get(position));
                sf.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.screen_area, sf)
                        .commit();

            }
        });

        //FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.new_practice, null);
             /*   Spinner strokeSpinner = (Spinner) dialogView.findViewById(R.id.spinner_stroke);
                ArrayAdapter strokeSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.stroke_array, android.R.layout.simple_spinner_dropdown_item);
                strokeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                strokeSpinner.setAdapter(strokeSpinnerAdapter);
                strokeSpinnerAdapter.notifyDataSetChanged();

                Spinner distanceSpinner = (Spinner) dialogView.findViewById(R.id.spinner_distance);
                ArrayAdapter distanceSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.distance_array, android.R.layout.simple_spinner_dropdown_item);
                distanceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                distanceSpinner.setAdapter(distanceSpinnerAdapter);
                distanceSpinnerAdapter.notifyDataSetChanged();

                final Spinner eventSpinner = (Spinner) dialogView.findViewById(R.id.spinner_event);
                ArrayAdapter eventSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.events_array, android.R.layout.simple_spinner_dropdown_item);
                eventSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                eventSpinner.setAdapter(eventSpinnerAdapter);
                eventSpinnerAdapter.notifyDataSetChanged();

                builder.setView(dialogView)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //Spinner spinner = (Spinner) dialogView.findViewById(R.id.spinner_stroke);
                                String text = eventSpinner.getSelectedItem().toString();
                                System.out.println("TEXT: " + text);
                                //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                                //System.out.println(timeStamp );
                                int x = data.size() + 1;
                                String theText = text + "           " + x;

                                data.add(theText);
                                FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
                                FirebaseUser user1 = mAuth1.getCurrentUser();
                                df = FirebaseDatabase.getInstance().getReference("Users").child(user1.getUid()).child("SwimPractice");
                                        df.child(theText).setValue("");

                                adapter.notifyDataSetChanged();

                            }
                        });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });*/

    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        System.out.println("CALLED!!!");
        inflater.inflate(R.menu.menu_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
    }
}