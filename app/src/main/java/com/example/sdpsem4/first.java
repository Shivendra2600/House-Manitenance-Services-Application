package com.example.sdpsem4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class first extends AppCompatActivity {
    private Button post,bids,status;
    private TextView hello;
    String name1,pin1,phone1,address1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        post = findViewById(R.id.buttonfirst1);
        bids = findViewById(R.id.buttonfirst2);
        hello = findViewById(R.id.tvfirst1);

        status=findViewById(R.id.buttonfirst3);

        Intent i = getIntent();
        name1 = i.getStringExtra("NAME");
        pin1 = i.getStringExtra("PIN");
        phone1 = i.getStringExtra("PHONE");
        address1 = i.getStringExtra("ADDRESS");

        hello.setText("Hello "+name1);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(first.this,JobList.class);
                i.putExtra("NAME",name1);
                i.putExtra("PIN",pin1);
                i.putExtra("ADDRESS",address1);
                i.putExtra("PHONE",phone1);
                startActivity(i);

            }
        });

        bids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(first.this,Job_Bids.class);
                i.putExtra("NAME",name1);
                i.putExtra("PIN",pin1);
                i.putExtra("ADDRESS",address1);
                i.putExtra("PHONE",phone1);
                startActivity(i);

            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(first.this,Job_Status.class);
                i.putExtra("NAME",name1);
                i.putExtra("PIN",pin1);
                i.putExtra("ADDRESS",address1);
                i.putExtra("PHONE",phone1);
                startActivity(i);

            }
        });

    }
}