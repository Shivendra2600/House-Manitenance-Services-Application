package com.example.sdpsem4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.appwidget.AppWidgetHost;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.PrimitiveIterator;

public class Job_DecSP extends AppCompatActivity {

    private TextView pone,address,jobd;
    private EditText price;
    private Button book;

    String name1,pin1,phone1,job1,trm,rupees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_dec_sp);

        pone = findViewById(R.id.tvjdsp1);
        address = findViewById(R.id.tvjdsp2);
        jobd = findViewById(R.id.tvjdsp3);

        price = findViewById(R.id.etjbsp1);

        book = findViewById(R.id.buttonjdsp);

        Intent i = getIntent();
        name1 = i.getStringExtra("NAME");
        pin1 = i.getStringExtra("PIN");
        phone1 = i.getStringExtra("PHONE");
        job1 = i.getStringExtra("JOB");
        trm = i.getStringExtra("TRM");


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Users = database.getReference("Listed Jobs").child(pin1).child(trm);
        Users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String phone = snapshot.child("Updated Phone").getValue(String.class);
                String addr = snapshot.child("Updated Address").getValue(String.class);
                String job_decreption = snapshot.child("Job Description").getValue(String.class);
                pone.setText(phone);
                address.setText(addr);
                jobd.setText(job_decreption);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rupees = price.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference Users = database.getReference("Bids").child(trm);
                DatabaseReference abc = database.getReference("Bid Details").child(trm).child(phone1);
                Users.child(phone1).setValue("("+phone1+")"+" "+name1);
                abc.child("Money").setValue(rupees);
                Toast.makeText(getApplicationContext(),"BID PLACED SUCCESSFULLY",Toast.LENGTH_LONG).show();
                
            }
        });


    }
}