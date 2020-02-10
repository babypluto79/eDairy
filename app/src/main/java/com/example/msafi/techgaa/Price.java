package com.example.msafi.techgaa;

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

import java.util.HashMap;

public class Price extends AppCompatActivity {
Button button;
private EditText Price;
private FirebaseDatabase database;
private DatabaseReference reference;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);
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
        Price = findViewById(R.id.price_change);
        button = findViewById(R.id.submit);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Price");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordPrice();
            }
        });
    }
    public void recordPrice(){
        final String updatePrice = Price.getText().toString();
        if(TextUtils.isEmpty(updatePrice)){
            Price.setError("Enter price to change");
            Price.requestFocus();
            return;
        }
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reference.child("Bei").setValue(updatePrice);
                Toast.makeText(getApplicationContext(),"Price updated successfully", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Price.setText("");
    }
}
