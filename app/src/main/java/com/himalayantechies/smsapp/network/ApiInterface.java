package com.himalayantechies.smsapp.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.himalayantechies.smsapp.models.Auth;

import org.json.JSONArray;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers(
            {"Content-Type:application/json","Cache-Control: no-cache"}
    )
    @POST("users/create")
    Call<JsonObject> registerUser(@Body HashMap<String,String> data);

//    @FormUrlEncoded
//    @POST("users/login")
//    Call<Auth> login(@Field("email") String email, @Field("password") String password);

    @POST("users/login")
    @Headers(
            {"Content-Type:application/json",
                    "Cache-Control: no-cache"}
    )
    Call<Auth> login(@Body HashMap<String, String> data);

    @POST("users/sendText")
    Call<JsonObject> sendSMS(@Body HashMap<String, Object> data, @Header("Authorization") String token);

    @POST("users/bulkText")
    Call<JsonArray> sendBulkSMS(@Body HashMap<String, Object> data, @Header("Authorization") String token);

//    @POST("users/bulkText")
//    Call<JsonObject> sendBulkSMS(@Body HashMap<String, Object> data, @Header("Authorization") String token);

}
