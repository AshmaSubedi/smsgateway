package com.himalayantechies.smsapp.di.Component;


import com.himalayantechies.smsapp.MainActivity;
import com.himalayantechies.smsapp.base.BaseActivity;
import com.himalayantechies.smsapp.di.module.AppModule;
import com.himalayantechies.smsapp.di.module.NetModule;
import com.himalayantechies.smsapp.ui.login.LoginActivity;
import com.himalayantechies.smsapp.ui.register.RegisterActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {

    void inject(LoginActivity target);

    void inject(RegisterActivity target);

    void inject(BaseActivity target);

    void inject(MainActivity target);

}
