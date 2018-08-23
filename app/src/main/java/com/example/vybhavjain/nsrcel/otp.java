package com.example.vybhavjain.nsrcel;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.*;

public class otp extends AppCompatActivity {

    EditText otpnumber;
    EditText phonenumber;
    Button generate_otp;
    Button verify_otp;
    String password, username, type, name_count , password_real;
    int password_real1;
    FirebaseAuth auth;
    private String verificationCode;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    final String id = "1L-8iuRCWLaHkwsAbTOLuni3sfJFpXe51DYeOSUSA5Cw";
    String name, emailID, reference;
    private RequestQueue requestQueue;
    private StringRequest request;
    int password_index;
    JSONObject jsonObject;
    String phonenumber_real = "";
    int count=0;
    String[] tokenarray;
    String inmateindex;
    int checker=0;
    String formattedDate;
    private static final String URL_guest = "https://script.google.com/macros/s/AKfycbxv-7ZjjQ9PYNDvXRn0Z-RZ8doJNYzOS0D26YS0caxmtdtM2fUR/exec";
    private static final String URL_inmate = "https://script.google.com/macros/s/AKfycbysQjfTssbAb7rBb7nvebEos4Y0ijLSTZF3HCSY9GV7zrIAEwE/exec";
    private static final String token_url = "https://script.google.com/macros/s/AKfycbyXttNyrNjD1emRZA8jFK8s6i_V-Fs7dlOBHjdWrixZZ54AdCfd/exec";
    private static final String token_delete = "https://script.google.com/macros/s/AKfycbxcR6q0-HCjitqoVzrzavcbS1tLJoHbzkBc6OcpyNbO4ZWo8dGK/exec";
    private static final String token_url_inmate = "https://script.google.com/macros/s/AKfycbzc2chHOPYzRtGE81CLq21pcOBdymR3F6qdXsB3Xpy5yZDQyt0/exec";
    private static final String token_delete_inmate = "https://script.google.com/macros/s/AKfycbwRZV5eZuwfl-3C7AJl5iTjxH1NeEFwkw_LoTVxE5D4298MiI0/exec";
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
        username = getIntent().getStringExtra("Username");
        type = getIntent().getStringExtra("type");
        Log.e(type, "onCreate:wassup " );
        inmateindex = getIntent().getStringExtra("inmateindex");
        Log.e(inmateindex, "onCreate:inmateindex ");
        reference = getIntent().getStringExtra("reference");
        Log.e(reference, "onCreate: reference" );
        emailID = getIntent().getStringExtra("email");
        requestQueue = Volley.newRequestQueue(this);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);


        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                //   Toast.makeText(otp.this, "verification completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(otp.this, "verification failed, please add +91 or a valid number", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                // super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(otp.this, "Code sent", Toast.LENGTH_SHORT).show();
            }

        };
        if( type.equals("g")) {
            request = new StringRequest(Request.Method.GET, token_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        String flag = response;
                        Log.e(flag, "onResponse: tokens");
                        try {
                            jsonObject = new JSONObject(response);
                            Log.e(String.valueOf(jsonObject), "onResponse:");


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    JSONArray obj = null;
                    try {
                        obj = (JSONArray) (jsonObject.get("user"));
                        tokenarray = new String[obj.length()];
                        if (tokenarray.length < 3) {
                            Toast.makeText(otp.this, "Please contact help desk for token .", Toast.LENGTH_LONG).show();
                        }
                            else {
                            Log.e(String.valueOf(tokenarray.length), "onResponse: lollllllllllll ");
                            for (int j = 0; j < obj.length(); j++) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = (JSONObject) (obj.get(j));
                                    String token = jsonObject.optString("token");
                                    tokenarray[j] = token;
                                    Log.e(tokenarray[j], "onResponse: name");


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                          catch(JSONException e){
                            e.printStackTrace();
                        }
                        Random rand = new Random();
                        password_real1 = (rand.nextInt(tokenarray.length) + 0);
                        password_real = tokenarray[password_real1];
                        password_index = password_real1 + 1;
                    }




            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();


                    return hashMap;

                }
            };


            requestQueue.add(request);

        }
        else if( type.equals("i")) {
            request = new StringRequest(Request.Method.GET, token_url_inmate, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        String flag = response;
                        Log.e(flag, "onResponse: tokens");
                        try {
                            jsonObject = new JSONObject(response);
                            Log.e(String.valueOf(jsonObject), "onResponse:");


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    JSONArray obj = null;
                    try {
                        obj = (JSONArray) (jsonObject.get("user"));
                        tokenarray = new String[obj.length()];
                        if (tokenarray.length < 2) {
                            Toast.makeText(otp.this, "Please contact help desk for token .", Toast.LENGTH_LONG).show();
                        }
                        else {
                            for (int j = 0; j < obj.length(); j++) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = (JSONObject) (obj.get(j));
                                    String token = jsonObject.optString("token");
                                    tokenarray[j] = token;
                                    Log.e(tokenarray[j], "onResponse: name");


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Random rand = new Random();
                    password_real1 = (rand.nextInt(tokenarray.length) + 0);
                    password_real = tokenarray[password_real1];
                    password_index = password_real1 + 1;

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();


                    return hashMap;

                }
            };


            requestQueue.add(request);

        }


    }


    private void SigninWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(otp.this, Ticket.class);
                            intent.putExtra("Password", password_real);
                            intent.putExtra("Username", username);
                            intent.putExtra("type", type);
                            startActivity(intent);
                            if(type.equals("g")) {
                                if (emailID.length() != 0 && username.length() != 0 && reference.length() != 0) {
                                    request = new StringRequest(Request.Method.POST, URL_guest, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                String flag = response;


                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                        }


                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            HashMap<String, String> hashMap = new HashMap<String, String>();
                                            hashMap.put("name", username);
                                            hashMap.put("phonenumber", phonenumber_real);
                                            hashMap.put("id", id);
                                            hashMap.put("email", emailID);  // added email
                                            hashMap.put("reference", reference);
                                            hashMap.put("token", password_real); // added refernce
                                            hashMap.put("date", formattedDate);

                                            return hashMap;

                                        }
                                    };


                                    requestQueue.add(request);

                                }
                            }
                            else if (type.equals("i"))
                            {
                                Log.e(type, "verify_otp:hi " );
                                Log.e(password_real, "verify_otp: password real");
                                Log.e(inmateindex, "verify_otp: inmate index ");
                                if (username.length() != 0) {
                                    request = new StringRequest(Request.Method.POST, URL_inmate, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                String flag = response;


                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                        }


                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            HashMap<String, String> hashMap = new HashMap<String, String>();
                                            hashMap.put("index", inmateindex); // val in database
                                            hashMap.put("token",password_real); // added refernce
                                            hashMap.put("date", formattedDate);

                                            return hashMap;

                                        }
                                    };


                                    requestQueue.add(request);

                                }
                            }
                            if( type.equals("g")) {
                                request = new StringRequest(Request.Method.POST, token_delete, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {
                                            String flag = response;
                                            Log.e(flag, "onResponse: tokens");


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }


                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("token_index", String.valueOf(password_index));


                                        return hashMap;

                                    }
                                };


                                requestQueue.add(request);
                            }
                            else if( type.equals("i"))
                            {
                                request = new StringRequest(Request.Method.POST, token_delete_inmate, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {
                                            String flag = response;
                                            Log.e(flag, "onResponse: tokens");


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }


                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("token_index", String.valueOf(password_index));


                                        return hashMap;

                                    }
                                };


                                requestQueue.add(request);
                            }
                            finish();
                        } else {

                            count++;
                            if(count<2)
                             Toast.makeText(otp.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                            else if(count<3)
                                Toast.makeText(otp.this, "Incorrect OTP,After the next Incorrect OTP, you will be taken to the guest login page", Toast.LENGTH_LONG).show();
                            else
                                System.exit(0);
                            }

                            }

                });
    }

    public void generate_otp(View v) {
        phonenumber_real = phonenumber.getText().toString();
        if (phonenumber_real.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please fill Phone number first", Toast.LENGTH_SHORT).show();
            Log.e(phonenumber_real, "onClick: mobile number");
        } else {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phonenumber_real,                     // Phone number to verify
                    60,                           // Timeout duration
                    TimeUnit.SECONDS,                // Unit of timeout
                    otp.this,        // Activity (for callback binding)
                    mCallback);                      // OnVerificationStateChangedCallbacks
        }
    }

    public void verify_otp(View v) {
        phonenumber_real = phonenumber.getText().toString();
        if( phonenumber_real.length() == 0 )
        {
            Toast.makeText(getApplicationContext(),"Please fill Phone number first",Toast.LENGTH_SHORT).show();
        }
        else {
            String otpnumber_real = otpnumber.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otpnumber_real);
            SigninWithPhone(credential);
        }

    }
}


