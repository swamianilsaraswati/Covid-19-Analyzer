package com.example.coronavirusapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.coronavirusapplication.gateway.registerServer;

import org.json.JSONException;
import org.json.JSONObject;

public class AlmostThere extends AppCompatActivity {

    EditText firstName,lastName;
    Button proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.holo_red_light, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.holo_red_light));
        }
        //hides action bar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_almost_there);

        firstName = findViewById(R.id.firstName1);
        lastName = findViewById(R.id.lastName1);

        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0 && lastName.getText().toString().length()!=0){
                    proceed.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    proceed.setEnabled(true);
                    proceed.setClickable(true);
                }else{
                    proceed.setBackgroundColor(Color.rgb(235,235,228));
                    proceed.setEnabled(false);
                    proceed.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0 && firstName.getText().toString().length()!=0){
                    proceed.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    proceed.setEnabled(true);
                    proceed.setClickable(true);
                }else{
                    proceed.setBackgroundColor(Color.rgb(211,211,211));
                    proceed.setEnabled(false);
                    proceed.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        proceed = findViewById(R.id.proceed);
        proceed.setBackgroundColor(Color.rgb(211,211,211));
        proceed.setClickable(false);
        proceed.setEnabled(false);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveToDatabase("9470423844",firstName.getText().toString(),lastName.getText().toString());
                Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AlmostThere.this,HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveToDatabase(String mobileNumber, String firstName, String lastName) {

        Response.Listener<String> listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("success");
                    if(success.equals("success")){

                    }else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message=null;
                if(error.networkResponse==null){
                    if(error instanceof NetworkError){
                        message="Cannot connect to Internet...Please check your connection!";
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                    }else if(error instanceof ServerError){
                        message="The server could not be found. Please try again after some time!!";
                    }else if(error instanceof AuthFailureError){
                        message="Cannot connect to Internet...Please check your connection!";
                    }else if(error instanceof ParseError){
                        message="Parsing error! Please try again after some time!!";
                    }else if(error instanceof TimeoutError){
                        message="Connection TimeOut! Please check your internet connection.";
                    }
                }
            }
        };
        registerServer register=new registerServer(mobileNumber,firstName,lastName,listener,errorListener);
        RequestQueue queue= Volley.newRequestQueue(AlmostThere.this);
        queue.add(register);
        register.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
}
