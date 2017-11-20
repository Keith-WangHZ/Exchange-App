package com.example.huazi.campusexchange.presenters;

import android.content.Context;

import com.example.huazi.campusexchange.callbacks.onLoginFinishedListener;
import com.example.huazi.campusexchange.model.LoginModelImpl;
import com.example.huazi.campusexchange.ui.activitys.entrance.LoginActivity;

/**
 * Created by huazi on 2017/11/7.
 */

public class LoginPresenterImpl implements LoginPresenter, onLoginFinishedListener {

    private final LoginActivity loginActivity;
    private final LoginModelImpl loginModel;

    public LoginPresenterImpl(LoginActivity context){
        this.loginActivity = context;
       this.loginModel = new LoginModelImpl(context);
    }
    @Override
    public boolean checkInput(String userAccount, String psw) {
        loginModel.checkInput(userAccount, psw, this);
        return false;
    }

    @Override
    public void login(String userAccount, String psw) {
        loginModel.login(userAccount, psw, this);
    }

    @Override
    public void writeBoolean(boolean isRememberMe) {
        loginModel.writeBoolean(isRememberMe);
    }

    @Override
    public void writeData(String userinfo, String password, boolean isRememberMe) {
        loginModel.writeData(userinfo, password,isRememberMe);
    }

    @Override
    public String readData(String data) {
        return loginModel.readData(data);
    }

    @Override
    public boolean readBoolean(String data) {
        return loginModel.readBoolean(data);
    }

    @Override
    public void onUsernameError() {
        loginActivity.onUserLoginError(0);

    }

    @Override
    public void onPasswordError() {
        loginActivity.onUserLoginError(2);
    }

    @Override
    public void onSuccess() {
        loginActivity.onUserLoginError(3);
    }

    @Override
    public void onPhoneTrimError() {
        loginActivity.onUserLoginError(1);
    }

    @Override
    public void onNetError() {
        loginActivity.onUserLoginError(4);
    }
}
