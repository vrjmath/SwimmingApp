package com.example.viraj.swimmingapp;

import android.content.Context;
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
import java.util.List;


public class MeetFragment extends Fragment {
    private ArrayList<String> data = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    DatabaseReference dF;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meet, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView lv = (ListView) view.findViewById(R.id.listview);

        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, R.id.list_item_text, data);
        lv.setAdapter(adapter);
        //generateListContent();
        //lv.setAdapter(new MeetFragment().getActivity().MyListAdaper(this, R.layout.list_item, data));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MeetActivity.this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
               // Intent intent = new Intent(getActivity(), SpecificMeetActivity.class);
               // getActivity().startActivity(intent);


                SpecificMeetFragment smf = new SpecificMeetFragment();
                if(data.get(position).equals("2019 SCVAL Championships")) {
                    Bundle bundle = new Bundle();
                    //System.out.println("Position is" + data.get(position));
                    bundle.putString("Reference", "2018 SCVAL Championships");
                    System.out.println("INSIDE MEET" + bundle.getString("Reference"));
                    //bundle.putInt("type", 1);
                    smf.setArguments(bundle);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.screen_area, smf)
                            .commit();
                }
            }
        });

        dF = FirebaseDatabase.getInstance().getReference("Meets");
        dF.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //HashMap<String, Object> dataMap = new HashMap<String, Object>();
                for(DataSnapshot meet: dataSnapshot.getChildren()){
                    if(meet.getKey().equals("2018 SCVAL Championships"))
                        data.add("2019 SCVAL Championships");
                    else
                    data.add(meet.getKey());
                }
                //Collections.sort(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void generateListContent() {
        for(int i = 0; i < 1; i++) {
            data.add("DACA CBA+ Long Course");
        }
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

       /* @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MeetActivity.ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                MeetActivity.ViewHolder viewHolder = new MeetFragment().ViewHolder();
                viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                //viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (MeetActivity.ViewHolder) convertView.getTag();
            /*mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();

                }
            });
            mainViewholder.title.setText(getItem(position));

            return convertView;
        }*/
    }
    public class ViewHolder {

        ImageView thumbnail;
        TextView title;
        Button button;
    }



    }






