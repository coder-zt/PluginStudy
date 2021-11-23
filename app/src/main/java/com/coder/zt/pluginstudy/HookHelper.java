package com.coder.zt.pluginstudy;

import android.app.Instrumentation;
import android.os.Handler;
import android.util.Log;

import com.coder.zt.pluginstudy.utils.RefInvoke;


public class HookHelper {

    private static final String TAG = "HookHelper";

    public static void attachBaseContext() {
        Object currentActivityThread = RefInvoke.getStaticFieldObject("android.app.ActivityThread", "sCurrentActivityThread");
        Log.d(TAG, "attachBaseContext: " + (currentActivityThread == null));
        Handler mH = (Handler) RefInvoke.getFieldObject("android.app.ActivityThread", currentActivityThread, "mH");
        RefInvoke.setFieldObject(Handler.class, mH, "mCallback", new MockClass2(mH));
    }

    public static void attachContext() {
        Object currentActivityThread = RefInvoke.invokeStaticMethod("android.app.ActivityThread", "currentActivityThread");
        Instrumentation mInstrumentation = (Instrumentation) RefInvoke.getFieldObject("android.app.ActivityThread", currentActivityThread, "mInstrumentation");
        EvilInstrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);
        RefInvoke.setFieldObject(currentActivityThread,"mInstrumentation", evilInstrumentation);
    }
}
