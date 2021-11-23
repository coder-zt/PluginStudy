package com.coder.zt.iplugin;

import android.app.Activity;
import android.content.res.Resources;

import com.coder.zt.iplugin.utils.PluginManager;

public abstract class ProxyActivity extends Activity {

    @Override
    public Resources getResources() {
        return PluginManager.mNowResources;
    }
}
