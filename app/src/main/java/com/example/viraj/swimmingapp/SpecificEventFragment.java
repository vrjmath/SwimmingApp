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
import android.widget.EditText;
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


public class SpecificEventFragment extends Fragment {
    private ArrayList<String> data = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    //MyListAdaper<Context, int, List<String>> adapter;
    ListView lv;
    DatabaseReference dF;
    Bundle bundle;
    EditText heat;
    EditText lane;
    Button timerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //bundle = getArguments();
        //getActivity().setTitle(bundle.getString("reference"));
        return inflater.inflate(R.layout.fragment_specific_event, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = (ListView) view.findViewById(R.id.listview);
        //lv.setAdapter(new MyListAdaper(getActivity(), R.layout.list_item_swimmer, data));
        adapter = new MyListAdapter(this.getActivity(), R.layout.list_item_swimmer, data);
        lv.setAdapter(adapter);

        //View inflatedView = getLayoutInflater().inflate(R.layout.list_item_swimmer, null);

       // heat = (EditText) inflatedView.findViewById(R.id.list_item_heat);
        //lane = (EditText) inflatedView.findViewById(R.id.list_item_lane);
        //ViewHolder vh = new ViewHolder();
       // vh.button =(Button) inflatedView.findViewById(R.id.list_item_timerButton);
       // System.out.println("timer button is :" +timerButton);

        bundle = getArguments();
            dF = FirebaseDatabase.getInstance().getReference("Meets")
                    .child(bundle.getString("meetReference"))
                    .child("Events")
                    .child(bundle.getString("reference"));

     /*   vh.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.screen_area, new ChronometerFragment())
                        .commit();
            }
        });*/

      /*  heat.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

                return false;
            }
        });

        lane.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

                return false;
            }
        });*/

        dF.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //HashMap<String, Object> dataMap = new HashMap<String, Object>();
                for(DataSnapshot swimmer: dataSnapshot.getChildren()){
                    //if(swimmer.getKey())
                    data.add(swimmer.getKey() + " " + swimmer.getValue().toString());
                }
                //Collections.sort(data);
                adapter.notifyDataSetChanged();
                //lv.getAdapter()
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
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

    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;

        private MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
            //System.out.println("entered adapter");
        }
        public void notifyAdapterDataSetChanged() {
            //... your custom logic
            notifyDataSetChanged();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            //System.out.println("entered view");
            if(convertView == null) {
                //System.out.println("entered convert");
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
               // viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.button = (Button) convertView.findViewById(R.id.list_item_timerButton);
                //System.out.println("Button code is: +" +viewHolder.button);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                    ChronometerFragment cf = new ChronometerFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("meetReference", bundle.getString("meetReference"));
                    bundle2.putString("eventReference", bundle.getString("reference"));
                    bundle2.putString("swimmerReference", data.get(position));
                    cf.setArguments(bundle2);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.screen_area, cf)
                            .commit();
                }
            });
            mainViewholder.title.setText(getItem(position));

            return convertView;
        }

    }

    public class ViewHolder {

        ImageView thumbnail;
        TextView title;
        Button button;
    }
}

