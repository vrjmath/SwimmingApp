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
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HeatLaneDisplayFragment extends Fragment {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    //List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    Map<String, List<String>> map;

    Bundle inputBundle;
    ScrollView scroll;
    EditText text;
    ArrayList<String> order;
    String orderPool;
    String type;
    int numLanes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_heat_lane_display, container, false);

    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        expandableListDetail = getData();
        ArrayList<String> temp = new ArrayList<String>(expandableListDetail.keySet());
        Collections.sort(temp);
        //expandableListTitle = new ArrayList<String>();
        expandableListAdapter = new CustomExpandableListAdapter(getActivity(), temp, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
              /*  Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
              /*  Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                /*Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();*/
                return false;
            }
        });
    /*

        text = (EditText) view.findViewById(R.id.orderSwimmers);
        text.setEnabled(false); //prevent the et_laps to be editable

        scroll = (ScrollView) view.findViewById(R.id.scrollview3);
        inputBundle = getArguments();
        order = inputBundle.getStringArrayList("Order");
        setHeatLane(order);
        for(int x = 0; x < order.size(); x ++){
            text.append(order.get(x) + "\n");
        }*/

    }

    public void makeLanes(int a){
        for(int x = 0; x < a; x ++) {
            String temp = order.get(x);
            order.set(x, temp +" H" + x/5 + " L" + x%5);
        }
    }

    public void setHeatLane(List<String> cheese) {
        for (int a = 0; a < cheese.size(); a++) {
            int x = a%4 + 1;
            int y = a/4 + 1;
            String temp = cheese.get(a);
            cheese.set(a, temp + " H" + y + " L" + x);
        }
    }

    public HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        inputBundle = getArguments();
        System.out.println("Input:" + inputBundle.getString("Lanes"));
        numLanes = Integer.parseInt(inputBundle.getString("Lanes"));
        order = inputBundle.getStringArrayList("Order");
        orderPool = inputBundle.getString("OrderPool");
        type = inputBundle.getString("Type");
        //setHeatLane(order);
        System.out.println("HREREEREE:");
        for(int x = 0; x <order.size(); x ++)
            System.out.print(order.get(x) + ", ");
        ArrayList<String> swimmers = new ArrayList<String>();
        swimmers.addAll(Arrays.asList("ryan ng", "sumer hajela", "bryan ku",
                "jason kim", "brandon fung", "michael cheng", "boris strots",
                "taesu yim", "nilay kundu", "cara lee", "mei matsumoto",
                "alice cheng", "lei otsuka", "casey tsai", "ivy li",
                "jane choi", "patricia saito", "neeti badve", "jinsu yim",
                "iris dong", "isabel lee", "sherry lin", "olivia candelaria",
                "jacqueleine liu", "lukas peng", "viraj shitole", "iris yuh"));

        if(type.equals("Regular")){
        if(orderPool.equals("Heats")){
        for(int x = 0; x < order.size(); x++){
            if(x%numLanes==0){
                System.out.println("XIS:" + x);
                List<String> heat = new ArrayList<String>();
                for(int a = 0; a < numLanes; a ++) {
                    int lane = a + 1;
                    try{heat.add(lane + " " + order.get(x + a));}
                    catch(Exception e){}
                }
//                System.out.println("NAMES:" + heat.get(0) + heat.get(1));
                int heatNum = x/numLanes + 1;
                System.out.println("heatNum:" + heatNum);
                expandableListDetail.put("Heat " + heatNum, heat);
            }
        }}
        else if(orderPool.equals("Lanes")) {
            boolean in = false;
            for (int a = 1; a <= numLanes; a++) {
                if (a == numLanes) {
                    in = true;
                }
                System.out.println("beforeA:" + a);
                List<String> lane = new ArrayList<String>();
                for (int x = 1; x <= order.size(); x++) {
                    if (in == true) {
                        if (x % numLanes == 0) {
                            System.out.println("XXXX:" + x + "A:" + a + "NumLanes:" + numLanes + "Order" + order.get(x - 1));
                            int heat = x / numLanes;
                            try {
                                lane.add(heat + " " + order.get(x - 1));
                            } catch (Exception e) {
                            }
                        }
                    } else if (x % numLanes == a) {
                        System.out.println("XXXX:" + x + "A:" + a + "NumLanes:" + numLanes + "Order" + order.get(x - 1));
                        int heat = x / numLanes + 1;
                        try {
                            lane.add(heat + " " + order.get(x - 1));
                        } catch (Exception e) {
                        }
                    }
                }
                expandableListDetail.put("Lane " + a, lane);
            }
        }

        }
        else if(type.equals("Meet")) {
            String [][] arr = circleSeeder(order, numLanes);
            if(orderPool.equals("Heats"))
                arr = transposeMatrix(arr);

            for(int x = 0; x < arr[0].length; x++){
                for(int y = 0; y < arr.length; y ++){
                    int sum = x + 1;
                    if(arr[y][x] == null){arr[y][x] = null;}
                    else{
                    arr[y][x] = sum + " "  +arr[y][x];}

                }
            }
            if(orderPool.equals("Lanes")){
                for(int a = 0; a<numLanes; a++) {
                    int lane3 = a + 1;
                    ArrayList<String> temp = new ArrayList<String>();
                    for(int e = 0; e < arr[a].length; e ++){
                        if(arr[a][e] != null)
                            temp.add(arr[a][e]);
                    }
                    expandableListDetail.put("Lane " + lane3, temp);
                }
            }
            else if(orderPool.equals("Heats")){
                for(int a = 0; a<arr.length; a++) {
                    int heat3 = a + 1;
                    ArrayList<String> temp = new ArrayList<String>();
                    for(int e = 0; e < arr[a].length; e ++){
                        if(arr[a][e] != null)
                            temp.add(arr[a][e]);
                    }
                    expandableListDetail.put("Heat " + heat3, temp);
                }
            }
           /* if(orderPool.equals("Heats")) {
                String[][] arr = new String[(order.size() / numLanes) + 1][numLanes];
                for(int x = 1; x <= order.size(); x ++){
                    arr[(x-1)/numLanes][(x-1)%numLanes] = order.get(x-1);
                }
                for(int a = 0; a < order.size()/numLanes  + 1; a++){
                    for(int b = 0; b < numLanes; b ++)
                    System.out.println("Array fix:" + ":" + numLanes + ":" + a  + b + ":" +arr[a][b]);
                }
                String [][] arr2 = transposeMatrix(arr);
                if(numLanes%2 == 1) {
                    for (int a = (numLanes / 2); a > 0; a++) {
                        ArrayList<String> e = new ArrayList<>(Arrays.asList(arr[a]));
                        expandableListDetail.put("Lane " + a, e);
                        if(a%2 == 1){a = a-2;}
                        else {a = a + 1;}
                    }
                }
            }*/


        }

        return expandableListDetail;
    }

    public static String[][] transposeMatrix(String [][] m){
        String[][] temp = new String[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

    /**
     * Started 12-5-2018
     * Patricia Saito
     */

    /*{"jason kim", "sumer hajela", "ryan ng", "bryan ku", "jason kim"}
    {"nilay kundu", "boris strots", "michael cheng", "taesu yim", "cara lee"}
    {"casey tsai", "alice cheng", "mei matsumoto", "lei otsuka", "ivy li"}
    {"jinsu yim", "patricia saito", "jane choi", "neeti badve", "iris dong"}
    {"jacqueleine liu", "sherry lin", "isabel lee", "olivia candelaria", "lukas peng"}
    {null, "iris yuh", "viraj shitole", null, null}*/
    /**
     * Circle seeds a list of swimmers into a given number of lanes.
     * For an odd number of lanes:
     * 		Begin at the middle number, and alternate adding swimmers to
     * 		the lane on the right and left from the center.
     * For an even number of lanes:
     * 		Begin at the lower of the 2 middle values, and alternate
     * 		adding swimmers to the left, then right from the center.
     * Continue adding swimmers until the last lane (always numLanes) is
     * filled, and proceed to the next heat (rows) until no more swimmers
     * remain.
     * If the last heat contains less than 3 swimmers, swimmers from the
     * previous heat will be moved to the last heat so no heat contains
     * less than 3 swimmers.
     * @param swimmers ArrayList of swimmers ranked by time in an event.
     * @param numLanes Lanes available for seeding.
     * @return 2D array containing lane x heat for swimmers.
     */
    public String[][] circleSeeder(ArrayList<String> swimmers, int numLanes)
    {
        double numHeats = (double)swimmers.size()/numLanes;
        if(swimmers.size()%numLanes!=0)
            numHeats+=1;
        else if (swimmers.size()<=numLanes)
            numHeats = 1;
        String[][] heatLanes  = new String [numLanes][(int)numHeats];
        if(numLanes%2!=0) //odd lane case
        {
            for(int heatIndex = 0; heatIndex < (int)numHeats; heatIndex++)
            {
                int laneIndex = numLanes/2 + 1;
                int next = 0;
                while(heatLanes[numLanes-1][heatIndex]== null && swimmers.size()!=0)
                //as long as last lane is empty and swimmers still remain
                {
                    heatLanes[laneIndex-1][heatIndex] = swimmers.remove(0);
                    next = (Math.abs(next)+1);
                    if(next%2!=0)
                        next *= -1;
                    laneIndex += next;
                }
            }
            if(numLanes>3 && (int)numHeats>1)
            {
                int count = 0;
                for(int i=0; i<numLanes; i++)
                {
                    if(heatLanes[i][(int)numHeats-1] != null)
                    {
                        count++;
                    }
                }
                if(count==1)
                {
                    heatLanes[numLanes/2+1][(int)numHeats-1] = heatLanes[numLanes/2][(int)numHeats-1];
                    heatLanes[numLanes/2][(int)numHeats-1] = heatLanes[0][(int)numHeats-2];
                    heatLanes[0][(int)numHeats-2] = null;
                    heatLanes[numLanes/2-1][(int)numHeats-1] = heatLanes[numLanes-1][(int)numHeats-2];
                    heatLanes[numLanes-1][(int)numHeats-2] = null;
                }
                // 0 1 2 3 4
                // 1 2 3 4 5
                //   2 1 3
                else if(count==2)
                {
                    heatLanes[numLanes/2+1][(int)numHeats-1] = heatLanes[numLanes/2-1][(int)numHeats-1];
                    heatLanes[numLanes/2-1][(int)numHeats-1] = heatLanes[numLanes/2][(int)numHeats-1];
                    heatLanes[numLanes/2][(int)numHeats-1] = heatLanes[numLanes-1][(int)numHeats-2];
                    heatLanes[numLanes-1][(int)numHeats-2] = null;
                }
            }
        }
        else if(numLanes%2==0) //even lane case
        {
            for(int heatIndex = 0; heatIndex < (int)numHeats; heatIndex++)
            {
                int laneIndex = numLanes/2;
                int next = 0;
                while(heatLanes[numLanes-1][heatIndex]== null && swimmers.size()!=0)
                //as long as last lane is empty and swimmers still remain
                {
                    heatLanes[laneIndex-1][heatIndex] = swimmers.remove(0);
                    next = (Math.abs(next)+1);
                    if(next%2==0)
                        next *= -1;
                    laneIndex += next;
                }
            }
            if(numLanes>3 && (int)numHeats>1)
            {
                int count = 0;
                for(int i=0; i<numLanes; i++)
                {
                    if(heatLanes[i][(int)numHeats-1] != null)
                    {
                        count++;
                    }
                }
                if(count==1)
                {
                    heatLanes[numLanes/2-2][(int)numHeats-1] = heatLanes[numLanes/2-1][(int)numHeats-1];
                    heatLanes[numLanes/2-1][(int)numHeats-1] = heatLanes[0][(int)numHeats-2];
                    heatLanes[0][(int)numHeats-2] = null;
                    heatLanes[numLanes/2][(int)numHeats-1] = heatLanes[numLanes-1][(int)numHeats-2];
                    heatLanes[numLanes-1][(int)numHeats-2] = null;
                }
                // 0 1 2 3 4 5
                // 1 2 3 4 5 6
                //   3 1 2
                else if(count==2)
                {
                    heatLanes[numLanes/2-2][(int)numHeats-1] = heatLanes[numLanes/2][(int)numHeats-1];
                    heatLanes[numLanes/2][(int)numHeats-1] = heatLanes[numLanes/2-1][(int)numHeats-1];
                    heatLanes[numLanes/2-1][(int)numHeats-1] = heatLanes[numLanes-1][(int)numHeats-2];
                    heatLanes[numLanes-1][(int)numHeats-2] = null;
                }
            }
        }
        return heatLanes;
    }

    public void printHeatLanes(String[][]matrix)
    {
        System.out.print("         ");
        for(int h = 0; h< matrix[0].length; h++)
        {
            System.out.printf("  %-20s","Heat " + (h+1) + ":");
        }
        System.out.println();
        for(int lane = 0; lane < matrix.length; lane++)
        {
            System.out.printf("Lane %2d: ", lane+1);
            for(int heat = 0; heat < matrix[0].length; heat++)
            {
                if(matrix[lane][heat] != null)
                    System.out.printf("  %-20s",matrix[lane][heat]);
                else System.out.printf("  %-20s","<empty>");
            }
            System.out.println();
        }
    }



}
