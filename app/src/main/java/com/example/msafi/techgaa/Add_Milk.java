package com.example.msafi.techgaa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Add_Milk extends AppCompatActivity {
Toolbar toolbar;
Button submit;
private EditText Amount;
private FirebaseDatabase database;
private DatabaseReference reference, childRef;
private String email, date1, name;
CalendarView calendarView;
TimePicker timePicker;
Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__milk);
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

        Amount = findViewById(R.id.Milk_quantity);
        submit = findViewById(R.id.Record);
        calendarView = findViewById(R.id.Select_Date);

        Intent intent = getIntent();
         email = intent.getStringExtra("email");
         name = intent.getStringExtra("name");
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Milk");
        childRef = reference.push();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordMilk();
            }
        });


    }
    public void recordMilk(){

        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        DateFormat dff = new SimpleDateFormat("HH:mm:ss");
        date = new Date();
        final String selectedDate = df.format(new Date(calendarView.getDate()));
        Calendar calendar = Calendar.getInstance();
        final String currentTime = dff.format(calendar.getTime());
        final String Quantity = Amount.getText().toString();
        if(TextUtils.isEmpty(Quantity)){
            Amount.setError("Enter the milk quantity in litres");
            Amount.requestFocus();
            return;
        }

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("email",email);
                hashMap.put("Date",selectedDate);
                hashMap.put("Time",currentTime);
                hashMap.put("Quantity", Quantity);
                hashMap.put("Collector",name);
                String key = childRef.getKey();
                reference.child(key).setValue(hashMap);
                Toast.makeText(getApplicationContext(), "Milk recorded successfully", Toast.LENGTH_LONG).show();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                String error = databaseError.getMessage().toString();
                Toast.makeText(getApplicationContext(), "Data not entered beacuse" + error , Toast.LENGTH_LONG).show();

            }
        });
        Amount.setText("");

    }



}
