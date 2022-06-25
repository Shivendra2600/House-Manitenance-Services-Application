package com.example.sdpsem4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class jobList_SP extends AppCompatActivity {

    private Button carp,plum,hk,bath,elect,vc;
    String name1,pin1,phone1,job1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list_sp);

        carp = findViewById(R.id.buttonjlsp1);
        plum = findViewById(R.id.buttonjlsp2);
        hk = findViewById(R.id.buttonjlsp3);
        bath = findViewById(R.id.buttonjlsp4);
        elect = findViewById(R.id.buttonjlsp5);
        vc = findViewById(R.id.buttonjlsp6);

        Intent i = getIntent();
        name1 = i.getStringExtra("NAME");
        pin1 = i.getStringExtra("PIN");
        phone1 = i.getStringExtra("PHONE");

        ///////////////////////////////////////////////////////////////////////////////////////////
        carp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                job1 = "carp";
                Intent i = new Intent(jobList_SP.this,Job_Available.class);
                i.putExtra("NAME",name1);
                i.putExtra("PIN",pin1);
                i.putExtra("PHONE",phone1);
                i.putExtra("JOB",job1);
                startActivity(i);
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////

        plum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                job1 = "plum";
                Intent i = new Intent(jobList_SP.this,Job_Available.class);
                i.putExtra("NAME",name1);
                i.putExtra("PIN",pin1);
                i.putExtra("PHONE",phone1);
                i.putExtra("JOB",job1);
                startActivity(i);
            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////

        hk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                job1 = "hk";
                Intent i = new Intent(jobList_SP.this,Job_Available.class);
                i.putExtra("NAME",name1);
                i.putExtra("PIN",pin1);
                i.putExtra("PHONE",phone1);
                i.putExtra("JOB",job1);
                startActivity(i);
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////////

        bath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                job1 = "bath";
                Intent i = new Intent(jobList_SP.this,Job_Available.class);
                i.putExtra("NAME",name1);
                i.putExtra("PIN",pin1);
                i.putExtra("PHONE",phone1);
                i.putExtra("JOB",job1);
                startActivity(i);
            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////

        elect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                job1 = "elect";
                Intent i = new Intent(jobList_SP.this,Job_Available.class);
                i.putExtra("NAME",name1);
                i.putExtra("PIN",pin1);
                i.putExtra("PHONE",phone1);
                i.putExtra("JOB",job1);
                startActivity(i);
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////

        vc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                job1 = "vc";
                Intent i = new Intent(jobList_SP.this,Job_Available.class);
                i.putExtra("NAME",name1);
                i.putExtra("PIN",pin1);
                i.putExtra("PHONE",phone1);
                i.putExtra("JOB",job1);
                startActivity(i);
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////


    }
}