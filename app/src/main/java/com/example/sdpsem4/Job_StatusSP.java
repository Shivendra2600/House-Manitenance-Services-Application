package com.example.sdpsem4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Job_StatusSP extends AppCompatActivity {

    String name1,pin1,phone1;
    private TextView name,phone,address,price;
    private Button job_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_status_sp);

        Intent i = getIntent();
        name1 = i.getStringExtra("NAME");
        pin1 = i.getStringExtra("PIN");
        phone1 = i.getStringExtra("PHONE");

        name = findViewById(R.id.tvjssp1);
        phone = findViewById(R.id.tvjssp2);
        address = findViewById(R.id.tvjssp3);
        price = findViewById(R.id.tvjssp4);

        job_done = findViewById(R.id.buttonjssp1);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Users = database.getReference("COrder").child(phone1);
        Users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ph = snapshot.child("Phone").getValue(String.class);
                phone.setText(ph);

                DatabaseReference abc = database.getReference("Listed Jobs").child(pin1).child(ph);
                abc.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String add = snapshot.child("Updated Address").getValue(String.class);
                        String pho = snapshot.child("Updated Phone").getValue(String.class);
                        phone.setText(pho);
                        address.setText(add);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference xyz = database.getReference("Bid Details").child(ph).child(phone1);
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

                DatabaseReference kj = database.getReference("users").child(ph);
                kj.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String nm = snapshot.child("Name").getValue(String.class);
                        name.setText(nm);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference uv = database.getReference("users").child(ph);
                uv.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String Email = snapshot.child("Email").getValue(String.class);

                        job_done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + Email));
                                i.putExtra(i.EXTRA_SUBJECT,"Job Completion");
                                i.putExtra(i.EXTRA_TEXT,"Your Job Has Been Completed!!!\nHave a Good Day");
                                startActivity(i);

                                database.getReference("Bid Details").child(ph).removeValue();
                                database.getReference("Bids").child(ph).removeValue();
                                database.getReference("COrder").child(phone1).removeValue();
                                database.getReference("Confirmed Orders").child(ph).removeValue();

                                DatabaseReference ln = database.getReference("Service Providers").child(phone1);
                                ln.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String pin = snapshot.child("Pin").getValue(String.class);
                                        database.getReference("Listed Jobs").child(pin).child(ph).removeValue();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        });

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