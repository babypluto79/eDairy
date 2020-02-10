package com.example.msafi.techgaa;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class Tabs2 extends FragmentStatePagerAdapter {
    int numoftabs;
    public Tabs2(FragmentManager fm, int nooftabs){
        super(fm);
        this.numoftabs = nooftabs;
    }
    @Override
    public int getCount(){
        return numoftabs;
    }
    @Override
    public Fragment getItem(int position){
        switch(position){
            case 0:
                F_Home fHome = new F_Home();
                return fHome;
            case 1:
               F_With fWith = new F_With();
               return fWith;

            default:
                return null;

        }
    }
}
