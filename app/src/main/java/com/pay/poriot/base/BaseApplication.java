package com.pay.poriot.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.mygreendao.greendao.DaoMaster;
import com.example.mygreendao.greendao.DaoSession;
import com.google.gson.Gson;
import com.pay.poriot.rep.interceptor.LogInterceptor;
import com.pay.poriot.repository.RepositoryFactory;
import com.pay.poriot.repository.SharedPreferenceRepository;
import com.pay.poriot.util.AppFilePath;

import io.realm.Realm;
import okhttp3.OkHttpClient;

public class BaseApplication extends Application{

    private static Context mHtcApp;
    private static BaseApplication sInstance;
    private DaoSession daoSession;
    private static OkHttpClient httpClient;
    public static RepositoryFactory repositoryFactory;
    public static SharedPreferenceRepository sp;

    public static Context getInstance() {
        return mHtcApp;
    }

    public static BaseApplication getsInstance() {
        return sInstance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mHtcApp = this;
        sInstance = this;
        init();
        Realm.init(this);
        AppFilePath.init(this);
    }


    protected void init() {
        sp = SharedPreferenceRepository.init(getApplicationContext());
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor())
                .build();
        Gson gson = new Gson();
        repositoryFactory = RepositoryFactory.init(sp, httpClient, gson);
        //创建数据库表
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "wallet", null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();
    }


    public static OkHttpClient okHttpClient() {
        return httpClient;
    }

    public static RepositoryFactory repositoryFactory() {
        return  repositoryFactory;
    }

}
