package com.example.huazi.campusexchange.ui.activitys.entrance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.huazi.campusexchange.MainActivity;
import com.example.huazi.campusexchange.R;
import com.example.huazi.campusexchange.model.bean.UserBean;
import com.example.huazi.campusexchange.presenters.LoginPresenterImpl;
import com.example.huazi.campusexchange.ui.base.BaseActivity;
import com.example.huazi.campusexchange.ui.view.CleanEditText;
import com.example.huazi.campusexchange.utils.BlurUtil;
import com.example.huazi.campusexchange.utils.DateUtil;
import com.example.huazi.campusexchange.utils.RegexUtil;
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

   /* private void clickLogin() {
        final String account = et_email_phone.getText().toString();
        String password = et_password.getText().toString();
        if (checkInput(account, password)) {

            mDialog = new ProgressDialog(this);
            mDialog.setMessage("正在登陆，请稍后...");
            mDialog.show();

            EMClient.getInstance().login(account, password, new EMCallBack() {
                @Override
                public void onSuccess() {
                    mDialog.dismiss();
                    //临时保存登录信息
                    saveLogin(account);
                    // 加载所有会话到内存
                    EMClient.getInstance().chatManager().loadAllConversations();
                    // 加载所有群组到内存，如果使用了群组的话
                    // EMClient.getInstance().groupManager().loadAllGroups();
                    startActivity(new Intent(getContext(), MainActivity.class));
                    finish();
                }

                @Override
                public void onError(final int i, final String s) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                            Log.d("Login", "登录失败 Error code:" + i + ", message:" + s);
                            *//**
                             * 关于错误码可以参考官方api详细说明
                             * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                             *//*
                            switch (i) {
                                // 网络异常 2
                                case EMError.NETWORK_ERROR:
                                    showToast("网络错误 code: " + i + ", message:" + s);
                                    break;
                                // 无效的用户名 101
                                case EMError.INVALID_USER_NAME:
                                    showToast("无效的用户名 code: " + i + ", message:" + s);
                                    break;
                                // 无效的密码 102
                                case EMError.INVALID_PASSWORD:
                                    showToast("无效的密码 code: " + i + ", message:" + s);
                                    break;
                                // 用户认证失败，用户名或密码错误 202
                                case EMError.USER_AUTHENTICATION_FAILED:
                                    showToast("用户认证失败，用户名或密码错误 code: " + i + ", message:" + s);
                                    break;
                                // 用户不存在 204
                                case EMError.USER_NOT_FOUND:
                                    showToast("用户不存在 code: " + i + ", message:" + s);
                                    break;
                                // 无法访问到服务器 300
                                case EMError.SERVER_NOT_REACHABLE:
                                    showToast("无法访问到服务器 code: " + i + ", message:" + s);
                                    break;
                                // 等待服务器响应超时 301
                                case EMError.SERVER_TIMEOUT:
                                    showToast("等待服务器响应超时 code: " + i + ", message:" + s);
                                    break;
                                // 服务器繁忙 302
                                case EMError.SERVER_BUSY:
                                    showToast("服务器繁忙 code: " + i + ", message:" + s);
                                    break;
                                // 未知 Server 异常 303 一般断网会出现这个错误
                                case EMError.SERVER_UNKNOWN_ERROR:
                                    showToast("未知的服务器异常 code: " + i + ", message:" + s);
                                    break;
                                default:
                                    showToast("ml_sign_in_failed code: " + i + ", message:" + s);
                                    break;
                            }
                        }
                    });
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }
    }*/

    /**
     * 登录用户的信息
     * @param account
     */
   /* private void saveLogin(String account) {
        boolean isDBExist = !DataSupport.limit(1).find(UserDB.class).isEmpty();
        if (isDBExist) {
            userList = DataSupport.where("tel = ?", account).find(UserDB.class);
            if (!userList.isEmpty()) {
                SharedPreferences.Editor editor = getContext().getSharedPreferences("Login", MODE_PRIVATE).edit();
                editor.putInt("id", userList.get(0).getId());
                editor.putString("name", userList.get(0).getName());
                editor.putString("sex", userList.get(0).getSex());
                editor.putString("school", userList.get(0).getSchool());
                editor.putString("faculty", userList.get(0).getFaculty());
                editor.putString("major", userList.get(0).getMajor());
                editor.putString("education", userList.get(0).getEducation());
                editor.putString("e_mail", userList.get(0).getE_mail());
                editor.putString("tel", userList.get(0).getTel());
                editor.putString("enrollment_Year", userList.get(0).getEnrollment_Year());
                editor.putString("login_time", DateUtil.textForNow2());
                editor.commit();
            }
        }
    }*/

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
    /*public boolean checkInput(String account, String password) {
        // 账号为空时提示
        if (account == null || account.trim().equals("")) {
            Toast.makeText(this, R.string.tip_account_empty, Toast.LENGTH_LONG)
                    .show();
        } else {
            // 账号不匹配手机号格式（11位数字且以1开头）
            if ( !RegexUtil.checkMobile(account)) {
                Toast.makeText(this, R.string.tip_account_regex_not_right,
                        Toast.LENGTH_LONG).show();
            } else if (password == null || password.trim().equals("")) {
                Toast.makeText(this, R.string.tip_password_can_not_be_empty,
                        Toast.LENGTH_LONG).show();
            } else {
                return true;
            }
        }

        return false;
    }*/

}
