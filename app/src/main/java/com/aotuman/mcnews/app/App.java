package com.aotuman.mcnews.app;

import android.app.Application;
import android.content.Context;

/**
 * ClassName: App<p>
 * Author:oubowu<p>
 * Fuction: Application<p>
 * CreateDate:2016/2/16 1:25<p>
 * UpdateUser:<p>
 * UpdateDate:<p>
 */
public class App extends Application {

    private static Context sApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplicationContext = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }




    // 获取ApplicationContext
    public static Context getContext() {
        return sApplicationContext;
    }

}
