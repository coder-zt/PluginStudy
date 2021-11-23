package com.coder.zt.pluginstudy;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.coder.zt.pluginstudy.utils.RefInvoke;

public class EvilInstrumentation extends Instrumentation {

    private static final String TAG = "EvilInstrumentation";
    private Instrumentation mBase;

    public EvilInstrumentation(Instrumentation mBase) {
        this.mBase = mBase;
    }



    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        Log.d(TAG, "execStartActivity: Π大星到此一游!");
        Class[] p1 = {Context.class, IBinder.class, IBinder.class, Activity.class,
                Intent.class,int.class, Bundle.class};
        Object[] v1 = {who, contextThread, token, target, intent, requestCode, options};
        return (ActivityResult) RefInvoke.invokeInstanceMethod(mBase, "execStartActivity", p1, v1);
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Log.d(TAG, "newActivity: Π大星到此一游!");
        Intent targetIntent = intent.getParcelableExtra(AMSHookHelper.EXTRA_TARGET_INTENT);
        if (targetIntent == null) {
            return mBase.newActivity(cl, className, intent);
        }
        String targetClassName = targetIntent.getComponent().getClassName();
        return mBase.newActivity(cl, targetClassName, targetIntent);
    }

    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        Log.d(TAG, "callActivityOnCreate: Π大星到此一游!");
        mBase.callActivityOnCreate(activity, icicle);
    }
}
