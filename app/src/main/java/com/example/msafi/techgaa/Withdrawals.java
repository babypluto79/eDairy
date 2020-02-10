package com.example.msafi.techgaa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Withdrawals extends AppCompatActivity {
Toolbar toolbar;
EditText With;
Button button;
Calendar calendar;
String email,Amount;
FirebaseDatabase database;
DatabaseReference reference, childRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawals);
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
        With = findViewById(R.id.withdraw_amount);
        button = findViewById(R.id.submit);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Her");
        childRef = reference.push();
        Intent intent = getIntent();
         email = intent.getStringExtra("email");
         Amount = intent.getStringExtra("amount");
         Toast.makeText(getApplicationContext(), email + "\n" + Amount, Toast.LENGTH_LONG).show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alka();
            }
        });

    }
    public void Alka(){
        final String Kiasi = With.getText().toString();
        calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
       final String date = df.format(calendar.getTime());
        if(TextUtils.isEmpty(Kiasi)){
            With.setError("Enter amount to withdraw");
            With.requestFocus();
            return;
        }
        Double AmountAvailable = Double.parseDouble(Amount);
        int AmountWithdraw = Integer.parseInt(Kiasi);

        if(AmountWithdraw > AmountAvailable){
            Toast.makeText(getApplicationContext(), "You do not have enough money to withdraw " + "\t" + Kiasi + "\n" + date, Toast.LENGTH_LONG).show();
        }
        else {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("email", email);
                    hashMap.put("Amount", Kiasi);
                    hashMap.put("date",date);
                    String key = childRef.getKey();
                    reference.child(key).setValue(hashMap);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            Toast.makeText(getApplicationContext(), Kiasi + "\t" + "has been sent to your phone number" + "\n" + "at " + "\t" + date, Toast.LENGTH_LONG).show();
        }


    }
}
