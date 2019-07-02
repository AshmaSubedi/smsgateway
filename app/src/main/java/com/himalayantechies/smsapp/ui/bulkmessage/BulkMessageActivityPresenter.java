package com.himalayantechies.smsapp.ui.bulkmessage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.himalayantechies.smsapp.SaveSharedPreferences;
import com.himalayantechies.smsapp.network.ApiInterface;
import com.himalayantechies.smsapp.ui.sms.SMSActivityContract;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BulkMessageActivityPresenter implements BulkMessageContract.presenter {
    BulkMessageContract.view view;
    ApiInterface api;
    private Context context;
    SaveSharedPreferences saveSharedPreferences;

    public BulkMessageActivityPresenter(BulkMessageContract.view view, ApiInterface api, Context context) {
        this.view = view;
        this.api = api;
        this.context = context;
    }


    @Override
    public void sendBulkSMS() {
        saveSharedPreferences = new SaveSharedPreferences(context);
        HashMap<String , Object> map = new HashMap<>();
        map.put("phone_number", view.getNumber());
        map.put("messages", view.getMessage());
        map.put("device_id", view.getDeviceId());
//        Call<JsonObject> sendBulkSMS = api.sendBulkSMS(map, saveSharedPreferences.readToken());
//        sendBulkSMS.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                JsonObject jsonObject = response.body();
//                view.successSend();
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
////                view.failedMsg();
////                t.printStackTrace();
//                Log.e("TAG",t.getMessage());
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        Call<JsonArray> sendBulkSMS = api.sendBulkSMS(map, saveSharedPreferences.readToken());
        sendBulkSMS.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray jsonArray = response.body();
                view.successSend();
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                    view.failedMsg();
                    Log.e("TAG",t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
