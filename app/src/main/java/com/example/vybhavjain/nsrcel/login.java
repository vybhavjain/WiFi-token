package com.example.vybhavjain.nsrcel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class login extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    private static final String URL_guest = "https://script.google.com/macros/s/AKfycbxv-7ZjjQ9PYNDvXRn0Z-RZ8doJNYzOS0D26YS0caxmtdtM2fUR/exec";
    private static final String URL_check = "https://script.google.com/macros/s/AKfycbxvD8zvWeIB9ZnoOGvkAA0eBRksFxBJENlHJviRvyP5FxzV2fc/exec";
    private RequestQueue requestQueue;
    private StringRequest request;
    String password , type1;
    int guest;
    EditText name , phonenumber , email , reference;   // added email, refered person
    Button b;
    int flag1 = 0;
    JSONObject jsonArray;
    String[] emailarray , tokenarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        name = (EditText) findViewById(R.id.myname);
        email = (EditText) findViewById(R.id.email);  // edit text needed
        reference = (EditText) findViewById(R.id.reference); //  edit text needed
        b = (Button) findViewById(R.id.submit);
        requestQueue = Volley.newRequestQueue(this);
        sharedpreferences = getSharedPreferences("Count", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String myname = name.getText().toString();
                Log.e( myname,"onClick: name");
                final String emailID = email.getText().toString();
                Log.e( emailID ,"onClick: email");
                int flag = 0;
                final String myreference = reference.getText().toString();  // added new reference
                request = new StringRequest(Request.Method.GET, URL_check , new Response.Listener<String>() {
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
                                emailarray = new String[obj.length()];
                                tokenarray = new String[obj.length()];
                                for (int j = 0; j < obj.length(); j++) {
                                    JSONObject jsonObject = null;
                                    try {
                                        jsonObject = (JSONObject) (obj.get(j));
                                        String email = jsonObject.optString("email");
                                        String token = jsonObject.optString("token");
                                        emailarray[j] = email;
                                        tokenarray[j] = token;
                                        Log.e( emailarray[j],"onResponse: name" );


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                for( int i = 0; i < emailarray.length; i++ )
                                {
                                    if(emailID.equals(emailarray[i]))
                                    {
                                        flag1 = 1;
                                        password = tokenarray[i];
                                        type1 = "g";
                                        Intent intent_here = new Intent(login.this , Ticket.class);
                                        intent_here.putExtra("Username", myname);
                                        intent_here.putExtra("Password", password);
                                        intent_here.putExtra("type", type1);
                                        startActivity(intent_here);

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

                guest += 1;
                editor.putString("count_var", String.valueOf(guest));
                editor.commit();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String name_count = preferences.getString("count_var", "");
                type1 = "g";
                Intent intent = new Intent(login.this , otp.class);
                if(email.length() == 0||name.length() == 0 || reference.length() ==0 )
                {
                 Toast.makeText(getApplicationContext(),"Please fill all fields",Toast.LENGTH_LONG).show();
                }
                else if( flag1 != 1 )
                {
                    intent.putExtra("Username", myname);
                    intent.putExtra("type", type1);
                    intent.putExtra("email", emailID);
                    intent.putExtra("reference", myreference);
                    startActivity(intent);
                }
            }
        });

    }
}
