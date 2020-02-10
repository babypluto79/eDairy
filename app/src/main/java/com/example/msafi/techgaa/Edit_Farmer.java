package com.example.msafi.techgaa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class Edit_Farmer extends AppCompatActivity {
Toolbar toolbar;
TextView firstname,lastname,email, village, phone, id, Mamount, milk_price, TotalWithdrawal, RemainingAmount;
FirebaseDatabase database;
DatabaseReference reference, FarmerRef, price, iwithdraw;
Button delete, update, Payout;
String key, Mail;
int index, add, iPrice, iAmount;
ArrayAdapter<String> adapter;
ArrayList<String> details;
ArrayList<Integer> quantityList, withdrawalList;
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__farmer);
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
        firstname = findViewById(R.id.farmer_firstname);
        email = findViewById(R.id.farmer_email);
        village = findViewById(R.id.farmer_village);
        phone = findViewById(R.id.farmer_phone);
        id = findViewById(R.id.farmer_id);
       Payout = findViewById(R.id.payout);
       TotalWithdrawal = findViewById(R.id.withdrawal_amount);
       RemainingAmount = findViewById(R.id.no_withdrawal);
       Mamount = findViewById(R.id.milk_amount);
       milk_price = findViewById(R.id.milk_price);
        listView = findViewById(R.id.abie);
        details = new ArrayList<>();
        quantityList = new ArrayList<>();
        withdrawalList = new ArrayList<>();
         database = FirebaseDatabase.getInstance();
         reference = database.getReference("Huncho");
         FarmerRef = database.getReference("Milk");
         iwithdraw = database.getReference("Her");
         price = database.getReference("Price");
        Intent intent = getIntent();
        String Fname = intent.getStringExtra("firstname");
        String Lname = intent.getStringExtra("lastname");
        String name = Fname + "\t" + Lname;
        Mail = intent.getStringExtra("email");
        String Id = intent.getStringExtra("id");
        String Phone = intent.getStringExtra("phone");
        String Village = intent.getStringExtra("village");
        key = intent.getStringExtra("key");
        firstname.setText("Name: " +"\t" +  name);
        email.setText("Email:" +"\t"+ Mail);
        village.setText("Village:" +"\t" + Village);
        phone.setText("Phone: " +"\t" +  Phone);
        id.setText("National Id: " +"\t" + Id);

        Payout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Sorry, No Records for the user yet!", Toast.LENGTH_LONG).show();
            }
        });

        Query query = FarmerRef.orderByChild("email").equalTo(Mail);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while(iterator.hasNext()) {
                    DataSnapshot next = (DataSnapshot) iterator.next();
                    String Quantity = (String) next.child("Quantity").getValue();
                    String Date = (String) next.child("Date").getValue();
                    String Time = (String) next.child("Time").getValue();
                    String yote = "Amount: " + "\t" + Quantity + "\t" + "Litres" + "\n" + "Recorded On: " + "\t" + Date + "\n" + "On: " + "\t" + Time;
                    details.add(yote);
                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, details);
                    listView.setAdapter(adapter);

                  int amount = Integer.parseInt(Quantity);
                  quantityList.add(amount);
                    int sum = 0;
                    for(int i = 0; i< quantityList.size(); i++)
                        sum += quantityList.get(i);
                     final String Samount = Integer.toString(sum);

                Mamount.setText(Samount + "\t" + "Litres");
                price.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String price = dataSnapshot.child("Bei").getValue(String.class);
                        milk_price.setText("Price Per Litre: " + "\t" + price);
                        iPrice = Integer.parseInt(price);
                        iAmount = Integer.parseInt(Samount);

                        int Milkworth = iPrice * iAmount;
                        Huncho();

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
        query.addListenerForSingleValueEvent(valueEventListener);
        Bashment();

    }
    public void Bashment() {

        Query query2 = iwithdraw.orderByChild("email").equalTo(Mail);
        ValueEventListener valueEventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        String withAmount = (String) next.child("Amount").getValue();
                        int heck = Integer.parseInt(withAmount);
                        withdrawalList.add(heck);
                        add = 0;
                        for (int j = 0; j < withdrawalList.size(); j++) {
                            add += withdrawalList.get(j);
                        }
                        String With = Integer.toString(add);
                        TotalWithdrawal.setText("Total Withdrawals:" + "\t" + With);
                        Huncho();

                    }


                } else {
                    RemainingAmount.setText("Withdrawals = 0.0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query2.addValueEventListener(valueEventListener2);


    }
    public void Huncho(){
        int sum = 0;
        for(int i = 0; i< quantityList.size(); i++)
            sum += quantityList.get(i);
        final String Samount = Integer.toString(sum);
        double eazy = Double.parseDouble(Samount);
        double totalShit = eazy * iPrice;
        double yemi = totalShit - add;
        final String doubleShit = Double.toString(yemi);
        RemainingAmount.setText("remaining:" + "\t" + doubleShit);

        Payout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Edit_Farmer.this, Withdrawals.class);
                intent.putExtra("email", Mail);
                intent.putExtra("amount", doubleShit);
                startActivity(intent);
            }
        });



    }

    }


