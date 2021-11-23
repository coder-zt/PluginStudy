package com.coder.zt.pluginstudy;

import com.coder.zt.pluginstudy.utils.RefInvoke;

import java.lang.reflect.Proxy;

public class AMSHookHelper {

    private static final String TAG = "AMSHookHelper";
    public static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    public static void hookMAN() throws ClassNotFoundException {
        // 获取ActivityTaskManager对象中的静态字段IActivityTaskManagerSingleton
        Object iActivityTaskManager = RefInvoke.getStaticFieldObject(
                "android.app.ActivityTaskManager", "IActivityTaskManagerSingleton");
        // IActivityTaskManagerSingleton是一个Singleton<T>对象，接着获取设置单例中的实例对象mInstance,
        // 而mInstance是IActivityTaskManager的实例对象，我们在此hook该对象
        Object mInstance = RefInvoke.getFieldObject("android.util.Singleton", iActivityTaskManager, "mInstance");
        Class<?> classB2Interface = Class.forName("android.app.IActivityTaskManager");
        //通过Proxy.newProxyInstance()创建上文提到的mInstance的代理对象，通过该代理对象可以在调用方法前后添加其他操作
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{classB2Interface},
                new MockClass1(mInstance));
        RefInvoke.setFieldObject("android.util.Singleton", iActivityTaskManager, "mInstance", proxy);
    }
}
