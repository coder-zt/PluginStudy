package com.coder.zt.plugin.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.coder.zt.iplugin.ProxyActivity;
import com.coder.zt.mylibrary.R;

public class PluginActivity extends ProxyActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);
    }
}
