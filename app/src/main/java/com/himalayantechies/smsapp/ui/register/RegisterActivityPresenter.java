package com.himalayantechies.smsapp.ui.register;

import android.content.Context;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.himalayantechies.smsapp.network.ApiInterface;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivityPresenter implements RegisterActivityContract.Presenter {

    private RegisterActivityContract.View view;
    private ApiInterface api;
    private Context context;

    public RegisterActivityPresenter(RegisterActivityContract.View view, ApiInterface api, Context context) {
        this.view = view;
        this.api = api;
        this.context = context;
    }

    @Override
    public void register() {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", view.getUserEmail());
        map.put("password", view.getUserPassword());
        Call<JsonObject> registerUser = api.registerUser(map);
        registerUser.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response != null) {
//                    if (response.isSuccessful()) {
                        JsonObject object = response.body();
                        view.registerSuccess();
//                    }
//                    else{
//                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else{
//                    Toast.makeText(context, "Response is null", Toast.LENGTH_SHORT).show();
//                }
//                Log.i("Tag",object.toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    @Override
    public void validate() {

        if (view.getUserEmail().equals("")) {
            view.validationError("Email cannot be empty");
            return;
        }

        if (view.getUserPassword().equals("")) {
            view.validationError("Password cannot be empty");
            return;
        }

    }


}
