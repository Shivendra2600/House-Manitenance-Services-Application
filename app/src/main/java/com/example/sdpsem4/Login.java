package com.example.sdpsem4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.jar.Attributes;

public class Login extends AppCompatActivity {
    private EditText email , password , phone;
    private Button login,vol;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView signup = (TextView)findViewById(R.id.tvlogin1);
        TextView forg = (TextView)findViewById(R.id.tvlogin2);
        phone = findViewById(R.id.etlogin3);
        Switch show = (Switch)findViewById(R.id.switchlogin1);
        Switch change = (Switch)findViewById(R.id.switchlogin2);



        email = findViewById(R.id.etlogin1);
        password = findViewById(R.id.etlogin2);
        login = findViewById(R.id.buttonlogin1);

        forg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Eemail = email.getText().toString();
                builder.setMessage("Reset Password ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (TextUtils.isEmpty(Eemail)) {
                                    Toast.makeText(getApplicationContext(), "Enter your email!", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    firebaseAuth.sendPasswordResetEmail(Eemail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "Check Email!", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                String msg = task.getException().getMessage();
                                                Toast.makeText(getApplicationContext(), "Error : " + msg, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("FORGOT PASSWORD");
                alert.show();

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Signup.class);
                startActivity(i);
            }
        });

        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        change.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loogin();
                        }
                    });
                } else {
                    firebaseAuth = FirebaseAuth.getInstance();
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Loogin();
                        }
                    });
                }
            }
        });
        builder = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);


    }

    private void Loogin() {
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String Phone = phone.getText().toString();
        if (TextUtils.isEmpty(Email)){
            email.setError("Enter Your Email");
            return;
        }
        else if (TextUtils.isEmpty(Password)){
            password.setError("Enter Your Password");
            return;
        }

        else if (Password.length()<4){
            password.setError("Low Password Length");
            return;
        }
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(Email , Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Loged In",Toast.LENGTH_LONG).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference Users = database.getReference("users").child(Phone);
                    Users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String namedb = snapshot.child("Name").getValue(String.class);
                            String pindb = snapshot.child("Pin").getValue(String.class);
                            String phone1 = snapshot.child("Phone").getValue(String.class);
                            String address1 = snapshot.child("Address").getValue(String.class);
                            Intent i = new Intent(Login.this,first.class);
                            i.putExtra("NAME",namedb);
                            i.putExtra("PIN",pindb);
                            i.putExtra("ADDRESS",address1);
                            i.putExtra("PHONE",phone1);
                            startActivity(i);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"Failed To Register",Toast.LENGTH_LONG).show();
                    builder.setMessage("Do You Want To Signup?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Login.this,Signup.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Wrong Details");
                    alert.show();
                }
                progressDialog.dismiss();
            }
        });

    }

    private void loogin() {
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String Phone = phone.getText().toString();
        if (TextUtils.isEmpty(Email)){
            email.setError("Enter Your Email");
            return;
        }
        else if (TextUtils.isEmpty(Password)){
            password.setError("Enter Your Password");
            return;
        }

        else if (Password.length()<4){
            password.setError("Low Password Length");
            return;
        }
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(Email , Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Loged In",Toast.LENGTH_LONG).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference Users = database.getReference("Service Providers").child(Phone);
                    Users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String namedb = snapshot.child("Name").getValue(String.class);
                            String pindb = snapshot.child("Pin").getValue(String.class);
                            String phonedb = snapshot.child("Phone").getValue(String.class);
                            Intent i = new Intent(Login.this,SP_first.class);
                            i.putExtra("NAME",namedb);
                            i.putExtra("PIN",pindb);
                            i.putExtra("PHONE",phonedb);
                            startActivity(i);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"Failed To Register",Toast.LENGTH_LONG).show();
                    builder.setMessage("Do You Want To Signup?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Login.this,Signup.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Wrong Details");
                    alert.show();
                }
                progressDialog.dismiss();
            }
        });

    }
}