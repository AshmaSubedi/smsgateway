package com.himalayantechies.smsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class SaveSharedPreferences {

    private SharedPreferences sharedPreferences;
    private Context context;

    public SaveSharedPreferences( Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file),Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status), status);
        editor.commit();
    }

    public boolean readLoginStatus(){
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status), false);
    }

    public String readToken(){
        return sharedPreferences.getString(context.getString(R.string.pref_user_token), null);
    }

    public String writeToken(String token){
        if (token != null && token.startsWith("Bearer:")){
            token = token.replace("Bearer:", "Bearer");
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_token), token);
        editor.apply();
        return token;

    }

    public Integer readDeviceId(){
        return sharedPreferences.getInt(context.getString(R.string.pref_user_deviceId), -1);
    }

    public Integer writeDeviceId(Integer deviceId){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.pref_user_deviceId), deviceId);
        editor.apply();
        return deviceId;

    }


}
