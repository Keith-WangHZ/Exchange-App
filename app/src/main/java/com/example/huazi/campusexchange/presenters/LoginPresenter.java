package com.example.huazi.campusexchange.presenters;

/**
 * Created by 42556 on 2017/11/7.
 */

public interface LoginPresenter {
    boolean checkInput(String userAccount, String psw);
    void login(String userAccount, String psw);
    void writeBoolean(boolean isRememberMe);
    void writeData(String userinfo, String password, boolean isRememberMe);
    String readData(String data);
    boolean readBoolean(String data);

}
