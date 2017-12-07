package com.example.huazi.campusexchange.ui.activitys.entrance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.huazi.campusexchange.R;
import com.example.huazi.campusexchange.model.bean.UserBean;
import com.example.huazi.campusexchange.presenters.LoginPresenterImpl;
import com.example.huazi.campusexchange.presenters.SignUpPresenter;
import com.example.huazi.campusexchange.presenters.SignUpPresenterImpl;
import com.example.huazi.campusexchange.ui.base.BaseActivity;
import com.example.huazi.campusexchange.ui.view.CleanEditText;
import com.example.huazi.campusexchange.utils.BlurUtil;
import com.example.huazi.campusexchange.utils.PreferenceManager;
import com.example.huazi.campusexchange.utils.VerifyCodeManager;

import java.util.List;

/**
 * @creation_time: 2017/11/08
 * @author: huazi
 * @e-mail: wanghuazhi_beijing@163.com
 * @describe: 注册界面,一般会使用手机登录，通过获取手机验证码，跟服务器交互完成注册
 */

public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SignupActivity";
    // 界面控件
    private CleanEditText phoneEdit;
    private CleanEditText passwordEdit;
    private CleanEditText verifyCodeEdit;
    private Button getVerifiCodeButton;
    private CleanEditText et_nickname;
    private VerifyCodeManager codeManager;
    private List<UserBean> userList;
    private LinearLayout ll_school;
    private TextView tv_school;
    // 弹出框
    private ProgressDialog mDialog;
    private TextView userRule;
    private SignUpPresenter signUpPresenter;
    private PreferenceManager mPreferenceManager;

    @Override
    protected void onCreate() {
        setContentView(R.layout.activity_signup);
        Drawable mBackground = getDrawable(R.drawable.login_background);
        Bitmap mBitMap = BlurUtil.bitMapBlur(mBackground, 10, 8, 3);
        RelativeLayout loginLayout = (RelativeLayout)findViewById(R.id.layout_root);
        loginLayout.setBackground(new BitmapDrawable(getResources(),mBitMap));
        initViews();
        codeManager = new VerifyCodeManager(this, phoneEdit, getVerifiCodeButton);
        signUpPresenter = new SignUpPresenterImpl(this);
        mPreferenceManager = PreferenceManager.getInstance(this);
    }

    private void initViews() {
        et_nickname = getView(R.id.et_nickname);
        ll_school = getView(R.id.ll_school);
        tv_school = getView(R.id.tv_school);
        getVerifiCodeButton = getView(R.id.btn_send_verifi_code);
        getVerifiCodeButton.setOnClickListener(this);
        phoneEdit = getView(R.id.et_phone);
        phoneEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);// 下一步
        verifyCodeEdit = getView(R.id.et_verifiCode);
        verifyCodeEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);// 下一步
        passwordEdit = getView(R.id.et_password);
        passwordEdit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        passwordEdit.setImeOptions(EditorInfo.IME_ACTION_GO);
        passwordEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // 点击虚拟键盘的done
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO) {
//                    commit();
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_verifi_code:
                // TODO 请求接口发送验证码

                if (codeManager.getVerifyCode(VerifyCodeManager.REGISTER)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToast("收到验证码：1234");
                                    verifyCodeEdit.setText("1234");
                                }
                            });
                        }
                    }).start();
                }

                break;
            case R.id.btn_create_account:
//                commit();
                break;
            case R.id.tv_user_rule:
                signUpPresenter.showServiceDialog();
                break;
            case R.id.choose_school:
                int requestCode = 0;
                Intent intent = new Intent(SignUpActivity.this,ChooseSchool.class);
                startActivityForResult(intent, requestCode);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String change01;
        if(null != data){
            change01 = data.getStringExtra("result");
            mPreferenceManager.setCurrentSchoolname(change01);
        }else{
            change01 = mPreferenceManager.getCurrentSchoolname();
        }
        // 根据上面发送过去的请求吗来区别
        switch (requestCode) {
            case 0:
                tv_school.setText(change01);
                break;
            default:
                break;
        }
    }
}
