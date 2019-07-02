package com.himalayantechies.smsapp.ui.sms;

import android.content.Context;
import android.util.JsonReader;

import com.google.gson.JsonObject;
import com.himalayantechies.smsapp.SaveSharedPreferences;
import com.himalayantechies.smsapp.network.ApiInterface;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SMSActivityPresenter implements SMSActivityContract.presenter{

    SMSActivityContract.view view;
    ApiInterface api;
    private Context context;
    SaveSharedPreferences saveSharedPreferences;

    public SMSActivityPresenter(SMSActivityContract.view view, ApiInterface api, Context context) {
        this.view = view;
        this.api = api;
        this.context = context;
    }


    @Override
    public void sendSMS(String number, String message, String token) {
        saveSharedPreferences = new SaveSharedPreferences(context);
        HashMap<String , Object> map = new HashMap<>();
        map.put("phone_number", Long.parseLong(view.getNumber()));
        map.put("messages", view.getMessage());
        map.put("device_id", view.getDeviceId());
        Call<JsonObject> sendSMS = api.sendSMS(map, saveSharedPreferences.readToken());
        sendSMS.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                view.successSend();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                    view.failedMsg();
                    t.printStackTrace();
            }
        });
    }
}
