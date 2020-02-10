package com.example.msafi.techgaa;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class F_With extends Fragment {
Context context;
DatabaseReference reference, beiReference;
FirebaseUser user;
FirebaseDatabase database;
FirebaseAuth auth;
String userMail;
ListView listView;
ArrayList<String> arrayList;
ArrayAdapter<String> adapter;

    public F_With() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f__with, container, false);
    }
    @Override
    public void onStart(){
        super.onStart();
        View view = getView();
        context = getContext();
        if(view != null){
         listView = view.findViewById(R.id.listview);
         arrayList = new ArrayList<>();
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            userMail = user.getEmail();
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("Her");
            Query query = reference.orderByChild("email").equalTo(userMail);
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                    while(iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        String withList = (String)next.child("Amount").getValue();
                        String date = (String)next.child("date").getValue();
                        String dia = "Amount: " +"\t"+ withList + "\n" + "On:" + "\t" + date;
                        arrayList.add(dia);
                        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrayList);
                        listView.setAdapter(adapter);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            query.addValueEventListener(valueEventListener);

        }
    }

}
