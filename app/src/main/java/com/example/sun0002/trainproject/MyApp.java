package com.example.sun0002.trainproject;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by yq895943339 on 2017/5/8.
 */

public class MyApp extends Application {
    private static final String TAG = MyApp.class.getName();


    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
        Utils.init(this);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        Log.e(TAG, "onCreate: "+sharedPreferences );
    }

    public SharedPreferences getSharedPreferencesMyApp() {
        Log.e(TAG, "getSharedPreferencesMyApp: "+sharedPreferences );
        return sharedPreferences;
    }

    private static MyApp instance = null;

    public static MyApp getInstance() {
        if (instance == null) {
            synchronized (MyApp.class) {
                if (instance == null) {
                    instance = new MyApp();
                }
            }
            return instance;
        }
        return instance;
    }

    private static class MyAppHolder {
        /**
         * 单例对象实例
         */
        static final MyApp INSTANCE = new MyApp();
    }

//    public static MyApp getInstance() {
//        return MyAppHolder.INSTANCE;
//    }

    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
//    private MyApp() {
//    }
}
