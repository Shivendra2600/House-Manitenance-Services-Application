package com.example.sdpsem4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JobList extends AppCompatActivity {

    private Button carp,plum,hk,bath,elect,vc;
    String name1,pin1,phone1,address1,job1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        carp = findViewById(R.id.button_JobList1);
        plum = findViewById(R.id.button_JobList2);
        hk = findViewById(R.id.button_JobList3);
        bath = findViewById(R.id.button_JobList4);
        elect = findViewById(R.id.button_JobList5);
        vc = findViewById(R.id.button_JobList6);

        Intent i = getIntent();
        name1 = i.getStringExtra("NAME");
        pin1 = i.getStringExtra("PIN");
        phone1 = i.getStringExtra("PHONE");
        address1 = i.getStringExtra("ADDRESS");

        ///////////////////////////////////////////////////////////////////////////////////////////
        carp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                job1 = "carp";
                Intent i = new Intent(JobList.this,Job_Desc.class);
                i.putExtra("NAME",name1);
                i.putExtra("PIN",pin1);
                i.putExtra("ADDRESS",address1);
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
                Intent i = new Intent(JobList.this,Job_Desc.class);
                i.putExtra("NAME",name1);
                i.putExtra("PIN",pin1);
                i.putExtra("ADDRESS",address1);
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
                Intent i = new Intent(JobList.this,Job_Desc.class);
                i.putExtra("NAME",name1);
                i.putExtra("PIN",pin1);
                i.putExtra("ADDRESS",address1);
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
                Intent i = new Intent(JobList.this,Job_Desc.class);
                i.putExtra("NAME",name1);
                i.putExtra("PIN",pin1);
                i.putExtra("ADDRESS",address1);
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
                Intent i = new Intent(JobList.this,Job_Desc.class);
                i.putExtra("NAME",name1);
                i.putExtra("PIN",pin1);
                i.putExtra("ADDRESS",address1);
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
                Intent i = new Intent(JobList.this,Job_Desc.class);
                i.putExtra("NAME",name1);
                i.putExtra("PIN",pin1);
                i.putExtra("ADDRESS",address1);
                i.putExtra("PHONE",phone1);
                i.putExtra("JOB",job1);
                startActivity(i);
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////

    }
}