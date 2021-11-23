package com.coder.zt.plugin.res;

import android.content.Context;

import com.coder.zt.iplugin.IResGetter;
import com.coder.zt.mylibrary.R;

public class ResGetter implements IResGetter {
    @Override
    public String getString(Context context) {
        return context.getResources().getString(R.string.plugin_str);
    }
}
