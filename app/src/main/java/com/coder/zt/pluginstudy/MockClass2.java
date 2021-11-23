package com.coder.zt.pluginstudy;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.coder.zt.pluginstudy.utils.RefInvoke;

public class MockClass2 implements Handler.Callback{
    private static final String TAG = "MockClass2";
    private Handler mH;
    public MockClass2(Handler H) {
        mH = H;
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        Log.d(TAG, "handleMessage: " + msg.what);
        switch (msg.what){
            case 149:
                handleLaunchActivity(msg);
                break;
        }
        mH.handleMessage(msg);
        return true;
    }

    private void handleLaunchActivity(Message msg) {
        Object obj = msg.obj;
        Intent intent = (Intent) RefInvoke.getFieldObject(obj, "intent");
        if (intent != null) {
            Intent targetIntent = intent.getParcelableExtra(AMSHookHelper.EXTRA_TARGET_INTENT);
            intent.setComponent(targetIntent.getComponent());
            Log.d(TAG, "handleLaunchActivity: " + obj.toString());
        }
    }
}
