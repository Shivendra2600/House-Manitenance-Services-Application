package com.example.sdpsem4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class worker_details extends AppCompatActivity {

    String name1,pin1,phone1,address1,trm;
    private TextView name,phone,workdec,price;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_details);

        name = findViewById(R.id.tvwd1);
        phone = findViewById(R.id.tvwd2);
        workdec = findViewById(R.id.tvwd3);
        price = findViewById(R.id.tvwd4);

        next = findViewById(R.id.buttonwd1);

        Intent i = getIntent();
        name1 = i.getStringExtra("NAME");
        pin1 = i.getStringExtra("PIN");
        phone1 = i.getStringExtra("PHONE");
        address1 = i.getStringExtra("ADDRESS");
        trm = i.getStringExtra("TRM");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Users = database.getReference("Service Providers").child(trm);
        Users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nm = snapshot.child("Name").getValue(String.class);
                String ph = snapshot.child("Phone").getValue(String.class);
                String wd = snapshot.child("Work Description").getValue(String.class);
                name.setText(nm);
                phone.setText(ph);
                workdec.setText(wd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference abc = database.getReference("Bid Details").child(phone1).child(trm);
        abc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String pr = snapshot.child("Money").getValue(String.class);
                price.setText(pr+" Rs");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference Users = database.getReference("Confirmed Orders").child(phone1);
                DatabaseReference abc = database.getReference("COrder").child(trm);
                Users.child("Phone").setValue(trm);
                abc.child("Phone").setValue(phone1);
                Toast.makeText(getApplicationContext(),"JOB CONFIRMED",Toast.LENGTH_LONG).show();

                DatabaseReference ccc = database.getReference("Service Providers").child(trm);
                ccc.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String Email = snapshot.child("Email").getValue(String.class);

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + Email));
                        intent.putExtra(intent.EXTRA_SUBJECT,"Job Allotment");
                        intent.putExtra(intent.EXTRA_TEXT,"Your Bid has been accepted\nHave a Good Day");
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent i = new Intent(worker_details.this,Payment.class);
                startActivity(i);

            }
        });



    }
}