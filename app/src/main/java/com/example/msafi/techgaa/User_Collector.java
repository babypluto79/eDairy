package com.example.msafi.techgaa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class User_Collector extends AppCompatActivity {
FirebaseAuth auth;
FirebaseUser user;
TextView textView, textView1, textView3;
FirebaseDatabase database;
DatabaseReference reference, dbRef;
Toolbar toolbar;
ArrayList<String> farmerList = new ArrayList<String>();
ArrayList<String> phone = new ArrayList<String>();
ArrayAdapter<String> adapter;
ListView listView;
Nicki nicki;
ArrayList<String> EmailList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__collector);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        textView = findViewById(R.id.user_collector_name);
        textView1 = findViewById(R.id.user_collector_village);
        textView3 = findViewById(R.id.user_collector_phone);
        listView = findViewById(R.id.farmers);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Takeoff");
        dbRef = database.getReference("Huncho");
        String Mail = user.getEmail();
        textView.setText(Mail);
         nicki = new Nicki();
        phone = new ArrayList<String>();

        Query query = reference.orderByChild("email").equalTo(Mail);

        ValueEventListener queryValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while(iterator.hasNext()) {
                    DataSnapshot next = (DataSnapshot) iterator.next();
                    String firstname = (String) next.child("firstname").getValue();
                   String  village = (String) next.child("village").getValue();
                    String lastname = (String) next.child("lastname").getValue();
                    String phone = (String) next.child("phone").getValue();
                   final String name = firstname + "\t" + lastname;
                    String vill = "Collecting from: " +"\t" + village;
                   textView.setText( name);
                    textView1.setText(vill);
                    textView3.setText(phone);
                    Query query1 = dbRef.orderByChild("village").equalTo(village);
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                            Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                            while(iterator.hasNext()) {
                                DataSnapshot next = (DataSnapshot) iterator.next();
                                String Fname = (String) next.child("firstname").getValue();
                                String Lname = (String) next.child("lastname").getValue();
                                String LPhone = (String)next.child("phone").getValue();
                                String Lvillage = (String)next.child("village").getValue();
                                String Lemail = (String)next.child("email").getValue();
                                String last = "name" + "\t" + Fname + "\t" + Lname + "\n" + "From: " + "\t" + Lvillage + "\t" + "village " + "\n" + "Contact: " + "\t" + LPhone;
                                farmerList.add(last);
                                EmailList.add(Lemail);
                                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, farmerList);
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                        String Mail = EmailList.get(i);
                                        Intent intent = new Intent(User_Collector.this, Add_Milk.class);
                                        intent.putExtra("email",Mail);
                                        intent.putExtra("name", name);
                                        startActivity(intent);
                                    }
                                });


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
query1.addListenerForSingleValueEvent(valueEventListener);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addListenerForSingleValueEvent(queryValueListener);




    }
}
