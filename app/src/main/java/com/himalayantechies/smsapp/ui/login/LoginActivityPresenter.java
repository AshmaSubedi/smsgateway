package com.himalayantechies.smsapp.ui.login;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.himalayantechies.smsapp.SaveSharedPreferences;
import com.himalayantechies.smsapp.models.Auth;
import com.himalayantechies.smsapp.network.ApiInterface;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityPresenter implements LoginActivityContract.Presenter {

    private LoginActivityContract.View view;
    private ApiInterface api;
    SaveSharedPreferences saveSharedPreferences;
    private Context context;

    public LoginActivityPresenter(LoginActivityContract.View view, ApiInterface api, Context context) {
        this.view = view;
        this.api = api;
        this.context = context;
    }

    @Override
    public void authenticate(String username, String password) {

        HashMap<String, String> map = new HashMap<>();
        map.put("email", view.getUserEmail());
        map.put("password", view.getPassword());
//
        Call<Auth> checkLogin = api.login(map);
//        Call<Auth> checkLogin = api.login(username, password);
        saveSharedPreferences = new SaveSharedPreferences(context);
        checkLogin.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        Auth auth = response.body();
                        saveSharedPreferences.writeToken(auth.getToken());
                        saveSharedPreferences.writeDeviceId(auth.getId());
                        saveSharedPreferences.writeLoginStatus(true);

                        Log.i("SharedPreferences",""+saveSharedPreferences.readToken())
;                        view.loginSuccess();
                    }

//                if (response.isSuccessful()) {
//                    saveSharedPreferences.writeToken(auth.getToken());
//                    saveSharedPreferences.writeLoginStatus(true);
//                    view.loginSuccess();
//
//                }
                }
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void validation() {
        if (view.getUserEmail().equals("")) {
            view.validationError("Email cannot be empty");
        }
        if (view.getPassword().equals("")) {
            view.validationError("Password cannot be empty");
        }

    }

    @Override
    public void saveUserEmail(boolean isRemember) {

    }
}
