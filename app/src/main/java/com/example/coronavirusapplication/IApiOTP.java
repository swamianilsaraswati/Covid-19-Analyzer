package com.example.coronavirusapplication;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Call;

public interface IApiOTP {
    @GET("{api_key}/SMS/+91{users_phone_no}/AUTOGEN")
    Call<MessageResponseOTP> sentOTP(@Path("api_key")String apiKey, @Path("users_phone_no")String phone_no);
    @GET("{api_key}/SMS/VERIFY/{session_id}/{otp_entered_by_user}")
    Call<MessageResponseOTP> verifyOTP(@Path("api_key")String apiKey, @Path("session_id")String session_id,@Path("otp_entered_by_user")String otp_entered_by_user);
}
