package com.example.vybhavjain.nsrcel;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

 public class Ticket extends AppCompatActivity {

     TextView uname, upassword, countdown;
     CountDownTimer countDownTimer;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.ticketpage);
         String username = getIntent().getStringExtra("Username");
         String password = getIntent().getStringExtra("Password");
         String type_user = getIntent().getStringExtra("type");
         Log.e(type_user, "onCreate: user_type");
         uname = (TextView) findViewById(R.id.username);
         upassword = (TextView) findViewById(R.id.password);
         //   countdown = (TextView) findViewById(R.id.countdown);
         //   countdown = (TextView) findViewById(R.id.countdown);
         uname.setText("Username: " + username);
         upassword.setText("Password: " + password);


     }
 }






