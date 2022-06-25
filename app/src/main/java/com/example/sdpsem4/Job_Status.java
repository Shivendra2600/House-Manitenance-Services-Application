package com.example.sdpsem4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Job_Status extends AppCompatActivity {

    String name1,pin1,phone1,address1;
    private TextView name,phone,workdec,price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_status);

        name = findViewById(R.id.tvjs1);
        phone = findViewById(R.id.tvjs2);
        workdec = findViewById(R.id.tvjs3);
        price = findViewById(R.id.tvjs4);

        Intent i = getIntent();
        name1 = i.getStringExtra("NAME");
        pin1 = i.getStringExtra("PIN");
        phone1 = i.getStringExtra("PHONE");
        address1 = i.getStringExtra("ADDRESS");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Users = database.getReference("Confirmed Orders").child(phone1);
        Users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ph = snapshot.child("Phone").getValue(String.class);
                phone.setText(ph);

                DatabaseReference abc = database.getReference("Service Providers").child(ph);
                abc.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String nm = snapshot.child("Name").getValue(String.class);
                        String wd = snapshot.child("Work Description").getValue(String.class);
                        name.setText(nm);
                        workdec.setText(wd);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference xyz = database.getReference("Bid Details").child(phone1).child(ph);
                xyz.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String mn = snapshot.child("Money").getValue(String.class);
                        price.setText(mn);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}