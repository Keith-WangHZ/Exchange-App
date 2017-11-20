package com.example.huazi.campusexchange.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.huazi.campusexchange.R;
import com.example.huazi.campusexchange.callbacks.onLoginFinishedListener;
import com.example.huazi.campusexchange.utils.NetUtils;
import com.example.huazi.campusexchange.utils.RegexUtil;

import static android.content.Context.MODE_MULTI_PROCESS;
import static android.provider.Telephony.Mms.Part.FILENAME;

/**
 * Created by 42556 on 2017/11/7.
 */

public class LoginModelImpl implements LoginModel{

    private final Context context;

    public LoginModelImpl(Context context){
        this.context = context;
    }
    @Override
    public boolean checkInput(String userAccount, String password, onLoginFinishedListener listener) {
        if (userAccount == null || userAccount.trim().equals("")) {
            listener.onUsernameError();
        } else {
            // 账号不匹配手机号格式（11位数字且以1开头）
            if ( !RegexUtil.checkMobile(userAccount)) {
                listener.onPhoneTrimError();
            } else if (password == null || password.trim().equals("")) {
                listener.onPasswordError();
            } else {
                return true;
            }
        }

        return false;
    }

    @Override
    public void saveLogin(String userAccount, onLoginFinishedListener listener) {

    }

    @Override
    public void login(final String userAccount, final String password, final onLoginFinishedListener listener) {

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 200:
                        listener.onSuccess();
                        break;
                    case 400:
                        listener.onNetError();
                        break;
                }
            }
        };
        new Thread(){
            @Override
            public void run() {
                String result = NetUtils.httpUrlConnGet(userAccount, password);
                if (null != result && !result.equals("0")){
                    handler.sendEmptyMessage(200);
                }else {
                    handler.sendEmptyMessage(400);
                }
            }
        }.start();
    }

    @Override
    public void writeBoolean(boolean isRememberMe) {
        SharedPreferences.Editor share_edit = context.getSharedPreferences(FILENAME,
                MODE_MULTI_PROCESS).edit();
        share_edit.putBoolean("isrememberme", isRememberMe);
        share_edit.commit();
    }

    @Override
    public void writeData(String userinfo, String password, boolean isRememberMe) {
        SharedPreferences.Editor share_edit = context.getSharedPreferences(FILENAME,
                MODE_MULTI_PROCESS).edit();
        share_edit.putString("userinfo", userinfo);
        share_edit.putString("password", password);
        share_edit.putBoolean("isrememberme", isRememberMe);
        share_edit.commit();
    }

    @Override
    public String readData(String data) {
        SharedPreferences pref = context.getSharedPreferences(FILENAME, MODE_MULTI_PROCESS);
        String str = pref.getString(data, "");
        return str;
    }

    @Override
    public boolean readBoolean(String data) {
        SharedPreferences pref = context.getSharedPreferences(FILENAME, MODE_MULTI_PROCESS);
        return pref.getBoolean(data, false);
    }
}
