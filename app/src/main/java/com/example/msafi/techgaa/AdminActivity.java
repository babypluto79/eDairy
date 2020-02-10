package com.example.msafi.techgaa;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {
FirebaseDatabase database;
DatabaseReference reference;
Toolbar toolbar;
TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
      //TabLayout is a component that is used to manage multiple tabs in android
        // i have 3 tabs which are fragments that represent the admin, farmers and collectors
        //getting a refernce to the Tablayout widget
      tabLayout =  findViewById(R.id.tab_layout);
      //creating the tabs
      tabLayout.addTab(tabLayout.newTab().setText("Farmers"));
       tabLayout.addTab(tabLayout.newTab().setText("Admin"));
       tabLayout.addTab(tabLayout.newTab().setText("Collectors"));
       tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
       //viewpager is a component that allows you to swipe right and left like in whatsapp
       final ViewPager viewPager = findViewById(R.id.view_pager);
       TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
       viewPager.setAdapter(tabsAdapter);
       viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
       tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               viewPager.setCurrentItem(tab.getPosition());
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });

    }
}
