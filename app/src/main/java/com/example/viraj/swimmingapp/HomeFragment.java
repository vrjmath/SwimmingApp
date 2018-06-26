package com.example.viraj.swimmingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeFragment extends android.app.Fragment {
    private ArrayList<String> data = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private ListView lv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = (ListView) view.findViewById(R.id.listview);

        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, R.id.list_item_text, data);
        lv.setAdapter(adapter);

        generateListContent();
        adapter.notifyDataSetChanged();
        //lv.setAdapter(new HomeActivity.MyListAdaper(this, R.layout.list_item, data));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MeetActivity.this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(HomeActivity.this, SpecificMeetActivity.class);
                //HomeActivity.this.startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lv.invalidateViews();
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                // Get the layout inflater
                LayoutInflater inflater = getActivity().getLayoutInflater();
                //final EditText name = (EditText) findViewById(R.id.firstName);

                final View dialogView = inflater.inflate(R.layout.new_swimmer, null);
                builder.setView(dialogView)
                        // Add action buttons
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                EditText firstName = (EditText) dialogView.findViewById(R.id.firstName);
                                EditText lastName = (EditText) dialogView.findViewById(R.id.lastName);
                                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

                                int day = datePicker.getDayOfMonth();
                                int month = datePicker.getMonth();
                                int year = datePicker.getYear()- 1900;


                                SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
                                Date d = new Date(year, month, day);
                                String strDate = dateFormatter.format(d);


                                String fn = firstName.getText().toString();
                                String ln = lastName.getText().toString();
                                /*if(!(TextUtils.isEmpty(fn) || TextUtils.isEmpty(ln))) {
                                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                                }*/
                                data.add(ln + ", " + fn + ": " + strDate);
                                //for(int a = 0; a < data.size(); a ++)
                                //System.out.println(data.get(a));
                                adapter.notifyDataSetChanged();

                                // Write a message to the database



                                //myRef.setValue("Hello, World!");

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //HomeActivity.this.getDialog().cancel();
                            }
                        });
                final AlertDialog dialog = builder.create();
                dialog.show();
                //dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                /*Button addSwimmer = (Button)findViewById(R.id.add_swimmer);
                addSwimmer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                EditText et = findViewById(R.id.firstName);*/
            }
        });


    }

    private void generateListContent() {
        for(int i = 0; i < 3; i++) {
            data.add("Bobs, Bob");
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meet, menu);
        return true;
    }*/

    /*@Override
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
            HomeFragment.ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                //HomeFragment.ViewHolder viewHolder = new HomeFragment().ViewHolder();
               // viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
               // viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                //viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
              //  convertView.setTag(viewHolder);
            }
            mainViewholder = (HomeFragment.ViewHolder) convertView.getTag();
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
