package com.hamzeh.moraghebehapp.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hamzeh.moraghebehapp.data.database.daoes.AmalDao;
import com.hamzeh.moraghebehapp.data.database.daoes.ArbayiinDao;
import com.hamzeh.moraghebehapp.data.database.daoes.ResultsDao;
import com.hamzeh.moraghebehapp.retrofit.Api;
import com.hamzeh.moraghebehapp.retrofit.calls.AmalCall;
import com.hamzeh.moraghebehapp.retrofit.calls.ArbayiinCall;
import com.hamzeh.moraghebehapp.retrofit.calls.LoginCall;
import com.hamzeh.moraghebehapp.retrofit.calls.ResultsCall;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ActivityRetainedScoped;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
@InstallIn(ActivityRetainedComponent.class)
public class WebServiceModule {



    @ActivityRetainedScoped
    @Provides
    public static Api provideWebService(ExecutorService executorService) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient mClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
//                .addInterceptor(oauth1Wordpress)
                .build();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.5:3000/")
                .callbackExecutor(executorService)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(mClient);

        Retrofit retrofit = builder.build();
        Log.d("inside ", "retrofit");
        return retrofit.create(Api.class);
    }

    @ActivityRetainedScoped
    @Provides
    public static LoginCall provideLoginCall(Api retrofit,
                                             SharedPreferences.Editor editor) {
        return new LoginCall(retrofit, editor);

    }

    @ActivityRetainedScoped
    @Provides
    public static ArbayiinCall provideGetArbayiinCall(Api retrofit,
                                                      SharedPreferences sharedPreferences, ArbayiinDao arbayiinDao) {
        return new ArbayiinCall(retrofit, sharedPreferences, arbayiinDao);

    }

    @ActivityRetainedScoped
    @Provides
    public static AmalCall provideGetAmalCall(Api retrofit,
                                              SharedPreferences sharedPreferences, AmalDao amalDao,
                                              @ApplicationContext Context context) {
        return new AmalCall(retrofit, sharedPreferences, amalDao, context);

    }

    @ActivityRetainedScoped
    @Provides
    public static ResultsCall provideResultsCall(Api retrofit,
                                                 SharedPreferences sharedPreferences, ResultsDao resultsDao) {
        return new ResultsCall(retrofit, sharedPreferences, resultsDao);

    }


}
