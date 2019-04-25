package com.example.viraj.swimmingapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class SetCreateFragment extends Fragment {

    String distance;
    String stroke;
    String pool;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_set_create_fragment, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        distance = "50";
        stroke = "Freestyle";
        pool = "Short Course Yards";
        final MaterialSpinner distanceSpinner = (MaterialSpinner) view.findViewById(R.id.spinnerDistance);
        // spinner.setPrompt("Title");
        ArrayAdapter<CharSequence> distanceAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.distance_array, android.R.layout.simple_spinner_item);
        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceSpinner.setAdapter(distanceAdapter);
        distanceSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                distance = item;
                // System.out.println("Itemis:" + item);
            }
        });

        //final Spinner strokeSpinner = (Spinner) view.findViewById(R.id.spinnerStroke);
        final MaterialSpinner strokeSpinner = (MaterialSpinner) view.findViewById(R.id.spinnerStroke);
        //final MaterialBetterSpinner strokeSpinner = (MaterialBetterSpinner)
        // view.findViewById(R.id.spinnerStroke);

        // spinner.setPrompt("Title");
        ArrayAdapter<CharSequence> strokeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.stroke_array, android.R.layout.simple_spinner_item);
        strokeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        strokeSpinner.setAdapter(strokeAdapter);
        strokeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                stroke = item;
            }
        });

        final MaterialSpinner poolSpinner = (MaterialSpinner) view.findViewById(R.id.spinnerPool);
        ArrayAdapter<CharSequence> poolAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.pool_array, android.R.layout.simple_spinner_item);
        poolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        poolSpinner.setAdapter(poolAdapter);
        poolSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                pool = item;
            }
        });


        Button mMakeLanes = (Button) view.findViewById(R.id.Next);
        mMakeLanes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SetFragment sf = new SetFragment();
                boolean go = false;
                if(distance.equals("50") && stroke.equals("Freestyle")){go = true;}
                else if(distance.equals("100") && !stroke.equals("IM")){go = true;}
                else if(distance.equals("200")){go = true;}
                else if(distance.equals("400") && stroke.equals("IM")){go = true;}
                else if(distance.equals("400") && (stroke.equals("Freestyle") || stroke.equals("IM")) && pool.equals("Long Course Meters")){go = true;}
                else if(distance.equals("500") && stroke.equals("Freestyle") && pool.equals("Short Course Yards")){go = true;}
                else if(distance.equals("1000") && stroke.equals("Freestyle") && pool.equals("Short Course Yards")){go = true;}

                if(go == true) {
                    Bundle b = new Bundle();
                    b.putString("Distance", distance);
                    b.putString("Stroke", stroke);
                    b.putString("Pool", pool);
                    // System.out.println("INFORMATION:" +distance + stroke);
                    sf.setArguments(b);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.screen_area, sf)
                            .commit();
                }
                else
                {
                    Toast.makeText(getActivity(),
                            "Please enter a valid event", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

