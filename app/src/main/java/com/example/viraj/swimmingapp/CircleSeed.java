package com.example.viraj.swimmingapp;

/**
 * Started 12-5-2018
 * Patricia Saito
 */

import java.util.Arrays;
import java.util.ArrayList;

public class CircleSeed {
    public static void main(String[]args)
    {
        ArrayList<String> swimmers = new ArrayList<String>();
        swimmers.addAll(Arrays.asList("ryan ng", "sumer hajela", "bryan ku",
                "jason kim", "brandon fung", "michael cheng", "boris strots",
                "taesu yim", "nilay kundu", "cara lee", "mei matsumoto",
                "alice cheng", "lei otsuka", "casey tsai", "ivy li",
                "jane choi", "patricia saito", "neeti badve", "jinsu yim",
                "iris dong", "isabel lee", "sherry lin", "olivia candelaria",
                "jacqueleine liu", "lukas peng", "viraj shitole", "iris yuh"));
        CircleSeed vrj = new CircleSeed();
        vrj.printHeatLanes(vrj.circleSeeder(swimmers, 5));
    }

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

    /**
     * Just for the sake of having a visual
     * @param matrix
     */
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
