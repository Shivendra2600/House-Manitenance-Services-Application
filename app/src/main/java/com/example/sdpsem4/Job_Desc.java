package com.example.sdpsem4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Job_Desc extends AppCompatActivity {

    String name1,pin1,phone1,address1,job1,updated_phone,updated_address,Job_Dec;
    EditText et2,et3,et4;
    TextView tv1;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_desc);

        Intent i = getIntent();
        name1 = i.getStringExtra("NAME");
        pin1 = i.getStringExtra("PIN");
        phone1 = i.getStringExtra("PHONE");
        address1 = i.getStringExtra("ADDRESS");
        job1 = i.getStringExtra("JOB");

        tv1 = findViewById(R.id.tvjob_dec1);
        et2 = findViewById(R.id.etjob_dec2);
        et3 = findViewById(R.id.etjob_dec3);
        et4 = findViewById(R.id.etjob_dec4);

        next = findViewById(R.id.buttonjob_dec1);

        tv1.setText(name1);
        et2.setText(phone1);
        et3.setText(address1);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updated_phone = et2.getText().toString();
                updated_address = et3.getText().toString();
                Job_Dec = et4.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference Users = database.getReference("Listed Jobs").child(pin1).child(phone1);
                DatabaseReference abc = database.getReference("Job Names").child(pin1).child(job1);
                Users.child("Updated Phone").setValue(updated_phone);
                Users.child("Updated Address").setValue(updated_address);
                Users.child("Job Description").setValue(Job_Dec);
                abc.child(phone1).setValue("("+phone1+")"+" "+Job_Dec);

                Toast.makeText(getApplicationContext(),"JOB POSTED SUCCESSFULLY",Toast.LENGTH_LONG).show();


            }
        });

    }
}