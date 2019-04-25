package com.example.viraj.swimmingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class PointsDisplayFragment extends Fragment {
    private ArrayList<String> data = new ArrayList<String>();
    ArrayAdapter<String> adapter1;
    ListView lv;
    Bundle bundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //bundle = getArguments();
        //getActivity().setTitle(bundle.getString("reference"));
        return inflater.inflate(R.layout.fragment_points_display, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = (ListView) view.findViewById(R.id.listview);
        adapter1 = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, R.id.list_item_text, data);
        lv.setAdapter(adapter1);
        bundle = getArguments();
        int[] points = bundle.getIntArray("Team Points");
        String event = bundle.getString("Event");
        data.add(event);
        data.add("GUNN: " + points[0]);
        data.add("HHS: " + points[1]);
        data.add("LAHS: " + points[2]);
        data.add("LOGA: " + points[3]);
        data.add("PALY: " + points[4]);
        data.add("MTVI: " + points[5]);
        data.add("SART: " + points[6]);


    }

    public class ViewHolder {

        ImageView thumbnail;
        TextView title;
        Button button;
    }
}
