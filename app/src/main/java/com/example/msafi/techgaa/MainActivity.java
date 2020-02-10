package com.example.msafi.techgaa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button login_btn;
    Toolbar toolbar;
    private String Email;
    private DatabaseReference dbref, childRef;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        login_btn = findViewById(R.id.Home_login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginPage.class);
                startActivity(intent);
            }
        });
        //these are some firebase stuff
        //getting an instance of fiebaseAuth
        auth = FirebaseAuth.getInstance();
        //getting an instance of firebaseDatabase
        database = FirebaseDatabase.getInstance();
        //the databaseReference is used to get an instance of firebaseDatabase and create a path if not available
        dbref = database.getReference("admin");
        //i dont even know what the fuck this for!!
        childRef = dbref.push();

    }
}