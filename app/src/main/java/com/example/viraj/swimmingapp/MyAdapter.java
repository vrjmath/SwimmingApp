package com.example.viraj.swimmingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.example.viraj.swimmingapp.TabFragment.int_items;


public class MyAdapter  extends FragmentPagerAdapter {


    public MyAdapter(FragmentManager fm)
    {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            //case 0:
                //return new SpecificMeetFragment();
            case 1:
                SpecificEventFragment sef = new SpecificEventFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("type", 2);
                bundle.putString("reference", "hello");
                sef.setArguments(bundle);
                return sef;
            case 0:
                return new SpecificMeetFragment();
            //case 3:
               // return new UlipFragment();


        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Events";
            case 1:
                return "1 W 800 Free";
            //case 2:
              //  return "Mutual  Funds";


        }

        return null;
    }
}
