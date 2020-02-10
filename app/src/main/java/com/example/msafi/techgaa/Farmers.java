package com.example.msafi.techgaa;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Farmers extends Fragment {
public static final String TAG = Farmers.class.getSimpleName();
Context context;
  FirebaseDatabase database;
 DatabaseReference reference, childRef;
 private ListView datalistView;

 ArrayAdapter<String> arrayAdapter;
ArrayList<String> listItems;
ArrayList<String> listKeys;
ArrayList<String> listEmail;
ArrayList<String> listFirstname;
ArrayList<String> listLastname;
ArrayList<String> listVillage;
ArrayList<String> listPhone;
ArrayList<String> listId;
//don't fuck with me please
    private EditText itemText;
private Button findButton;
private Button deleteButton;
private Boolean searchMode = false;
private Boolean itemSelected = false;
private int selectedPosition = 0;
ArrayAdapter<String> adapter;
    public Farmers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_farmers, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        context = getContext();
        if (view != null) {

listFirstname = new ArrayList<String>();
listLastname = new ArrayList<String>();
listPhone = new ArrayList<String>();
listId = new ArrayList<String>();
listVillage = new ArrayList<String>();
listItems = new ArrayList<String>();
listKeys = new ArrayList<String>();
listEmail = new ArrayList<String>();
datalistView = view.findViewById(R.id.listview);
datalistView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Huncho");
       // childRef = reference.push();

            FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, New_Farmer.class));
                }
            });

adapter = new ArrayAdapter<String>(context,
        android.R.layout.simple_list_item_1, listItems);
datalistView.setAdapter(adapter);
datalistView.setChoiceMode(
        ListView.CHOICE_MODE_SINGLE
);
datalistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        selectedPosition = i;
        itemSelected = true;

    }
});
addChileEventListener();



        }

    }
    private void addChileEventListener(){
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
            //adapter.add((String)dataSnapshot.child("firstname").getValue());
                final String firstname = (String)dataSnapshot.child("firstname").getValue();
                final String lastname = (String) dataSnapshot.child("lastname").getValue();
                final String email = (String)dataSnapshot.child("email").getValue();
                final String village = (String)dataSnapshot.child("village").getValue();
                final String phone = (String)dataSnapshot.child("phone").getValue();
                final String id = (String)dataSnapshot.child("id").getValue();
                String bones = firstname + "\t" + lastname;

                adapter.add(bones);


                listKeys.add(dataSnapshot.getKey());
                listEmail.add(email);
                listFirstname.add(firstname);
                listLastname.add(lastname);
                listPhone.add(phone);
                listVillage.add(village);
                listId.add(id);
                String key = dataSnapshot.getKey();
                final int index = listKeys.indexOf(key);
                datalistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                        Intent intent = new Intent(context, Edit_Farmer.class);
                       String fame = (String)adapterView.getAdapter().getItem(i);
                       //intent.putExtra("data", fame);
                       String key1 = listKeys.get(i);
                       String mail = listEmail.get(i);
                       String first = listFirstname.get(i);
                       String last = listLastname.get(i);
                       String pho = listPhone.get(i);
                       String vill = listVillage.get(i);
                       String nId = listId.get(i);
                       intent.putExtra("key", key1);
                       intent.putExtra("firstname", first);
                       intent.putExtra("lastname" , last);
                       intent.putExtra("phone", pho);
                       intent.putExtra("village", vill);
                       intent.putExtra("email", mail);
                       intent.putExtra("id", nId);
                       intent.putExtra("index",index);
                       startActivity(intent);
                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addChildEventListener(childEventListener);
    }
    @Override
    public void onResume(){
        super.onResume();
        listItems = new ArrayList<String>();
        listKeys = new ArrayList<String>();
        listEmail = new ArrayList<String>();
    }


    }




