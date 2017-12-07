package com.example.huazi.campusexchange.ui.activitys.entrance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.huazi.campusexchange.MainActivity;
import com.example.huazi.campusexchange.R;
import com.example.huazi.campusexchange.ui.base.BaseActivity;
import com.example.huazi.campusexchange.utils.BlurUtil;
import com.example.huazi.campusexchange.utils.WindowsUtil;

/**
 * Created by huazi on 2017/11/8.
 * Email:wanghuazhi_beijing@163.com
 */

public class FirstPageActivity extends BaseActivity{

    private TextView newUserTextView;
    private Button loginBtn;
    private Button signupBtn;

    @Override
    protected void onCreate() {
        WindowsUtil.fullScreen(this);
        initLayout(R.layout.first_page);
        Drawable mBackground = getDrawable(R.drawable.login_background);
        Bitmap mBitMap = BlurUtil.bitMapBlur(mBackground, 10, 8, 3);
        RelativeLayout loginLayout = (RelativeLayout)findViewById(R.id.layout_root);
        loginLayout.setBackground(new BitmapDrawable(getResources(),mBitMap));
        initview();
        newUserTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPageActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initview() {
        newUserTextView = (TextView) getView(R.id.new_user);
        loginBtn = (Button)findViewById(R.id.btn_login);
        signupBtn = (Button)findViewById(R.id.btn_signup);
    }
}
