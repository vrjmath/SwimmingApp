package com.example.viraj.swimmingapp;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;


public class HomeFragment extends Fragment {
    private ArrayList<String> data = new ArrayList<String>();
    private ArrayList<String> images = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private ListView lv;
    DatabaseReference df;
    private StorageReference mStorageRef;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStorageRef = FirebaseStorage.getInstance().getReference();


        //Uri file = Uri.fromFile(new File("2018JulySectEvent #1 Finals.txt"));
        final StorageReference riversRef = mStorageRef.child("2018JulySectEvent #1 Finals.txt");
        try{
        final File localFile = File.createTempFile("results", "txt");/*riversRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            // ...
                            //String url = taskSnapshot.getDownloadUrl().toString();
                            System.out.println("Success!");
                            //Log.e("firebase file is here ",";local tem file created  created " +localFile.toString());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle failed download
                            // ...
                            System.out.println("Fail!");
                        }
                    });*/

            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //System.out.println(riversRef.getDownloadUrl() + " is the url");
                    //DownloadManager.Request r = new DownloadManager.Request(uri);
                    //Intent temp = new Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString()));

                    //Intent downloadIntent = new Intent(Intent.ACTION_VIEW);
                    //downloadIntent.setData(Uri.parse(uri.toString()));
                    //startActivity(downloadIntent);

                    /*r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "fileName");
                    r.allowScanningByMediaScanner();
                    r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(r);*/
                    // Got the download URL for 'users/me/profile.png'
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }
        catch(Exception e){}




        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        df = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Swimmers");
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

        lv = (ListView) view.findViewById(R.id.listview);

        //TabHost tabHost = (TabHost) view.findViewById(android.R.id.tabhost);

        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, R.id.list_item_text,
                 data);
        lv.setAdapter(adapter);
        //df = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        //df.child(ln + ", " + fn).child("Birthdate").setValue(strDate);
        //generateListContent();

        adapter.notifyDataSetChanged();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();

                final View dialogView = inflater.inflate(R.layout.new_swimmer, null);
                builder.setView(dialogView)
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
                                FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
                                FirebaseUser user1 = mAuth1.getCurrentUser();
                                df = FirebaseDatabase.getInstance().getReference("Users").child(user1.getUid()).child("Swimmers");
                                df.child(ln + ", " + fn).child("Birthdate").setValue(strDate);

                                //df.setValue(ln + ", " + fn + ": " + strDate);
                                data.add(ln + ", " + fn + ": " + strDate);

                                adapter.notifyDataSetChanged();

                                //Write message to databse

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
