package com.example.sdpsem4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.HashMap;

public class Signup extends AppCompatActivity {
    private EditText email , password , name , confirm , pin , phone,address;

    private ProgressDialog progressDialog;
    private Switch show, change;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TextView log = (TextView)findViewById(R.id.tvsignup2);
        address = findViewById(R.id.etsignup7);
        show = findViewById(R.id.switchsignup1);
        change = findViewById(R.id.switchsignup2);
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.etsignup2);
        name = findViewById(R.id.etsignup1);
        password = findViewById(R.id.etsignup3);
        confirm = findViewById(R.id.etsignup4);
        pin = findViewById(R.id.etsignup5);
        phone = findViewById(R.id.etsignup6);
        progressDialog = new ProgressDialog(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        change.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Button signup = (Button)findViewById(R.id.buttonsignup1);
                    email.setHint("Email Id(Work)");
                    name.setHint("Name");
                    password.setHint("Password");
                    confirm.setHint("Confirm Password");
                    pin.setHint("Pin Code");
                    phone.setHint("Phone Number(Work)");
                    address.setHint("Describe Work Experience");
                    signup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v){
                            register();
                        }
                    });
                }
                else {
                    Button signup2 = (Button)findViewById(R.id.buttonsignup1);
                    email.setHint("Email Id");
                    name.setHint("Name");
                    password.setHint("Password");
                    confirm.setHint("Confirm Password");
                    pin.setHint("Pin Code");
                    phone.setHint("Phone Number");
                    address.setHint("Address");
                    signup2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Register();
                        }
                    });
                }
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Signup.this,Login.class);
                startActivity(j);
                finish();
            }
        });
    }

    private void Register() {
        String Name = name.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String Confirm = confirm.getText().toString();
        String Pin = pin.getText().toString();
        String Phone = phone.getText().toString();
        String Address = address.getText().toString();
        if (TextUtils.isEmpty(Email)){
            email.setError("Enter Your Email");
            return;
        }
        else if (TextUtils.isEmpty(Password)){
            password.setError("Enter Your Password");
            return;
        }
        else if (TextUtils.isEmpty(Confirm)){
            confirm.setError("Confirm Your Password");
            return;
        }
        else if (!Password.equals(Confirm)){
            password.setError("Password Does Not Match");
            return;
        }
        else if (Password.length()<4){
            password.setError("Low Length");
            return;
        }
        else if (!isVallidEmail(Email)){
            email.setError("Enter Valid Email");
            return;
        }
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(Email , Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                String abc = firebaseAuth.getUid();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference Users = database.getReference("users").child(Phone);
                                DatabaseReference pin = database.getReference("User Pins").child(Pin);
                                pin.child(abc).setValue(Phone);
                                Users.child("Email").setValue(Email);
                                Users.child("Name").setValue(Name);
                                Users.child("Pin").setValue(Pin);
                                Users.child("Phone").setValue(Phone);
                                Users.child("Address").setValue(Address);
                                Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Signup.this,Login.class);
                                startActivity(i);
                                finish();

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Failed To Register",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                progressDialog.dismiss();
            }
        });
    }
    private void register() {
        String Name = name.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String Confirm = confirm.getText().toString();
        String Pin = pin.getText().toString();
        String Phone = phone.getText().toString();
        String Address = address.getText().toString();
        if (TextUtils.isEmpty(Email)){
            email.setError("Enter Your Email");
            return;
        }
        else if (TextUtils.isEmpty(Password)){
            password.setError("Enter Your Password");
            return;
        }
        else if (TextUtils.isEmpty(Confirm)){
            confirm.setError("Confirm Your Password");
            return;
        }
        else if (!Password.equals(Confirm)){
            password.setError("Password Does Not Match");
            return;
        }
        else if (Password.length()<4){
            password.setError("Low Length");
            return;
        }
        else if (!isVallidEmail(Email)){
            email.setError("Enter Valid Email");
            return;
        }
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(Email , Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                String abc = firebaseAuth.getUid();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference Users = database.getReference("Service Providers").child(Phone);
                                DatabaseReference Bbpin = database.getReference("Service Providers Pins").child(Pin);
                                Bbpin.child(abc).setValue("("+Phone+")"+" "+Name);
                                Users.child("Email").setValue(Email);
                                Users.child("Name").setValue(Name);
                                Users.child("Pin").setValue(Pin);
                                Users.child("Phone").setValue(Phone);
                                Users.child("Work Description").setValue(Address);
                                Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Signup.this,Login.class);
                                startActivity(i);
                                finish();

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Failed To Register",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                progressDialog.dismiss();
            }
        });
    }
    private Boolean isVallidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}