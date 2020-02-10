package com.example.msafi.techgaa;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class Collectors extends Fragment {
FirebaseDatabase database;
DatabaseReference reference;
Context context;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> listFirstname;
    ArrayList<String> listLastname;
    ArrayList<String> listEmail;
    ArrayList<String> listId;
    ArrayList<String> listVillage;
    ArrayList<String> listPhone;
    ArrayList<String> listKey;
    public Collectors() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collectors, container, false);
    }
    @Override
    public void onStart(){
        super.onStart();
        View view = getView();
        context = getContext();
        arrayList = new ArrayList<String>();
        listFirstname = new ArrayList<String>();
        listLastname = new ArrayList<String>();
        listVillage = new ArrayList<String>();
        listPhone = new ArrayList<String>();
        listId = new ArrayList<String>();
        listEmail = new ArrayList<String>();
        listKey = new ArrayList<String>();
        if(view != null) {

            FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, Test.class));
                }
            });
            listView = view.findViewById(R.id.listView);
           database = FirebaseDatabase.getInstance();
           reference = database.getReference("Takeoff");
           reference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                   Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                   while(iterator.hasNext()){
                   DataSnapshot next = iterator.next();
                   final String firstnames = (String) next.child("firstname").getValue();
                   final String lastnames = (String) next.child("lastname").getValue();
                   final String village = (String) next.child("village").getValue();
                   final String national_id = (String) next.child("id").getValue();
                   final String phone = (String) next.child("phone").getValue();
                   final String email = (String) next.child("email").getValue();
                   String Bones = firstnames + "\t" + lastnames;
                   arrayList.add(Bones);
                   listPhone.add(phone);
                   listFirstname.add(firstnames);
                   listLastname.add(lastnames);
                   listEmail.add(email);
                   listId.add(national_id);
                   listKey.add(dataSnapshot.getKey());
                   listVillage.add(village);

                   arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayList);
                   listView.setAdapter(arrayAdapter);
                   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                           Intent intent = new Intent(context, Edit_Collector.class);
                          String first = listFirstname.get(i);
                          String last = listLastname.get(i);
                          String Mail = listEmail.get(i);
                          String Pho = listPhone.get(i);
                          String Nid = listId.get(i);
                          String Vill = listVillage.get(i);
                          String key = listKey.get(i);
                          intent.putExtra("firstname", first);
                          intent.putExtra("lastname", last);
                          intent.putExtra("email", Mail);
                          intent.putExtra("village", Vill);
                          intent.putExtra("phone", Pho);
                          intent.putExtra("id", Nid);
                          intent.putExtra("key", key);
                          Toast.makeText(context, Nid, Toast.LENGTH_LONG).show();
                           startActivity(intent);

                       }
                   });
               }
           }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });

        }
    }
    @Override
    public void onResume(){
        super.onResume();
        arrayList = new ArrayList<String>();
    }

}
