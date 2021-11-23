package com.coder.zt.pluginstudy;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MockClass1 implements InvocationHandler {

    private static final String TAG = "MockClass1";
    private Object mInstance;

    public MockClass1(Object instance) {
        this.mInstance = instance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if(method.getName().equals("startActivity")){
            Log.e(TAG, "invoke: " + method.getName() + "  Π大星到此一游!");
            //截获启动Activity中的信息并修改信息
            Intent raw;
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                if(args[i] instanceof Intent){
                    index = i;
                    break;
                }
            }
            raw = (Intent) args[index];
            Intent newIntent = new Intent();
            String stubPackage = raw.getComponent().getPackageName();
            ComponentName componentName = new ComponentName(stubPackage, SecondActivity.class.getName());
            newIntent.setComponent(componentName);
            newIntent.putExtra(AMSHookHelper.EXTRA_TARGET_INTENT, raw);
            args[index] = newIntent;
            Log.d(TAG, "invoke: hook success");
            return method.invoke(mInstance, args);
        }
        return method.invoke(mInstance, args);
    }
}
