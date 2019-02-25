package com.example.viraj.swimmingapp;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<String> data = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private ListView lv;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navView = navigationView.inflateHeaderView(R.layout.nav_header_home);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        TextView navUsername = (TextView) navView.findViewById(R.id.coachEmail);
        navUsername.setText(auth.getCurrentUser().getEmail().toString());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.screen_area, new HomeFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //FragmentManager FM = getSupportFragmentManager();
        if (id == R.id.nav_meets) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.screen_area, new MeetFragment())
                    .commit();

        } else if (id == R.id.nav_practice) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.screen_area, new PracticeFragment())
                    .commit();

        } else if (id == R.id.nav_swimmers) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.screen_area, new HomeFragment())
                    .commit();

        } else if (id == R.id.nav_attendance) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.screen_area, new AttendanceFragment())
                    .commit();

        } else if (id == R.id.nav_settings) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.screen_area, new SettingsFragment())
                    .commit();

        } else if (id == R.id.nav_share) {
            String to = "swimsharktech@gmail.com";
            String subject = "SwimApp Feedback";
            String message = "Hello";
            String[] recipients = to.split(",");
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, message);
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Select Email app"));

        } else if (id == R.id.nav_send) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(HomeActivity.this, FirstActivity.class);
            HomeActivity.this.startActivity(intent);

        } else if(id == R.id.privacy_policy) {
            String urlString = "https://swimsharktech.com/privacy_policy.html";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                // Chrome browser presumably not installed so allow user to choose instead
                intent.setPackage(null);
                startActivity(intent);
            }

        } else if(id == R.id.about_us) {
            String urlString = "https://swimsharktech.com/about-us.html";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                // Chrome browser presumably not installed so allow user to choose instead
                intent.setPackage(null);
                startActivity(intent);
            }

        } else if(id == R.id.request) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Requesting to update the database will update your added/edited swimmers and " +
                    "the lane maker.");

        // Add the buttons
            builder.setPositiveButton("Request", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    String date = new Date().toString();
                    FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
                    FirebaseUser user1 = mAuth1.getCurrentUser();
                    //System.out.println("USER!:" + user1.getUid()));
                    DatabaseReference df = FirebaseDatabase.getInstance().getReference("Requests").child(date);
                    df.setValue(user1.getUid());

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




        /*@Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            HomeActivity.ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                HomeActivity.ViewHolder viewHolder = new HomeActivity.ViewHolder();
                viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                //viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (HomeActivity.ViewHolder) convertView.getTag();
            /*mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();

                }
            });
            mainViewholder.title.setText(getItem(position));

            return convertView;
        }*/



    public class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }


}
