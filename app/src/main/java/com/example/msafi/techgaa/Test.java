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

import org.w3c.dom.Text;

public class Test extends AppCompatActivity {
private FirebaseDatabase database;
private FirebaseAuth auth;
private DatabaseReference reference, childRef;
private EditText textemail, textpassword, textfirstame, textlastname, textvillage, textconfirm, textphone, textid;
private Collector_User user;
private Button reg;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
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
        textpassword = findViewById(R.id.test_pass);
        reg = findViewById(R.id.test_reg);
        textfirstame  = findViewById(R.id.test_firstname);
        textlastname = findViewById(R.id.test_lastname);
        textvillage = findViewById(R.id.test_village);
        textphone = findViewById(R.id.test_phone);
        textid = findViewById(R.id.test_id);
        textconfirm = findViewById(R.id.test_confirm);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Takeoff");
        childRef = reference.push();
        user = new Collector_User();
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testRegister();
            }
        });
    }
    public void testRegister(){
        String Email = textemail.getText().toString().trim();
        String Password = textpassword.getText().toString().trim();
        String Firstname = textfirstame.getText().toString().trim();
        String Lastname = textlastname.getText().toString().trim();
        String Village = textvillage.getText().toString().trim();
        String Phone = textphone.getText().toString().trim();
        String Confirm = textconfirm.getText().toString().trim();
        String Nid = textid.getText().toString().trim();

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
                    textid.setError("Id cannot be less than 8 characters");
                    textid.requestFocus();
                    return;
        }
        if(Nid.length() > 8){
                    textid.setError("Id cannot be more than 8 characters");
                    textid.requestFocus();
                    return;
        }
        if(Phone.length() < 10){
                    textphone.setError("Phone number cannot be less than 10 characters");
                    textphone.requestFocus();
                    return;
        }
        if(Phone.length() > 10){
                    textphone.setError("Phone number cannot be more than 10 characters");
        }
        if(TextUtils.isEmpty(Phone)){
            textphone.setError("Enter phone number");
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
        getValues();

        auth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Insert();
                            Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_LONG).show();


                        }else{
                            Toast.makeText(getApplicationContext(), "You played yourself", Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }
    public void getValues(){
        user.setFirstname(textfirstame.getText().toString());
        user.setEmail(textemail.getText().toString());
        user.setLastname(textlastname.getText().toString());
        user.setNational_id(textid.getText().toString());
        user.setPhone(textphone.getText().toString());
        user.setVillage(textvillage.getText().toString());
    }
    public void Insert(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getValues();
                String key = childRef.getKey();
                reference.child(key).setValue(user);
                Toast.makeText(getApplicationContext(), "Data Inserted Successfully", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
