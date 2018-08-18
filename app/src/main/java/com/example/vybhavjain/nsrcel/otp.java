package com.example.vybhavjain.nsrcel;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otp extends AppCompatActivity {

    EditText otpnumber;
    EditText phonenumber;
    Button generate_otp;
    Button verify_otp;
    String password, username, type, name_count;
    FirebaseAuth auth;
    private String verificationCode;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);
        otpnumber = (EditText) findViewById(R.id.otp);
        phonenumber = (EditText) findViewById(R.id.number);
        final String phonenumber_real = phonenumber.getText().toString();
        Log.e(phonenumber_real, "onCreate: mobile number");
        generate_otp = (Button) findViewById(R.id.generate_otp);
        verify_otp = (Button) findViewById(R.id.verify_otp);
        password = getIntent().getStringExtra("Password");
        username = getIntent().getStringExtra("Username");
        type = getIntent().getStringExtra("type");
        name_count = getIntent().getStringExtra("count");


        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                //   Toast.makeText(otp.this, "verification completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(otp.this, "verification failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                // super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(otp.this, "Code sent", Toast.LENGTH_SHORT).show();
            }

        };



    }

    private void SigninWithPhone(PhoneAuthCredential credential){
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(otp.this, Ticket.class);
                            intent.putExtra("Password", password);
                            intent.putExtra("Username", username);
                            intent.putExtra("type", type);
                            intent.putExtra("count", name_count);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(otp.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void generate_otp(View v)
    {
        final String phonenumber_real = phonenumber.getText().toString();
        Log.e(phonenumber_real, "onClick: mobile number");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber_real,                     // Phone number to verify
                60,                           // Timeout duration
                TimeUnit.SECONDS,                // Unit of timeout
                otp.this,        // Activity (for callback binding)
                mCallback);                      // OnVerificationStateChangedCallbacks
    }

    public void verify_otp(View v) {
        String otpnumber_real = otpnumber.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otpnumber_real);
        SigninWithPhone(credential);
    }
}
