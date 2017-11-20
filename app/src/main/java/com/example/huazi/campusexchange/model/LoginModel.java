package com.example.huazi.campusexchange.model;

import com.example.huazi.campusexchange.callbacks.onLoginFinishedListener;

/**
 * Created by 42556 on 2017/11/7.
 */

public interface LoginModel {

    boolean checkInput(String userAccount, String password, onLoginFinishedListener listener);
    void saveLogin(String userAccount, onLoginFinishedListener listener);
    void login(String userAccount, String password, onLoginFinishedListener listener);

    void writeBoolean(boolean isRememberMe);
    void writeData(String userinfo, String password, boolean isRememberMe);
    String readData(String data);
    boolean readBoolean(String data);
}
