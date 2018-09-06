package com.example.viraj.swimmingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;


public class TimeSwimmerPairFragment extends Fragment {
    private ArrayList<String> data = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    int x;
    Bundle bundle;
    TextView time;
    ArrayList<String> splits;
    ArrayList<String> names;
    DatabaseReference df;
    DatabaseReference df2;
    DatabaseReference df3;
    String setName;
    ArrayList<String> sortedNames;


    DatabaseReference dF;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time_swimmer_pair, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        bundle = getArguments();
        names = new ArrayList<>();
        //names = bundle.getStringArrayList("attendance");
        FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
        FirebaseUser user1 = mAuth1.getCurrentUser();

        df3 = FirebaseDatabase.getInstance().getReference("Users").child(user1.getUid()).child("Attendance");
        df3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot swimmer: dataSnapshot.getChildren()){
                    System.out.println("ITSLATE" + swimmer.getKey());
                    names.add(swimmer.getKey().toString() + " ");//+ swimmer.getValue().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        setName = bundle.getString("SetName");

        setName = setName.substring(0, setName.length());
        time = (TextView)view.findViewById(R.id.time);
        sortedNames = new ArrayList<String>();
        ListView lv = (ListView) view.findViewById(R.id.listview);

        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item_swimmer_time, R.id.list_item_text, data);
        lv.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        splits = bundle.getStringArrayList("splits");
        time.setText(splits.get(x));
        if(setName.contains("50")) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            df2 = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("50 Freestyle");
            df2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String temp = dataSnapshot.getValue(String.class);
                    int b = 0;
                    for(int x = 0; x < temp.length(); x ++){
                        if(temp.charAt(x) == ',')
                            b++;
                    }
                    ArrayList<String> allNames = new ArrayList<>();
                    for(int a = 0; a <=b-1; a++) {
                        String theSub = temp.substring(0, temp.indexOf(","));
                        System.out.println("SUB:" + theSub);
                        allNames.add(theSub);
                        //prevPoint = temp.indexOf(",") + 2;
                        if (a < b - 1) {
                            temp = temp.substring(temp.indexOf(",") + 2);
                        }
                    }
                    for(int p = 0; p <allNames.size(); p++){
                        for(int u = 0; u < names.size(); u++){
                            if(names.get(u).contains(allNames.get(p))) {
                                sortedNames.add(allNames.get(p));
                                System.out.println("ALLNAMESARE" + sortedNames);
                                data.add(allNames.get(p));
                                adapter.notifyDataSetChanged();
                                System.out.println("TRUEIS");
                            }

                        }
                    }

                    /*
                        for(int u = 0; u < names.size(); u ++) {
                            System.out.println("UIS:" + u);
                        if(names.get(u).contains(theSub))
                            sortedNames.add(theSub);
                        }
                    }*/
                    //do what you want with the email
                    //return;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if(setName.contains("100")) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            df2 = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("100 Freestyle");
            df2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String temp = dataSnapshot.getValue(String.class);
                    int b = 0;
                    for(int x = 0; x < temp.length(); x ++){
                        if(temp.charAt(x) == ',')
                            b++;
                    }
                    ArrayList<String> allNames = new ArrayList<>();
                    for(int a = 0; a <=b-1; a++) {
                        String theSub = temp.substring(0, temp.indexOf(","));
                        System.out.println("SUB:" + theSub);
                        allNames.add(theSub);
                        //prevPoint = temp.indexOf(",") + 2;
                        if (a < b - 1) {
                            temp = temp.substring(temp.indexOf(",") + 2);
                        }
                    }
                    for(int p = 0; p <allNames.size(); p++){
                        for(int u = 0; u < names.size(); u++){
                            if(names.get(u).contains(allNames.get(p))) {
                                sortedNames.add(allNames.get(p));
                                System.out.println("ALLNAMESARE" + sortedNames);
                                data.add(allNames.get(p));
                                adapter.notifyDataSetChanged();
                                System.out.println("TRUEIS");
                            }

                        }
                    }

                    /*
                        for(int u = 0; u < names.size(); u ++) {
                            System.out.println("UIS:" + u);
                        if(names.get(u).contains(theSub))
                            sortedNames.add(theSub);
                        }
                    }*/
                    //do what you want with the email
                    //return;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        System.out.println("DOESITMAKEITHERE");

       /* for(int m = 0; m < sortedNames.size(); m ++){
            System.out.println("HOPETHISWORKS" + sortedNames.get(m));
        }*/
       // for(int a = 0; a < sortedNames.size(); a ++)
            for(int e = 0; e < sortedNames.size(); e ++)
                data.add(sortedNames.get(e));
            adapter.notifyDataSetChanged();

        //generateListContent();
        //lv.setAdapter(new MeetFragment().getActivity().MyListAdaper(this, R.layout.list_item, data));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                x++;
                String temp = data.get(position);
               data.remove(position);
               adapter.notifyDataSetChanged();
                FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
                FirebaseUser user1 = mAuth1.getCurrentUser();

                df = FirebaseDatabase.getInstance().getReference("Users").child(user1.getUid()).child("SwimPractice").child(setName);
                df.child(temp).setValue(splits.get(x-1));
               if(data.size() == 0){
                   getFragmentManager()
                           .beginTransaction()
                           .replace(R.id.screen_area, new PracticeFragment())
                           .commit();
               }
               else
               time.setText(splits.get(x));


            }
        });
    }


    @Override
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
    }
}


