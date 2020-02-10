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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class New_Farmer extends AppCompatActivity {


    private EditText textemail, textpassword, textfirstame, textlastname, textvillage, textconfirm, textphone, textid;
    private FirebaseAuth auth;
    private DatabaseReference reference,childRef;
    private FirebaseDatabase database;

    private Button reg;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        textemail = findViewById(R.id.test_email);
        textfirstame = findViewById(R.id.test_firstname);
        textlastname = findViewById(R.id.test_lastname);
        textvillage = findViewById(R.id.test_village);
        textpassword = findViewById(R.id.test_pass);
        textconfirm = findViewById(R.id.test_confirm);
        textphone = findViewById(R.id.test_phone);
        textid = findViewById(R.id.test_id);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Huncho");
        childRef = reference.push();
        reg = findViewById(R.id.test_reg);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              registerUser();
            }
        });

    }
    public void registerUser(){
        String Firstname = textfirstame.getText().toString();
        String Lastname = textlastname.getText().toString();
        String Email = textemail.getText().toString();
        String Village = textvillage.getText().toString();
        String Nid = textid.getText().toString();
        String Phone = textphone.getText().toString();
        String Password = textpassword.getText().toString();
        String Confirm = textconfirm.getText().toString();
        if(TextUtils.isEmpty(Firstname)){
            textfirstame.setError("Enter firstname");
            textfirstame.requestFocus();
        }
        if(TextUtils.isEmpty(Lastname)){
            textlastname.setError("Enter Lastname");
            textlastname.requestFocus();
            return;

        }
        if(TextUtils.isEmpty(Email)){
            textemail.setError("Enter email address");
            textemail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(Village)){
            textvillage.setError("Enter village name");
            textvillage.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(Nid)){
            textid.setError("Enter the national id");
            textid.requestFocus();
            return;
        }
        if(Nid.length() < 8){
            textid.setError("Id cannot be less than 8 digits");
            textid.requestFocus();
            return;

        }
        if(Nid.length() > 8){
            textid.setError("Id cannot be more than 8 characters");
            textid.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(Phone)){
            textphone.setError("Enter phone number");
            textphone.requestFocus();
            return;
        }
        if(Phone.length() < 10){
            textphone.setError("phone number cannot be less than 10 values");
            textphone.requestFocus();
            return;
        }
        if(Phone.length() > 10){
            textphone.setError("phone number cannot be more than 10 values");
            textphone.requestFocus();
            return;
        }

        if(Password.length()<6){
            textpassword.setError("password must be atleast 6 characters");
            textpassword.requestFocus();
            return;
        }
        if(!Password.matches(Confirm)){
            textconfirm.setError("passwords do not match try again!");
            textpassword.requestFocus();
            return;
        }
        auth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Insert();
                           Toast.makeText(getApplicationContext(), "User created!", Toast.LENGTH_LONG).show();


                       }
                       else{
                           Toast.makeText(getApplicationContext(),"Not Succesdful", Toast.LENGTH_LONG).show();

                       }
                    }
                });

    }
    public void Insert(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Firstname = textfirstame.getText().toString();
                String Lastname = textlastname.getText().toString();
                String Email = textemail.getText().toString();
                String Village = textvillage.getText().toString();
                String Nid = textid.getText().toString();
                String Phone = textphone.getText().toString();
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("firstname",Firstname);
                hashMap.put("lastname",Lastname);
                hashMap.put("email",Email);
                hashMap.put("village",Village);
                hashMap.put("id",Nid);
                hashMap.put("phone",Phone);
                String key = childRef.getKey();
                reference.child(key).setValue(hashMap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}



