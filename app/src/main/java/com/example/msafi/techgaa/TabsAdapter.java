package com.example.msafi.techgaa;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int numoftabs;
    public TabsAdapter(FragmentManager fm, int nooftabs){
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
                Farmers farmers = new Farmers();
                return farmers;
            case 1:
                Administrator administrator = new Administrator();
                return administrator;
            case 2:
                Collectors collectors = new Collectors();
                return collectors;
                default:
                    return null;

        }
    }
}
