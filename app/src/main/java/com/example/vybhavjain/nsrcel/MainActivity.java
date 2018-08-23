package com.example.vybhavjain.nsrcel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    EditText name , phonenumber;
    String[] namesarray , phonenumberarray , tokenarray_inmate;
    Button b;
    String password , type1;
    private static final String URL = "https://script.googleusercontent.com/macros/echo?user_content_key=suSzdJxXBfM90snBivmZZzp7yyuJTu_Ya4qMQlPFcML-pBzHhBRDE2mBs94zfMF0YZTgAp6V9gN2KL3orU8MoFS3W9lvaBJRm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnCSxg59lhMImqfINVDyVfMjQ7I1hBwkBHvqa3IcR2vWqYG6WaOXNcFwu1QPxwNwfZ0WY2nZ7HPw5&lib=Mpmp6VZVIcgylJlJbX0MEHL866zndRzds";
    private static final String URL_guest = "https://script.google.com/macros/s/AKfycbxv-7ZjjQ9PYNDvXRn0Z-RZ8doJNYzOS0D26YS0caxmtdtM2fUR/exec";
    private RequestQueue requestQueue;
    private StringRequest request;
    JSONObject jsonArray;
    int inmate , guest, inmateindex;
    TextView guest12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        name = (EditText) findViewById(R.id.myname);
        phonenumber = (EditText) findViewById(R.id.myphone);
        b = (Button) findViewById(R.id.submit);
        guest12 = (TextView) findViewById(R.id.guest);


        request = new StringRequest(Request.Method.GET, URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    String flag = response;
                    Log.e(flag, "onResponse:dailydarshan ");
                    try {
                        jsonArray = new JSONObject(response);
                        Log.e(String.valueOf(jsonArray), "onResponse:");


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    JSONArray obj = null;
                    try {
                        obj = (JSONArray) (jsonArray.get("user"));
                        namesarray = new String[obj.length()];
                        phonenumberarray = new String[obj.length()];
                        tokenarray_inmate = new String[obj.length()];
                        for (int j = 0; j < obj.length(); j++) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = (JSONObject) (obj.get(j));
                                String name = jsonObject.optString("name");
                                String phonenumberget = jsonObject.optString("phone_number");
                                String tokenget = jsonObject.optString("token_inmate");
                                namesarray[j] = name;
                                phonenumberarray[j] = phonenumberget;
                                tokenarray_inmate[j] = tokenget;
                                Log.e( namesarray[j],"onResponse: name" );
                                Log.e(tokenarray_inmate[j], "onResponse: token" );


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



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


                return hashMap;

            }
        };

        requestQueue.add(request);



        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final String myname = name.getText().toString().trim();
                Log.e( myname,"onClick: name");
                final String myphone = phonenumber.getText().toString().trim();
                int flag = 0;
                final String id = "1L-8iuRCWLaHkwsAbTOLuni3sfJFpXe51DYeOSUSA5Cw";
                if( myname.length() == 0 || myphone.length() == 0 )
                {
                    Toast.makeText(getApplicationContext() , "Please fill all fields" , Toast.LENGTH_SHORT).show();
                }
                else {
                    for (int i = 0; i < namesarray.length; i++) {
                        Log.e(String.valueOf(myname.equals(namesarray[i])), "onClick: comparison");
                        if (namesarray[i].equalsIgnoreCase(myname)) {
                            if (phonenumberarray[i].equals(myphone)) {
                                flag = 1;
                                inmateindex=i+2;
                                type1 = "i";
                                if( tokenarray_inmate[i].length() == 0 ) {
                                    Intent intent = new Intent(MainActivity.this, otp.class);
                                    intent.putExtra("Username", myname);
                                    intent.putExtra("type", type1);
                                    intent.putExtra("inmateindex", String.valueOf(inmateindex));
                                    startActivity(intent);
                                }
                                else
                                {
                                    Intent intent_1 = new Intent(MainActivity.this, Ticket.class);
                                    intent_1.putExtra("Username", myname);
                                    intent_1.putExtra("Password",tokenarray_inmate[i]);
                                    intent_1.putExtra("type", type1);
                                    startActivity(intent_1);
                                }


                            } else
                                Toast.makeText(getApplicationContext(), "Phone number is incorrect", Toast.LENGTH_LONG).show();
                        }

                    }
                }
                if( flag != 1 )
                    Toast.makeText(getApplicationContext() ,"User not registered, please contact the administration." , Toast.LENGTH_LONG).show();



            }
        });


        guest12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,login.class);
                startActivity(intent1);
            }
        });
    }
}
