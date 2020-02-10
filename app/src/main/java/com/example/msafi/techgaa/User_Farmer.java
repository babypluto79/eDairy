package com.example.msafi.techgaa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class User_Farmer extends AppCompatActivity {
FirebaseUser user;
FirebaseAuth auth;
TextView name,village,phone;
FirebaseDatabase database;
DatabaseReference reference, milkReference, Pricereference, withdrawalRef;
Toolbar toolbar;
ArrayAdapter<String> adapter;
ArrayList<String> milkList;
ArrayList<Integer> quantityList;
ListView listView;
TextView abie, Bei, Nicki, withdraw;
ArrayList<Integer> Wamountlist;
String userMail;
int milkWorth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__farmer);
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
        name = findViewById(R.id.user_farmer_name);
        phone = findViewById(R.id.user_farmer_phone);
        listView = findViewById(R.id.farmer_records);
        abie = findViewById(R.id.amount);
        auth = FirebaseAuth.getInstance();
        Bei = findViewById(R.id.price);
        Nicki = findViewById(R.id.memoi);
        withdraw = findViewById(R.id.withdraw);

        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Huncho");
        Pricereference = database.getReference("Price");
        milkReference = database.getReference("Milk");
        withdrawalRef = database.getReference("Her");
        userMail = user.getEmail();
         milkList = new ArrayList<>();
         quantityList = new ArrayList<Integer>();
         Wamountlist = new ArrayList<Integer>();
        Query query = reference.orderByChild("email").equalTo(userMail);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while(iterator.hasNext()) {
                    DataSnapshot next = (DataSnapshot) iterator.next();
                    String firstname = (String) next.child("firstname").getValue();
                    String Village = (String) next.child("village").getValue();
                    String lastname = (String) next.child("lastname").getValue();
                    String Phone = (String) next.child("phone").getValue();
                    String Fname = firstname + "\t" +lastname;
                    name.setText(Fname);
                    phone.setText(Phone);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
        Query query1 = milkReference.orderByChild("email").equalTo(userMail);
        ValueEventListener valueEventListener1 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while(iterator.hasNext()) {
                    DataSnapshot next = (DataSnapshot) iterator.next();
                    String Quantity = (String)next.child("Quantity").getValue();
                    String Date = (String) next.child("Date").getValue();
                    String Time = (String) next.child("Time").getValue();
                    String details = "Amount: " +"\t" + Quantity + "\t" + "Litres" + "\n" + "Recorded On: " + "\t" + Date + "\n" + "At: " +"\t" + Time;
                    milkList.add(details);
                    adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, milkList);
                    listView.setAdapter(adapter);
                   int amount = Integer.parseInt(Quantity);
                   quantityList.add(amount);

                    int sum = 0;
                    for(int i = 0; i< quantityList.size(); i++)
                        sum += quantityList.get(i);
                       final String Samount = Integer.toString(sum);

                   abie.setText(Samount + "\t" + "Litres");
                    Pricereference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String bei = dataSnapshot.child("Bei").getValue(String.class);
                            Bei.setText("Price per Litre: " + "\t" + bei);

                            int milkPrice = Integer.parseInt(bei);
                            int milkQuantity = Integer.parseInt(Samount);

                             milkWorth = milkPrice * milkQuantity;
                            final String milkString = Integer.toString(milkWorth);
                            Nicki.setText("Total Amount:" + "\t" + milkString);

                   withdraw.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Intent intent = new Intent(User_Farmer.this, Withdrawals.class);
                           intent.putExtra("email",userMail);
                           intent.putExtra("Amount", milkString);
                           startActivity(intent);

                       }
                   });




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query1.addListenerForSingleValueEvent(valueEventListener1);
        onStart();

    }
    public void onStart(){
        super.onStart();
        Query query2 = withdrawalRef.orderByChild("email").equalTo(userMail);
        ValueEventListener valueEventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while(iterator.hasNext()) {
                    DataSnapshot next = (DataSnapshot) iterator.next();
                    String amount = (String)next.child("Amount").getValue();
                    String Wemail = (String) next.child("email").getValue();
                    String date = (String)next.child("date").getValue();

                    int Wamount = Integer.parseInt(amount);
                    Wamountlist.add(Wamount);
                    int sum = 0;

                    for(int i = 0; i< Wamountlist.size(); i++)
                        sum += Wamountlist.get(i);
                    String Remaining_amount = Integer.toString(sum);

                    double Sremaining = milkWorth - sum;
                    final String finalAmount = Double.toString(Sremaining);
                    Nicki.setText(finalAmount);
                    withdraw.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(User_Farmer.this, Withdrawals.class);
                            intent.putExtra("email",userMail);
                            intent.putExtra("Amount",finalAmount);
                            startActivity(intent);
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query2.addListenerForSingleValueEvent(valueEventListener2);
    }
}
