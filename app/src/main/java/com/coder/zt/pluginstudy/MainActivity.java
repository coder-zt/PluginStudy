package com.coder.zt.pluginstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.coder.zt.iplugin.IBean;
import com.coder.zt.iplugin.ICallback;
import com.coder.zt.iplugin.IResGetter;
import com.coder.zt.iplugin.ProxyActivity;
import com.coder.zt.iplugin.utils.PluginManager;
import com.coder.zt.pluginstudy.utils.FileUtils;
import com.coder.zt.pluginstudy.utils.RefInvoke;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends Activity {

    private String apkName = "mylibrary-debug.apk";    //apk名称
    private static final String TAG = "MainActivity";
    private AssetManager mAssetManager;
    private Resources mResources;
    private Resources.Theme mTheme;
    private String dexpath = null;    //apk文件地址
    private File fileRelease = null;  //释放目录
    private DexClassLoader classLoader = null;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
                FileUtils.extractAssets(newBase, apkName);
//            HookHelper.attachBaseContext();
//            HookHelper.attachContext();
//            /**
//             *
//             */
//            AMSHookHelper.hookMAN();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDex();
        /**
         *
         */
//        Instrumentation mInstrumentation = (Instrumentation) RefInvoke
//                .getFieldObject(Activity.class, this, "mInstrumentation");
//        EvilInstrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);
//        RefInvoke.setFieldObject(Activity.class, this, "mInstrumentation",evilInstrumentation);
    }

    private void loadDex() {
        File extractFile = this.getFileStreamPath(apkName);
        dexpath = extractFile.getPath();
        fileRelease = getDir("dex", 0); //0 表示Context.MODE_PRIVATE
        classLoader = new DexClassLoader(dexpath,
                fileRelease.getAbsolutePath(), null, getClassLoader());


        TextView tvBtn = findViewById(R.id.tv_btn);
        tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                loadResources();
                Class mLoadClassBean;
                try {
                    mLoadClassBean = classLoader.loadClass("com.coder.zt.plugin.res.ResGetter");
                    Object obj =  mLoadClassBean.newInstance();
                    IResGetter beanObject = (IResGetter) obj;
                    Log.d(TAG, "onClick: " + beanObject.getString(MainActivity.this));

                    @SuppressLint("ResourceType")
                    String appName = getResources().getString(0x7f0d0068);
                    Log.d(TAG, "onClick: 获取了插件中的字符串：  " + appName);

                    mResources = MainActivity.super.getResources();
                    Log.d(TAG, "onClick: " + getResources().getString(R.string.host_str));

//                    mLoadClassBean = classLoader.loadClass("com.coder.zt.plugin.activity.PluginActivity");
//                    Object obj =  mLoadClassBean.newInstance();
//                    ProxyActivity beanObject = (ProxyActivity) obj;
//                    Intent intent = new Intent();
//                    String activityName = PluginManager.plugins.get(0).packageInfo.packageName + ".activity.PluginActivity";
//                    String activityName = "com.coder.zt.plugin.activity.PluginActivity";
//                    intent.setClass(MainActivity.this, Class.forName(activityName));
//
//                    startActivity(intent);
//                    Method getNameMethod = mLoadClassBean.getMethod("getName");
//                    getNameMethod.setAccessible(true);
//                    String name = (String) getNameMethod.invoke(beanObject);
//                    beanObject.register(new ICallback() {
//                        @Override
//                        public void callback(String msg) {
//                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
//                        }
//                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadResources() {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Log.d(TAG, "loadResources: assetManager is null " + (assetManager == null));
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            Log.d(TAG, "loadResources: addAssetPath is null " + (addAssetPath == null));

            addAssetPath.invoke(assetManager, dexpath);
            mAssetManager = assetManager;
            Log.d(TAG, "loadResources: mAssetManager is null " + (mAssetManager == null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mResources = new Resources(mAssetManager,
                super.getResources().getDisplayMetrics(),
                super.getResources().getConfiguration());
        mTheme = mResources.newTheme();
        mTheme.setTo(super.getTheme());
    }

    @Override
    public AssetManager getAssets() {

        if (mAssetManager == null) {
            return super.getAssets();
        }
        return mAssetManager;
    }

    @Override
    public Resources getResources() {
        Log.d(TAG, "getResources: 获取资源对象");
        if (mResources == null) {
            return super.getResources();
        }
        return  mResources;
    }

    @Override
    public Resources.Theme getTheme() {
        if (mTheme == null) {
            return super.getTheme();
        }
        return mTheme;
    }
    private void jump() {

        Intent i = new Intent(MainActivity.this, NoRegisterActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(i);
    }
}