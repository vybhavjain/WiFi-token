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
         uname.setText("Username: " + username);
         upassword.setText("Password: " + password);


    /*    if (type_user.equals("i")) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            countDownTimer = new CountDownTimer(259200000, 1000) {

                @Override
                public void onFinish() {
                    countdown.setText("Finished!");
                }

                @Override
                public void onTick(long millisUntilFinished) {
                    long seconds = millisUntilFinished / 1000;
                    long minutes = seconds / 60;
                    long hours = minutes / 60;
                    long days = hours / 24;
                    String time = ("Days : " + days + " " + "Hours : " + hours % 24 + " " + "minutes : " + minutes % 60 + " " + "Seconds : " + seconds % 60 + " ");
                    countdown.setText(time);

                }

            };

            countDownTimer.start();
        } else  {
            String name_count = getIntent().getStringExtra("count");
            Log.e( name_count , "onCreate:" );
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

                countDownTimer = new CountDownTimer(86400000, 1000) {

                    @Override
                    public void onFinish() {
                        countdown.setText("Finished!");
                    }

                    @Override
                    public void onTick(long millisUntilFinished) {
                        long seconds = millisUntilFinished / 1000;
                        long minutes = seconds / 60;
                        long hours = minutes / 60;
                        long days = hours / 24;
                        String time = ("Days : " + days + " " + "Hours : " + hours % 24 + " " + "minutes : " + minutes % 60 + " " + "Seconds : " + seconds % 60 + " ");
                        countdown.setText(time);

                    }

                };

                countDownTimer.start();
            }
        }
        */
     }
 }






