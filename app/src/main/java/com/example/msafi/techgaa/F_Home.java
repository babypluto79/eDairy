package com.example.msafi.techgaa;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class F_Home extends Fragment {
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
    TextView abie, Bei, Nicki, withdraw, TotalWithdrawals, RemainingAMount;
    ArrayList<Integer> Wamountlist;
String sum, userMail ,Castle, milkString, mia;
int milkWorth, add, milkPrice;
    Context context;
    public F_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_f__home, container, false);

    }
    @Override
    public void onStart(){
        super.onStart();
        View view = getView();
        context = getContext();
        if(view != null){
            context = getContext();
            name = view.findViewById(R.id.user_farmer_name);
            phone = view.findViewById(R.id.user_farmer_phone);
            listView = view.findViewById(R.id.farmer_records);
            auth = FirebaseAuth.getInstance();
            RemainingAMount = view.findViewById(R.id.tip);
            user = auth.getCurrentUser();
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("Huncho");
            Pricereference = database.getReference("Price");
            milkReference = database.getReference("Milk");
            withdraw = view.findViewById(R.id.withdraw);
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
                        final String Quantity = (String)next.child("Quantity").getValue();
                        String Date = (String) next.child("Date").getValue();
                        String Time = (String) next.child("Time").getValue();
                        String details = "Amount: " +"\t" + Quantity + "\t" + "Litres" + "\n" + "Recorded On: " + "\t" + Date + "\n" + "At: " +"\t" + Time;
                        milkList.add(details);
                        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, milkList);
                        listView.setAdapter(adapter);
                        int amount = Integer.parseInt(Quantity);
                        quantityList.add(amount);

                        int sum = 0;
                        for(int i = 0; i< quantityList.size(); i++)
                            sum += quantityList.get(i);
                        final String Samount = Integer.toString(sum);

                        Pricereference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String bei = dataSnapshot.child("Bei").getValue(String.class);

                                 milkPrice = Integer.parseInt(bei);
                                int milkQuantity = Integer.parseInt(Samount);

                                milkWorth = milkPrice * milkQuantity;
                                milkString = Integer.toString(milkWorth);
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



          query1.addValueEventListener(valueEventListener1);
          Bashment();







        }



        }

        public void Bashment(){

            Query query2 = withdrawalRef.orderByChild("email").equalTo(userMail);
            ValueEventListener valueEventListener2 = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                    Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                    while(iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        String withAmount = (String)next.child("Amount").getValue();
                        int heck = Integer.parseInt(withAmount);
                        Wamountlist.add(heck);
                         add = 0;
                        for(int j = 0; j<Wamountlist.size(); j++){
                            add += Wamountlist.get(j);
                        }
                        String With = Integer.toString(add);

Huncho();}






                    }else{
                        RemainingAMount.setText("Withdrawals = 0.0");
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
            double totalShit = eazy * milkPrice;
         double yemi = totalShit - add;
         final String doubleShit = Double.toString(yemi);
         RemainingAMount.setText("Account :" + "\t" + doubleShit);

           /* withdraw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Withdrawals.class);
                    intent.putExtra("email", userMail);
                    intent.putExtra("amount", doubleShit);
                    startActivity(intent);
                }
            });*/



        }


        }




