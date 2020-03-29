package com.example.coronavirusapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.HttpResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button getOtp;
    EditText mobileNumber;
    String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.holo_red_light, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.holo_red_light));
        }
        //hides action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        mobileNumber = findViewById(R.id.mobileNumber1);
        mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    getOtp.setClickable(false);
                    getOtp.setEnabled(false);
                }
                if(s.length()!=0){
                    getOtp.setBackgroundResource(android.R.drawable.btn_default_small);
                    getOtp.setClickable(false);
                    getOtp.setEnabled(false);
                }
                if(s.length()==10){
                    getOtp.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    getOtp.setClickable(true);
                    getOtp.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getOtp = findViewById(R.id.getOTP);
        getOtp.setClickable(false);
        getOtp.setEnabled(false);
        getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send otp to user
                sendOTPToUser(mobileNumber.getText().toString());
            }
        });
    }

    private void sendOTPToUser(String mobile) {
        IApiOTP apiService =
                ApiClientOTP.getClient().create(IApiOTP.class);

        Call<MessageResponseOTP> call = apiService.sentOTP("72b971c5-702b-11ea-9fa5-0200cd936042", mobile);
        call.enqueue(new Callback<MessageResponseOTP>() {
            @Override
            public void onResponse(Call<MessageResponseOTP> call, Response<MessageResponseOTP> response) {
                if(response.isSuccessful()) {
                    sessionId = response.body().getDetails();
                    Intent intent = new Intent(MainActivity.this,OTPVerification.class);
                    intent.putExtra("sessionId",sessionId);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponseOTP> call, Throwable t) {
                t.printStackTrace();
                Log.e("ERROR", t.toString());
                Toast.makeText(getApplicationContext(),"noooooooooooooo",Toast.LENGTH_SHORT).show();
            }

        });


        /*OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://2factor.in/API/V1/72b971c5-702b-11ea-9fa5-0200cd936042/SMS/7000757693/AUTOGEN")
                .get()
                .addHeader("content-type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseString = response.body().toString();
            JSONObject jsonObject = new JSONObject(responseString);
            sessionId = jsonObject.getString("Details");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }
}
