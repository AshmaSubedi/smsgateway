package com.himalayantechies.smsapp.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.himalayantechies.smsapp.models.Auth;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

public static final String PREF ="com.himalayan.smsapp";

    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    @Provides
    public SharedPreferences.Editor provideEditor(SharedPreferences preferences) {
        return preferences.edit();
    }

    @Provides
    Auth providesAuth(SharedPreferences preferences) {
        String json = preferences.getString("auth", "");
        if (json.equals("")) return null;
        Gson gson = new Gson();
        Auth auth = gson.fromJson(json, Auth.class);
        return auth;
    }

}
