package com.coder.zt.iplugin;

public interface IBean {

    String getName();

    void setName(String name);

    int getAge();

    void setAge(int age);

    void register(ICallback callback);
}
