package com.example.msafi.techgaa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginPage extends AppCompatActivity {
    Toolbar toolbar;
    private EditText email, password;
    Button submit;
    private static final String TAG = LoginPage.class.getSimpleName();
    private FirebaseDatabase database;
    private DatabaseReference dbRef, childRef, FarmerRef, CollectorRef;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener authStateListener;
ArrayList<String> emailist = new ArrayList<>();
ArrayList<String> RockyList = new ArrayList<>();
ArrayList<String> AdminList = new ArrayList<>();
   private ProgressDialog loadinBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.login_password);
        submit = findViewById(R.id.login_btn);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("Admin");

        childRef = dbRef.push();
        user = auth.getCurrentUser();
        loadinBar = new ProgressDialog(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == submit) {
                    loginUser();
                }
            }
        });



    }

    public void loginUser() {
        final String Mail = email.getText().toString().trim();
        final String Pass = password.getText().toString();

        if (TextUtils.isEmpty(Mail)) {
            email.setError("Enter your email Address");
            email.requestFocus();
            return;
        }
        if (Pass.length() < 6) {
            password.setError("Enter password");
            password.requestFocus();
            return;
        }
        else{
            loadinBar.setTitle("Signing in");
            loadinBar.setMessage("Please wait while credentials are verified");
            loadinBar.show();
            loadinBar.setCanceledOnTouchOutside(true);
        }
        FarmerRef = database.getReference("Huncho");
        FarmerRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String Asap = (String)dataSnapshot.child("email").getValue();
                emailist.add(Asap);
                if(emailist.contains(Mail)){
                   auth.signInWithEmailAndPassword(Mail, Pass)
                           .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           startActivity(new Intent(LoginPage.this, Farmer.class));
                       }

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                           loadinBar.hide();

                       }
                   });


                }
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
        });
        CollectorRef = database.getReference("Takeoff");
        CollectorRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String Rocky = (String) dataSnapshot.child("email").getValue();
                RockyList.add(Rocky);
                if(RockyList.contains(Mail)){
                    auth.signInWithEmailAndPassword(Mail,Pass).addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(LoginPage.this, User_Collector.class));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            loadinBar.hide();


                        }
                    });
                }

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
        });
dbRef.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        String Cardi = (String) dataSnapshot.child("email").getValue();
        AdminList.add(Cardi);
        if(AdminList.contains(Mail)){
            auth.signInWithEmailAndPassword(Mail,Pass).addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(LoginPage.this, AdminActivity.class));
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    loadinBar.hide();

                }
            });
        }
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
});
 /*auth.signInWithEmailAndPassword(Mail, Pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
     @Override
     public void onComplete(@NonNull Task<AuthResult> task) {
         if(task.isSuccessful()){
             startActivity(new Intent(LoginPage.this, AdminActivity.class));
         }else{
             Log.w(TAG, "Failed", task.getException());
             String exception = task.getException().toString();
             Toast.makeText(getApplicationContext(), "task failed beacuse of" + exception, Toast.LENGTH_LONG).show();
         }

     }
 });
*/
    }
}
