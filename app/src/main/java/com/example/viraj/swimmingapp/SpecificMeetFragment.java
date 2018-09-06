package com.example.viraj.swimmingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;



public class SpecificMeetFragment extends Fragment {
    private ArrayList<String> data = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView lv;
    DatabaseReference dF;
    Bundle bundle1;
    DatabaseReference dF1;
    DatabaseReference dF2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_specific_meet, container, false);


    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bundle1 = getArguments();
        System.out.println("arguments" + getArguments());
        lv = (ListView) view.findViewById(R.id.listview);
        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, R.id.list_item_text, data);
        lv.setAdapter(adapter);
        dF = FirebaseDatabase.getInstance().getReference("Meets");
        System.out.println(bundle1.getString("Reference")+ "is this");
        dF1 = dF.child(bundle1.getString("Reference"));
        dF2 = dF1.child("Events");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SpecificEventFragment sef = new SpecificEventFragment();
                data.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("reference", data.get(position));
                bundle.putInt("type", 1);
                bundle.putString("meetReference", bundle1.getString("Reference"));
                sef.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.screen_area, sef)
                        .commit();
            }
        });

        dF2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> dataMap = new HashMap<String, Object>();
                for (DataSnapshot event : dataSnapshot.getChildren()) {
                    data.add(event.getKey());
                }
                //System.out.println(data.get(0));
                System.out.println("here:" + data.get(0));
                ArrayList<String> temp = new ArrayList<String>();
                for(int a = 0; a < 100; a ++){
                    for(int b = 0; b < data.size(); b ++) {
                        System.out.println("it comes here");
                        System.out.println("Data is:" +data.get(b));
                        System.out.println("String:" + data.get(b).substring(0, data.get(b).indexOf(" ")));
                        if(data.get(b).substring(0, data.get(b).indexOf(" ")).equals( a + ""))
                            temp.add(data.get(b));
                    }
                }
                //Collections.sort(data);
                for(int a = 0; a < temp.size(); a ++)
                    data.set(a, temp.get(a));

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meet, menu);
        return true;
    }*/

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

    private class MyListAdaper extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;

        private MyListAdaper(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

    }

    public class ViewHolder {

        ImageView thumbnail;
        TextView title;
        Button button;
    }
}