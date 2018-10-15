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

import java.util.ArrayList;
import java.util.List;


public class LaneFragment extends Fragment {
    private ArrayList<String> data = new ArrayList<String>();

    private ObservableScrollView scrollView1 = null;
    private ObservableScrollView scrollView2 = null;

    ArrayList<String> mCheeseList;
    ArrayAdapter<String> adapter;
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
        v = view;
        mCheeseList = new ArrayList<String>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference df = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("\"100 M Free\"");
        //df.child(ln + ", " + fn).child("Birthdate").setValue(strDate);
        //df =

        //scrollView1 = (ObservableScrollView) view.findViewById(R.id.scrollview1);
       // scrollView1.setScrollViewListener();
       // scrollView2 = (ObservableScrollView) view.findViewById(R.id.scrollview2);
        //scrollView2.setScrollViewListener();
        System.out.println("REFE:" +df.toString());
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //HashMap<String, Object> dataMap = new HashMap<String, Object>();
                for(DataSnapshot swimmer: dataSnapshot.getChildren()){
                    mCheeseList.add(swimmer.getValue().toString());//+ swimmer.getValue().toString());
                    System.out.println("has been read in here!!!");
                    System.out.println("VALUEISIS:" + swimmer.getValue().toString());
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


        System.out.println("DONE!!!");
       for (int i = 0; i < mCheeseList.size(); ++i) {
            System.out.println("HEREIS:" + mCheeseList.get(i));
        }

        Button next = (Button) view.findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeatLaneDisplayFragment hldf = new HeatLaneDisplayFragment();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("Order", mCheeseList );
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
        System.out.println("ITWASCALLED!");
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


    }


