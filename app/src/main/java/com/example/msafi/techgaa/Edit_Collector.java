package com.example.msafi.techgaa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Edit_Collector extends AppCompatActivity {
TextView email, firstname, lastname, phone, id, village;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__collector);
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
        firstname = findViewById(R.id.collector_firstname);
        lastname = findViewById(R.id.collector_lastname);
        email = findViewById(R.id.collector_email);
        phone = findViewById(R.id.collector_phone);
        village = findViewById(R.id.collector_village);
        id = findViewById(R.id.collector_id);
        Intent intent = getIntent();
        String Fname = intent.getStringExtra("firstname");
        String Lname = intent.getStringExtra("lastname");
        String Mail = intent.getStringExtra("email");
        String Id = intent.getStringExtra("id");
        String Village = intent.getStringExtra("village");
        String Phone = intent.getStringExtra("phone");

        firstname.setText(Fname);
        lastname.setText(Lname);
        email.setText(Mail);
        id.setText(Id);
        village.setText(Village);
        phone.setText(Phone);


    }
}
