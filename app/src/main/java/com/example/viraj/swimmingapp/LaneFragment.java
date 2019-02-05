package com.example.viraj.swimmingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class LaneFragment extends Fragment {
    private ArrayList<String> data = new ArrayList<String>();

    private ObservableScrollView scrollView1 = null;
    private ObservableScrollView scrollView2 = null;
    DatabaseReference df3;
    Bundle inputBundle;
     String type;

    ArrayList<String> mCheeseList;
    ArrayAdapter<String> adapter;
    ArrayList<String> present;
    ArrayList<String> allNames;
    String event;
    View v;
    public LaneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_list_view, container, false);
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputBundle = getArguments();
         event = inputBundle.getString("Stroke");
        String temp = inputBundle.getString("Distance");
        String pool = inputBundle.getString("Pool");
        final String order = inputBundle.getString("Order");
        type = inputBundle.getString("Type");
        String poolAcr = "";
        final String numL = inputBundle.getString("Lanes");
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
        if(event.equals("Best Stroke")){
            event = temp + " " + poolAcr + " " + event;
        }
        else{
        event = "\"" + temp + " " + poolAcr + " " + event + "\"";}
        //"\"100 M Free\""

        v = view;
        mCheeseList = new ArrayList<String>();
        present = new ArrayList<String>();
        allNames = new ArrayList<String>();

        FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
        FirebaseUser user1 = mAuth1.getCurrentUser();

        df3 = FirebaseDatabase.getInstance().getReference("Users").child(user1.getUid()).child("Attendance");
        df3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot swimmer : dataSnapshot.getChildren()) {
                    System.out.println("It made it");
                    present.add(swimmer.getKey().toString() + "");//+ swimmer.getValue().toString());
                }
                compare();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("It did not make it");

            }
        });





  /*     for (int i = 0; i < mCheeseList.size(); ++i) {
            System.out.println("HEREIS:" + mCheeseList.get(i));
        }*/

        Button next = (Button) view.findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeatLaneDisplayFragment hldf = new HeatLaneDisplayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Lanes", numL);
                bundle.putStringArrayList("Order", mCheeseList );
                bundle.putString("OrderPool", order);
                bundle.putString("Type", type);
                bundle.putString("Event", event);
                hldf.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.screen_area, hldf)
                        .commit();
            }
            });
    }

    public void adapterStuff(ArrayList<String> temp) {


       // setHeatLane(temp);

        /*for(int x = 0; x < temp.size(); x ++) {
            String value = temp.get(x);
            int index = value.indexOf(' ') + 2;
            int secondIndex = 0;
            while(value.charAt(index) != ('H')) {
                index ++;
            }

            String newValue = value.substring(0, index) +
                    value.substring(value.length()-4);
            System.out.println("OLDVALUE:" + value);
            System.out.println("NEWVALUE:" + newValue);
            temp.set(x, newValue);
        }

        //for(int y = 0; y < temp.size(); y ++) {
         //   String aValue = temp.get(y);
          //  String bValue = aValue.substring(0, aValue.indexOf(''))
        //}*/
        adapter = new StableArrayAdapter(getActivity(), R.layout.text_view, temp);
        DynamicListView listView = (DynamicListView) v.findViewById(R.id.scrollview1);
        listView.setLaneFragment(this);
        listView.setCheeseList(mCheeseList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    public void setHeatLane(List<String> cheese) {
        for (int a = 0; a < cheese.size(); a++) {
            int x = a%4 + 1;
            int y = a/4 + 1;
            String temp = cheese.get(a);
            cheese.set(a, temp + " H" + y + "L" + x);
        }
    }

    public void makeData(int a){
        for(int x = 0; x < a; x ++) {
            data.add("H" + a/5 + "L" + a%5);
        }
    }
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if(scrollView == scrollView1) {
            scrollView2.scrollTo(x, y);
        } else if(scrollView == scrollView2) {
            scrollView1.scrollTo(x, y);
        }
    }

    public void compare() {
        //System.out.println("PRESENTIS:" + present.get(0));
       // System.out.println("IN HERE");
        //System.out.println("SIZE:" + present.size());
      //  for (int x = 0; x < present.size(); x++)
        //    System.out.println("NAMEIS:" + present.get(x));

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference df = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child(event);


        //df.child(ln + ", " + fn).child("Birthdate").setValue(strDate);
        //df =

        //scrollView1 = (ObservableScrollView) view.findViewById(R.id.scrollview1);
        // scrollView1.setScrollViewListener();
        // scrollView2 = (ObservableScrollView) view.findViewById(R.id.scrollview2);
        //scrollView2.setScrollViewListener();
        //  System.out.println("REFE:" +df.toString());
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //HashMap<String, Object> dataMap = new HashMap<String, Object>();
                for (DataSnapshot swimmer : dataSnapshot.getChildren()) {
                    String val = swimmer.getValue().toString();
                    String last = val.substring(val.indexOf(' ') + 1);
                    String first = val.substring(0, val.indexOf(' '));
                    char lastC = last.charAt(0);
                    last = Character.toUpperCase(lastC) + last.substring(1);
                    char firstC = first.charAt(0);
                    first = Character.toUpperCase(firstC) + first.substring(1);
                    val = last + ", " + first;
                    // System.out.println("VALUEEE:" + val);

                    boolean in = false;
                    for (int x = 0; x < present.size(); x++) {
                        String e = present.get(x).trim();

                        if (e.equals(val)) {
                            in = true;
                        }
                    }
                    if (in == true)
                        mCheeseList.add(swimmer.getValue().toString());//+ swimmer.getValue().toString());
                    // System.out.println("has been read in here!!!");
                    //System.out.println("VALUEISIS:" + swimmer.getValue().toString());
                    adapterStuff(mCheeseList);
                    //ListView lvI = (ListView) view.findViewById(R.id.scrollview2);
                    //makeData(mCheeseList.size());
                    //adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.list_item_text, data);
                    //lvI.setAdapter(adapter);
                }
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }


    }


