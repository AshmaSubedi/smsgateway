package com.himalayantechies.smsapp.base;

import android.app.Application;
import android.graphics.Bitmap;

import com.himalayantechies.smsapp.di.Component.AppComponent;
import com.himalayantechies.smsapp.di.Component.DaggerAppComponent;
import com.himalayantechies.smsapp.di.module.AppModule;
import com.himalayantechies.smsapp.di.module.NetModule;

public class App extends Application {

    AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(Config.getBaseUrl()))
                .build();
    }

    public AppComponent getComponent() {
        return  component;
    }

}
