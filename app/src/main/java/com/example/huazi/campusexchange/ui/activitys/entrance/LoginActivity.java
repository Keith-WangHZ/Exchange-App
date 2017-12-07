package com.example.huazi.campusexchange.ui.activitys.entrance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huazi.campusexchange.R;
import com.example.huazi.campusexchange.model.bean.UserBean;
import com.example.huazi.campusexchange.presenters.LoginPresenterImpl;
import com.example.huazi.campusexchange.ui.base.BaseActivity;
import com.example.huazi.campusexchange.ui.view.CleanEditText;
import com.example.huazi.campusexchange.utils.BlurUtil;
import com.example.huazi.campusexchange.utils.WindowsUtil;

import java.util.List;


/**
 * @creation_time: 2017/11/05
 * @author: huazi
 * @e-mail: wanghuazhi_beijing@163.com
 * @describe: 登录
 */

public class LoginActivity extends BaseActivity {
    private CleanEditText et_email_phone;
    private CleanEditText et_password;
    private Button btn_login;
    private List<UserBean> userList;
    private TextView tv_create_account;
    // 弹出框
    private ProgressDialog mDialog;
    private LoginPresenterImpl loginPresenter;
    private String account;
    private String password;
    private CheckBox rememberMe;


    @Override
    protected void onCreate() {
        WindowsUtil.fullScreen(this);
        initLayout(R.layout.activity_login);
        Drawable mBackground = getDrawable(R.drawable.login_background);
        Bitmap mBitMap = BlurUtil.bitMapBlur(mBackground, 10, 8, 3);
        RelativeLayout loginLayout = (RelativeLayout)findViewById(R.id.layout_root);
        loginLayout.setBackground(new BitmapDrawable(getResources(),mBitMap));
        loginPresenter = new LoginPresenterImpl(this);
        initView();
    }

    private void initView(){
        et_email_phone = getView(R.id.et_email_phone);
        et_password = getView(R.id.et_password);
        rememberMe = (CheckBox)getView(R.id.ck_userinfo);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_create_account = getView(R.id.tv_create_account);

        Log.d("wanghuazhi", "remember = " + loginPresenter.readBoolean("isrememberme") + ", name = " + loginPresenter.readData("userinfo"));
        if(loginPresenter.readBoolean("isrememberme")){
            rememberMe.setChecked(true);
            et_email_phone.setText(loginPresenter.readData("userinfo"));
            et_password.setText(loginPresenter.readData("password"));
        }else{
            rememberMe.setChecked(false);
            et_email_phone.setText("");
            et_password.setText("");
        }

        et_email_phone.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        et_email_phone.setTransformationMethod(HideReturnsTransformationMethod
                .getInstance());

        et_password.setImeOptions(EditorInfo.IME_ACTION_DONE);
        et_password.setImeOptions(EditorInfo.IME_ACTION_GO);
        et_password.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO) {
//                    clickLogin();
                    account = et_email_phone.getText().toString();
                    password = et_password.getText().toString();
                    loginPresenter.login(account, password);
                }
                return false;
            }
        });

        rememberMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.writeBoolean(rememberMe.isChecked());
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                clickLogin();
                account = et_email_phone.getText().toString();
                password = et_password.getText().toString();
                loginPresenter.login(account, password);
            }
        });

        tv_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SignUpActivity.class));
            }
        });
    }

    /**
     * 检查输入
     *
     * @param reponse
     * @return
     */
    public boolean onUserLoginError(int reponse){
        switch (reponse){
            case 0:
                Toast.makeText(this, R.string.tip_account_empty, Toast.LENGTH_LONG)
                        .show();
                break;
            case 1:
                Toast.makeText(this, R.string.tip_account_regex_not_right,
                        Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this, R.string.tip_password_can_not_be_empty,
                        Toast.LENGTH_LONG).show();
                break;
            case 3:
                Log.d("wanghuazhi", account+", "+password+", "+rememberMe.isChecked());
                loginPresenter.writeData(account, password, rememberMe.isChecked());
                mDialog = new ProgressDialog(this);
                mDialog.setMessage("正在登陆，请稍后...");
                mDialog.show();
                return true;
            case 4:
                Toast.makeText(this, "net error", Toast.LENGTH_LONG).show();
                break;
        }
        return false;
    }

}
