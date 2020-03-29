package com.example.coronavirusapplication.gateway;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class registerServer extends StringRequest {
    private static final String url="http://nitcmessmanagement.hostingerapp.com/login.php";
    private Map<String,String> param;
    public registerServer(String mobileNumber, String firstName, String LastName,Response.Listener<String> listener,Response.ErrorListener errorListener){
        super(Method.POST,url,listener,errorListener);
        param=new HashMap<>();
        param.put("mobileNumber",mobileNumber);
        param.put("firstName",firstName);
        param.put("LastName",LastName);
    }
    public Map<String,String> getParams(){return param;}
}
