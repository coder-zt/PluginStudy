package com.coder.zt.plugin;

import com.coder.zt.iplugin.IBean;
import com.coder.zt.iplugin.ICallback;

public class DataBean implements IBean {
    private String name = "Π大星";
    private int age = 18;

    private ICallback callback;
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void register(ICallback callback) {
        this.callback = callback;
        sendMessage("插件发送消息");
    }

    private void sendMessage(String msg){
        if (callback != null) {
            callback.callback(msg);
        }
    }
}
