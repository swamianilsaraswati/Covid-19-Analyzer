package com.example.coronavirusapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerification extends AppCompatActivity {

    EditText otpValue;
    Button verifyButton;
    String sessionId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verification);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            sessionId = extras.getString("sessionId");
        }

        //status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.holo_red_light, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.holo_red_light));
        }
        //hides action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_o_t_p_verification);

        Toast.makeText(getApplicationContext(),sessionId,Toast.LENGTH_LONG).show();

        otpValue = findViewById(R.id.otp1);
        otpValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    verifyButton.setClickable(false);
                }
                if(s.length()!=0){
                    verifyButton.setBackgroundResource(android.R.drawable.btn_default_small);
                    verifyButton.setClickable(false);
                }
                if(s.length()==6){
                    verifyButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    verifyButton.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        verifyButton = findViewById(R.id.verifyOTP);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOTP(sessionId,otpValue.getText().toString());
            }
        });
    }

    private void verifyOTP(String sessionId, String otpAuto) {
        IApiOTP apiService =
                ApiClientOTP.getClient().create(IApiOTP.class);

        Call<MessageResponseOTP> call = apiService.verifyOTP("72b971c5-702b-11ea-9fa5-0200cd936042",sessionId, otpAuto);

        call.enqueue(new Callback<MessageResponseOTP>() {

            @Override
            public void onResponse(Call<MessageResponseOTP> call, Response<MessageResponseOTP> response) {

                try {
                    if(response.body().getStatus().equals("Success")){
                        Intent intent = new Intent(OTPVerification.this,AlmostThere.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Not Success",Toast.LENGTH_SHORT).show();
                        Log.d("Failure", response.body().getDetails()+"|||"+response.body().getStatus());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MessageResponseOTP> call, Throwable t) {
                Log.e("ERROR", t.toString());
            }

        });
    }
}
