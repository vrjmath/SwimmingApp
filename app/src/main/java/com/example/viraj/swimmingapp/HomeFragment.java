package com.example.viraj.swimmingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class HomeFragment extends Fragment {
    private ArrayList<String> data1 = new ArrayList<String>();
    private ArrayList<String> data2 = new ArrayList<String>();
    private ArrayList<String> data3 = new ArrayList<String>();
    ArrayList<Person> people = new ArrayList<Person>();
    PersonAdapter pa;

//    ArrayAdapter<String> adapter;
    private ListView lv;
    DatabaseReference df;
    private StorageReference mStorageRef;
    String stroke;



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
                    people.add(new Person(swimmer.getKey(), swimmer.child("Best Stroke").getValue() + "",
                            swimmer.child("Age").getValue() + ""));
                   // data1.add(swimmer.getKey() + " " );//+ swimmer.getValue().toString());
                    //data2.add(swimmer.child("Best Stroke") + "");
                    System.out.println("has been read");
                }
                /*ArrayList<Integer> order = new ArrayList<>();
                for(int x = 0; x < data.size(); x ++)
                    order.set(x, Integer.parseInt(data.get(x).substring(' ' + 1)));
                Collections.sort(order);
                for(int a = 0; a<order.size(); a ++)
                    System.out.println("NUMBER:" + order.get(a));*/
                //Collections.sort(people);
               // adapter.notifyDataSetChanged();
                pa.notify(people);
                Log.d("Data set", "It has changed");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        pa = new PersonAdapter(getActivity(), people);
        lv = (ListView) view.findViewById(R.id.listview);

        //TabHost tabHost = (TabHost) view.findViewById(android.R.id.tabhost);
//R.layout.list_item, R.id.list_item_text
      /*  adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item_recycler_template, R.id.lbl_message
                , data);*/
        lv.setAdapter(pa);
        //df = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        //df.child(ln + ", " + fn).child("Birthdate").setValue(strDate);
        //generateListContent();

        //adapter.notifyDataSetChanged();
        pa.notify(people);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
              /* AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();

                final View dialogView = inflater.inflate(R.layout.swimmer_times, null);
                final EditText firstName = (EditText) dialogView.findViewById(R.id.times);
                firstName.setEnabled(false);*/
                //String name1 = lv.getItemAtPosition(position).toString();
                String name1 = people.get(position).getmName();
                String tName = name1.toLowerCase();
                name1 = name1.substring(name1.indexOf(',') + 2).toLowerCase() + " " +
                        name1.substring(0,name1.indexOf(',')).toLowerCase();
                //name1 = "\"" + name1 + "\"";
            /*    System.out.println("NAMEIS:" + name1);
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                df = FirebaseDatabase.getInstance().getReference("Swimmer Database").child(name1);
                System.out.println("DATAREF:" + df);
                df.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //HashMap<String, Object> dataMap = new HashMap<String, Object>();
                        for (DataSnapshot swimmer : dataSnapshot.getChildren()) {
                            String event = swimmer.getKey();
                            if(event.equals("Age") || event.equals("Gender")
                                    || event.equals("Team")){}
                                    else {
                                String time = swimmer.getValue().toString();
                                event = event.substring(1, event.length()-1);
                                time = time.substring(0, time.length());
                                String temp = event + time;
                                //  System.out.println("TIMEARE:"  + temp);
                                firstName.append(event + " "  +time + "\n");
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                builder.setView(dialogView)
                        .setPositiveButton("", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {


                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //HomeActivity.this.getDialog().cancel();
                            }
                        });
                final AlertDialog dialog = builder.create();
                dialog.show();*/
                Intent i = new Intent(getActivity(), SwimmerInformation.class);
                i.putExtra(EXTRA_MESSAGE, name1);
                i.putExtra("Other1", tName);
                startActivity(i);

/*
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setEnterTransition(new Fade(Fade.IN));
                    setEnterTransition(new Fade(Fade.OUT));

                  /*  ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(this,
                                    new Pair<View, String>(viewRoot.findViewById(R.id.lbl_date_and_time_header),
                                            getString(R.string.))),


                    startActivity(i);


                } else {
                    startActivity(i);
                }*/
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
                                EditText age = (EditText) dialogView.findViewById(R.id.age);
                                RadioGroup rb = (RadioGroup)dialogView.findViewById(R.id.radioButton);
                                int rbID = rb.getCheckedRadioButtonId();
                                System.out.println("RADIO BUTTON ID:" + rbID);
                                String gender;

                                if(rbID == R.id.radio_male)
                                    gender = "Male";
                                else if(rbID == R.id.radio_female)
                                    gender = "Female";
                                else
                                    gender = "";
                                final Spinner strokeSpinner = (Spinner) dialogView.findViewById(R.id.spinnerBestStroke);
                                // spinner.setPrompt("Title");
                               /* ArrayAdapter<CharSequence> strokeAdapter = ArrayAdapter.createFromResource(getActivity(),
                                        R.array.best_stroke_array, android.R.layout.simple_spinner_item);
                                strokeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                strokeSpinner.setAdapter(strokeAdapter);*/
                                stroke = strokeSpinner.getSelectedItem().toString();


                                 /*
                               (DatePicker) dialogView.findViewById(R.id.datePicker);

                                int day = datePicker.getDayOfMonth();
                                int month = datePicker.getMonth();
                                int year = datePicker.getYear()- 1900;


                                SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
                                Date d = new Date(year, month, day);
                                String strDate = dateFormatter.format(d);*/

                                String fn = firstName.getText().toString().toLowerCase();
                                fn = fn.substring(0, 1).toUpperCase() + fn.substring(1);
                                String ln = lastName.getText().toString().toLowerCase();
                                ln = ln.substring(0, 1).toUpperCase() + ln.substring(1);
                                String swimmerAge = age.getText().toString();
                                /*if(!(TextUtils.isEmpty(fn) || TextUtils.isEmpty(ln))) {
                                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                                }*/
                                FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
                                FirebaseUser user1 = mAuth1.getCurrentUser();
                                df = FirebaseDatabase.getInstance().getReference("Users").child(user1.getUid()).child("Swimmers");
                                df.child(ln + ", " + fn).child("Age").setValue(swimmerAge);
                                df.child(ln + ", " + fn).child("Gender").setValue(gender);
                                df.child(ln + ", " + fn).child("Best Stroke").setValue(stroke);

                                //df.setValue(ln + ", " + fn + ": " + strDate);
                                //data.add(ln + ", " + fn + ": ");
                                people.add(new Person(ln + ", " + fn + ": ", stroke, swimmerAge));

                               // adapter.notifyDataSetChanged();
                                pa.notify(people);
                                HomeFragment hf = new HomeFragment();
                                getFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.screen_area, hf)
                                        .commit();

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
            //data.add("Bobs, Bob");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

}
