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
import android.support.design.widget.Snackbar;
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
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
//import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



public class PracticeFragment extends Fragment{
    private ArrayList<String> data = new ArrayList<String>();
    DatabaseReference df;
    ArrayAdapter<String> adapter;
    MaterialSearchView searchView;
    String distance;
    String stroke;
    String pool;
    String lanes;
    String order;
    String type;
    String attendance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_practice, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        distance = "50";
        stroke = "Freestyle";
        pool = "Short Course Yards";
        lanes = "1";
        order = "Heats";
        type = "Regular";


     /*   NumberPicker n = (NumberPicker)view.findViewById(R.id.pickerDistance);
        final String[] a={"50","100","200","400", "500", "1000"};
        distance = "50";
        n.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        n.setDisplayedValues(a);
        n.setMaxValue(5);
        n.setMinValue(0);
        n.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                distance = a[newVal];

            }
        });*/

        final MaterialSpinner distanceSpinner = (MaterialSpinner) view.findViewById(R.id.spinnerDistance);
       // spinner.setPrompt("Title");
        ArrayAdapter<CharSequence> distanceAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.distance_array, android.R.layout.simple_spinner_item);
        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceSpinner.setAdapter(distanceAdapter);
        distanceSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                distance = item;
                System.out.println("Itemis:" + item);
            }
        });

        //final Spinner strokeSpinner = (Spinner) view.findViewById(R.id.spinnerStroke);
        final MaterialSpinner strokeSpinner = (MaterialSpinner) view.findViewById(R.id.spinnerStroke);
        //final MaterialBetterSpinner strokeSpinner = (MaterialBetterSpinner)
               // view.findViewById(R.id.spinnerStroke);

        // spinner.setPrompt("Title");
        ArrayAdapter<CharSequence> strokeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.stroke_array, android.R.layout.simple_spinner_item);
        strokeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        strokeSpinner.setAdapter(strokeAdapter);
        strokeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                stroke = item;
            }
        });

        final MaterialSpinner poolSpinner = (MaterialSpinner) view.findViewById(R.id.spinnerPool);
        ArrayAdapter<CharSequence> poolAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.pool_array, android.R.layout.simple_spinner_item);
        poolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        poolSpinner.setAdapter(poolAdapter);
        poolSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                pool = item;
            }
        });

       // final EditText numLanes = (EditText) view.findViewById(R.id.numLanes);
        final MaterialSpinner lanesSpinner = (MaterialSpinner) view.findViewById(R.id.spinnerLanes);
        //final MaterialBetterSpinner strokeSpinner = (MaterialBetterSpinner)
        // view.findViewById(R.id.spinnerStroke);

        // spinner.setPrompt("Title");
        ArrayAdapter<CharSequence> lanesAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.lanes_array, android.R.layout.simple_spinner_item);
        lanesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lanesSpinner.setAdapter(lanesAdapter);
        lanesSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                 lanes = item;
            }
        });

        final MaterialSpinner orderSpinner = (MaterialSpinner) view.findViewById(R.id.spinnerOrder);
        //final MaterialBetterSpinner strokeSpinner = (MaterialBetterSpinner)
        // view.findViewById(R.id.spinnerStroke);

        // spinner.setPrompt("Title");
        ArrayAdapter<CharSequence> orderAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.order_array, android.R.layout.simple_spinner_item);
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(orderAdapter);
        orderSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                order = item;
            }
        });

        final MaterialSpinner typeSpinner = (MaterialSpinner) view.findViewById(R.id.spinnerType);
        //final MaterialBetterSpinner strokeSpinner = (MaterialBetterSpinner)
        // view.findViewById(R.id.spinnerStroke);

        // spinner.setPrompt("Title");
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.type_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                type = item;
            }
        });




      /*  final Spinner attendanceSpinner = (Spinner) view.findViewById(R.id.spinnerAttendance);
        // spinner.setPrompt("Title");
        ArrayAdapter<CharSequence> attendanceAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.attendance_array, android.R.layout.simple_spinner_item);
        attendanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        attendanceSpinner.setAdapter(attendanceAdapter);
        attendanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                attendance = attendanceSpinner.getSelectedItem().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

*/
            //ListView lv = (ListView) view.findViewById(R.id.listview);
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
              //  String numL;

              /*  try {
                     numL = numLanes.getText().toString();
                }catch(Exception e){return;}

                if (numL.equals("0") || numL.equals("") || numL.equals(null)){
                    Toast.makeText(getActivity(),
                            "Please enter number of lanes", Toast.LENGTH_LONG).show();
                    return;
                }*/


                LaneFragment lf = new LaneFragment();
                boolean go = false;
                if(distance.equals("50") && stroke.equals("Freestyle")){go = true;}
                else if(distance.equals("100") && !stroke.equals("IM")){go = true;}
                else if(distance.equals("200")){go = true;}
                else if(distance.equals("400") && stroke.equals("IM")){go = true;}
                else if(distance.equals("400") && (stroke.equals("Freestyle") || stroke.equals("IM")) && pool.equals("Long Course Meters")){go = true;}
                else if(distance.equals("500") && stroke.equals("Freestyle") && pool.equals("Short Course Yards")){go = true;}
                else if(distance.equals("1000") && stroke.equals("Freestyle") && pool.equals("Short Course Yards")){go = true;}

                if(go == true) {
                    Bundle b = new Bundle();
                    b.putString("Distance", distance);
                    b.putString("Stroke", stroke);
                    b.putString("Pool", pool);
                    b.putString("Lanes", lanes);
                    b.putString("Order", order);
                    b.putString("Type", type);
                    // System.out.println("INFORMATION:" +distance + stroke);
                    lf.setArguments(b);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.screen_area, lf)
                            .commit();
                }
                else
                {
                    Toast.makeText(getActivity(),
                            "Please enter a valid event", Toast.LENGTH_LONG).show();
                }
            }
        });
    /*    adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, R.id.list_item_text, data);
        lv.setAdapter(adapter);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        df = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("SwimPractice");
        //df.child(ln + ", " + fn).child("Birthdate").setValue(strDate);
        //df =
     /*   df.addListenerForSingleValueEvent(new ValueEventListener() {
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
       // adapter.notifyDataSetChanged();

      /*  lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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