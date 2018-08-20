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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class login extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    private static final String URL_guest = "https://script.google.com/macros/s/AKfycbxv-7ZjjQ9PYNDvXRn0Z-RZ8doJNYzOS0D26YS0caxmtdtM2fUR/exec";
    private RequestQueue requestQueue;
    private StringRequest request;
    String password , type1;
    int guest;
    EditText name , phonenumber , email , reference;   // added email, refered person
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        name = (EditText) findViewById(R.id.myname);
        //phonenumber = (EditText) findViewById(R.id.myphone);
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
                //final String myphone = phonenumber.getText().toString();
                final String emailID = email.getText().toString();
                Log.e( emailID ,"onClick: email");
                int flag = 0;
                final String myreference = reference.getText().toString();  // added new reference
              //  final String id = "1L-8iuRCWLaHkwsAbTOLuni3sfJFpXe51DYeOSUSA5Cw";

           /*     if(email.length() != 0 && name.length() != 0 && reference.length() !=0) {
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
                            hashMap.put("name", myname);
                           // hashMap.put("phonenumber", myphone);
                            hashMap.put("id", id);
                            hashMap.put("email", emailID);  // added email
                            hashMap.put("reference", myreference);  // added refernce


                            return hashMap;

                        }
                    };


                    requestQueue.add(request);

                }
                */
                guest += 1;
                editor.putString("count_var", String.valueOf(guest));
                editor.commit();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String name_count = preferences.getString("count_var", "");
                type1 = "g";
                Random random = new Random();
                password = String.valueOf(random.nextInt(100000) + 0);
                Log.e( password,"onClick: password" );
                Intent intent = new Intent(login.this , otp.class);
                if(email.length() == 0||name.length() == 0 || reference.length() ==0)
                {
                 Toast.makeText(getApplicationContext(),"Please fill all fields",Toast.LENGTH_LONG).show();
                }
                else
                {
                    intent.putExtra("Password", password);
                    intent.putExtra("Username", myname);
                    intent.putExtra("type", type1);
                    intent.putExtra("count", name_count);
                    intent.putExtra("email", emailID);
                    intent.putExtra("reference", myreference);
                    Log.e(name_count, "onClick: count");
                    startActivity(intent);
                }
            }
        });

    }
}
