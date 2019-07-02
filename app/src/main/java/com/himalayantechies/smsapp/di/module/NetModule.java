package com.himalayantechies.smsapp.di.module;

import com.himalayantechies.smsapp.network.ApiInterface;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = AppModule.class)
public class NetModule {

    String baseUrl;

    public NetModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    public OkHttpClient provideClient() {
        HttpLoggingInterceptor loggingInterceptor  = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder().connectTimeout(240, TimeUnit.SECONDS).writeTimeout(240, TimeUnit.SECONDS).readTimeout(240,TimeUnit.SECONDS);
        client.addNetworkInterceptor(loggingInterceptor);
        return client.build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client) {

        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    public ApiInterface provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }


}
