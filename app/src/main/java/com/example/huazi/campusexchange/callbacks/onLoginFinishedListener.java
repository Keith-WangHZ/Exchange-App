package com.example.huazi.campusexchange.callbacks;

/**
 * Created by huazi on 2017/11/7.
 */

public interface onLoginFinishedListener {

    void onUsernameError();

    void onPasswordError();

    void onSuccess();

    void onPhoneTrimError();

    void onNetError();
}
