package com.example.viraj.swimmingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class SwimmerInformation extends AppCompatActivity {
    DatabaseReference df;
    String newName;
    String firstName1;
    String lastName1;
    String name;
    String tName;
    String ageNum;
    String beststrokehere;
    String genderType;
    EditText firstName;
    EditText lastName;
    EditText age;
    RadioGroup rb;
    Spinner strokeSpinner;

    ScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_swimmer_information);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
       // setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        Intent i = getIntent();
         name = i.getStringExtra(EXTRA_MESSAGE);
         tName = i.getStringExtra("Other1");

        newName = tName.substring(0, 1).toUpperCase() + tName.substring(1, tName.indexOf(' ')) + " "
                + tName.substring(tName.indexOf(' ') + 1, tName.indexOf(' ') + 2).toUpperCase()
                + tName.substring(tName.indexOf(' ') + 2);
        final TextView nameText = (TextView) findViewById(R.id.name_text);
        String tempName = name.substring(0, 1).toUpperCase() + name.substring(1, name.indexOf(' ')) + " "
                + name.substring(name.indexOf(' ') + 1, name.indexOf(' ') + 2).toUpperCase()
                + name.substring(name.indexOf(' ') + 2);

        nameText.setText(tempName);
        final EditText info = (EditText) findViewById(R.id.lbl_message_body);
        sv = (ScrollView) findViewById(R.id.scroll);
        info.setEnabled(false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        df = FirebaseDatabase.getInstance().getReference("Swimmer Database").child(name);
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //HashMap<String, Object> dataMap = new HashMap<String, Object>();
                ArrayList<String> events = new ArrayList<>();

                events.add("50 Yd Free"); events.add("100 Yd Free"); events.add("200 Yd Free");
                events.add("500 Yd Free"); events.add("1000 Yd Free"); events.add("1650 Yd Free");

                events.add("100 Yd Back"); events.add("200 Yd Back");

                events.add("100 Yd Breast"); events.add("200 Yd Breast");

                events.add("100 Yd Fly"); events.add("200 Yd Fly");

                events.add("200 Yd IM"); events.add("400 Yd IM");

                events.add("50 M Free"); events.add("100 M Free"); events.add("200 M Free");
                events.add("400 M Free"); events.add("800 M Free"); events.add("1500 M Free");

                events.add("100 M Back"); events.add("200 M Back");

                events.add("100 M Breast"); events.add("200 M Breast");

                events.add("100 M Fly"); events.add("200 M Fly");

                events.add("200 M IM"); events.add("400 M IM");

               /* ArrayList<String> times = new ArrayList<>();

                for(int x = 0; x < events.size(); x ++) {

                }*/

               /* times.add(df.child("50 Yd Free").getKey());
                times.add(df.child("100 Yd Free").getKey());
                times.add(df.child("200 Yd Free").getKey());
                times.add(df.child("500 Yd Free").getKey());
                times.add(df.child("1000 Yd Free").getKey());
                times.add(df.child("1650 Yd Free").getKey());
                times.add(df.child("100 Yd Back").getKey());
                times.add(df.child("200 Yd Back").getKey());
                times.add(df.child("100 Yd Breast").getKey());
                times.add(df.child("200 Yd Breast").getKey());
                times.add(df.child("100 Yd Fly").getKey());
                times.add(df.child("200 Yd Fly").getKey());
                times.add(df.child("200 Yd IM").getKey());
                times.add(df.child("400 Yd IM").getKey());
                times.add(df.child("50 M Free").getKey());
                times.add(df.child("100 M Free").getKey());
                times.add(df.child("200 M Free").getKey());
                times.add(df.child("400 M Free").getKey());
                times.add(df.child("800 M Free").getKey());
                times.add(df.child("1500 M Free").getKey());
                times.add(df.child("100 M Back").getKey());
                times.add(df.child("200 M Back").getKey());
                times.add(df.child("100 M Breast").getKey());
                times.add(df.child("200 M Breast").getKey());
                times.add(df.child("100 M Fly").getKey());
                times.add(df.child("200 M Fly").getKey());
                times.add(df.child("200 M IM").getKey());
                times.add(df.child("400 M IM").getKey());*/



                ArrayList<String> databaseTimes = new ArrayList<>();
                for(int x = 0; x < 28; x ++)
                    databaseTimes.add("");
               // List<String> events = new ArrayList<>();
                for (DataSnapshot swimmer : dataSnapshot.getChildren()) {
                    String event = swimmer.getKey();

                    if(event.equals("Age") || event.equals("Gender")
                            || event.equals("Team")){}
                    else {
                        String time = swimmer.getValue().toString();
                        event = event.substring(1, event.length()-1);
                        System.out.println("eventis:" + event);
                        int x = events.indexOf(event);
                        System.out.println("index:" + x);
                        time = time.substring(0, time.length());
                        databaseTimes.set(x, time);
                        //info.append(event + " "  +time + "\n");
                    }
                }

                for(int a = 0; a < events.size(); a ++){
                    if(databaseTimes.get(a).equals("")){}
                    else{
                        info.append(events.get(a) + " " + databaseTimes.get(a) + "\n");

                        sv.post(new Runnable() {
                            @Override
                            public void run() {
                                sv.smoothScrollTo(0, info.getTop());
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {



            }
        });

        ImageButton ar = (ImageButton)findViewById(R.id.arrow_right);
        ar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PracticeFragment hldf = new PracticeFragment();
                /*getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.screen_area, hldf)
                        .commit();*/
                Intent i = new Intent(SwimmerInformation.this, HomeActivity.class);
                SwimmerInformation.this.startActivity(i);
                finish();

            }
        });

        ImageButton ds = (ImageButton)findViewById(R.id.dot_settings);
        ds.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), v);
                //inflating menu from xml resource
                popup.inflate(R.menu.dots_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                editMethod();//handle menu1 click
                                return true;
                            case R.id.delete:
                                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                FirebaseUser user = mAuth.getCurrentUser();
                                String cName =  tName ;

                                DatabaseReference df1 = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Swimmers").child(newName);

                                df1.setValue(null);
                                Intent i = new Intent(SwimmerInformation.this, HomeActivity.class);
                                SwimmerInformation.this.startActivity(i);
                                finish();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();


            }
        });



    }

    public void editMethod(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.new_swimmer, null);


         firstName1 = name.substring(0, name.indexOf(" "));
        firstName1 = firstName1.substring(0,1).toUpperCase() + firstName1.substring(1);
         lastName1 = name.substring(name.indexOf(' ') + 1);
        lastName1 = lastName1.substring(0, 1).toUpperCase() + lastName1.substring(1);






         firstName = (EditText) dialogView.findViewById(R.id.firstName);
         lastName = (EditText) dialogView.findViewById(R.id.lastName);
         age = (EditText) dialogView.findViewById(R.id.age);
         rb = (RadioGroup)dialogView.findViewById(R.id.radioButton);
        strokeSpinner = (Spinner) dialogView.findViewById(R.id.spinnerBestStroke);
        databaseStuff();
        firstName.setText(firstName1);
        lastName.setText(lastName1);
        //int rbIDD = rb.getCheckedRadioButtonId();




        builder.setView(dialogView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DatabaseReference df3;
                        FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
                        FirebaseUser user1 = mAuth1.getCurrentUser();
                        df3 = FirebaseDatabase.getInstance().getReference("Users").child(user1.getUid()).child("Swimmers").child(newName);
                       df3.setValue(null);
                        int rbID = rb.getCheckedRadioButtonId();

                        String gender;

                        if(rbID == R.id.radio_male)
                            gender = "Male";
                        else if(rbID == R.id.radio_female)
                            gender = "Female";
                        else
                            gender = "";

                        // spinner.setPrompt("Title");
                               /* ArrayAdapter<CharSequence> strokeAdapter = ArrayAdapter.createFromResource(getActivity(),
                                        R.array.best_stroke_array, android.R.layout.simple_spinner_item);
                                strokeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                strokeSpinner.setAdapter(strokeAdapter);*/
                        String stroke = strokeSpinner.getSelectedItem().toString();


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
                        FirebaseAuth mAuth2 = FirebaseAuth.getInstance();
                        FirebaseUser user2 = mAuth1.getCurrentUser();
                        String wName = ln + ", " + fn;
                        df = FirebaseDatabase.getInstance().getReference("Users").child(user2.getUid()).child("Swimmers");
                        df.child(wName).child("Age").setValue(swimmerAge);
                        df.child(wName).child("Gender").setValue(gender);
                        df.child(wName).child("Best Stroke").setValue(stroke);
                        String returnName = wName.toLowerCase();
                        String returnName2 = fn.toLowerCase() + " " + ln.toLowerCase();
                        Intent i = new Intent(getApplicationContext(), SwimmerInformation.class);
                        i.putExtra(EXTRA_MESSAGE, returnName2);
                        i.putExtra("Other1", returnName);
                        startActivity(i);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //HomeActivity.this.getDialog().cancel();
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void databaseStuff() {
        DatabaseReference dfNEW;
        DatabaseReference df2;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        dfNEW = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Swimmers").child(newName).child("Age");

        dfNEW.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //HashMap<String, Object> dataMap = new HashMap<String, Object>();
                ageNum = dataSnapshot.getValue() + "";
                setAge();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        df2 = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Swimmers").child(newName).child("Gender");

        df2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //HashMap<String, Object> dataMap = new HashMap<String, Object>();
                genderType = dataSnapshot.getValue() + "";

                setGender();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference df3;
        df3 = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Swimmers").child(newName).child("Best Stroke");

        df3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //HashMap<String, Object> dataMap = new HashMap<String, Object>();
                beststrokehere = "" + dataSnapshot.getValue();

                setBestStroke();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void setGender(){

        if(genderType.equals("Male"))
            rb.check(R.id.radio_male);
        else
            rb.check(R.id.radio_female);


    }

    public void setAge(){


        try{int newAge = Integer.parseInt(ageNum);
        age.setText(newAge + "");} catch(Exception e){}


    }

    public void setBestStroke(){
        if(beststrokehere.equals("Freestyle"))
            strokeSpinner.setSelection(0);
        else if(beststrokehere.equals("Backstroke"))
            strokeSpinner.setSelection(1);
        else if(beststrokehere.equals("Breastroke"))
            strokeSpinner.setSelection(2);
        else if(beststrokehere.equals("Butterfly"))
            strokeSpinner.setSelection(3);
        else if(beststrokehere.equals("IM"))
            strokeSpinner.setSelection(4);

    }

    @Override
    public void onBackPressed()
    {
     /*   PracticeFragment hldf = new PracticeFragment();
                /*getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.screen_area, hldf)
                        .commit();
        Intent i = new Intent(SwimmerInformation.this, HomeActivity.class);
        SwimmerInformation.this.startActivity(i);
        finish();
        // code here to show dialog
        super.onBackPressed(); */ // optional depending on your needs
    }

}

