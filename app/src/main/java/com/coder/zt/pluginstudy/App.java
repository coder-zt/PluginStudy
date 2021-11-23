package com.coder.zt.pluginstudy;

import android.app.Application;
import android.content.Context;

import com.coder.zt.iplugin.utils.PluginManager;

public class App extends Application {

    private static Context sContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = base;
        PluginManager.init(this);
    }

    public static Context getContext() {
        return sContext;
    }
}
