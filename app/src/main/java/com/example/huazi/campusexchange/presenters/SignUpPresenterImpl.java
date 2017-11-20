package com.example.huazi.campusexchange.presenters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.huazi.campusexchange.R;
import com.example.huazi.campusexchange.ui.activitys.entrance.SignUpActivity;
import com.example.huazi.campusexchange.utils.DensityUtil;

/**
 * Created by huazi on 2017/11/9.
 * Email:wanghuazhi_beijing@163.com
 */

public class SignUpPresenterImpl implements SignUpPresenter {

    private final SignUpActivity context;

    public SignUpPresenterImpl(SignUpActivity context) {
        this.context = context;

    }

    @Override
    public void showServiceDialog() {
       // 内容
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(context);
//        normalDialog.setIcon(R.mipmap.ic_logo);
        normalDialog.setTitle("我是一个普通Dialog");
        normalDialog.setMessage("安全协议:\nfafasdfasfasfasfjasjfkasjfkashfkasjhfkashfkasjsakfhaskfhaskfhkasfhdsakhfkasdhfdksahfsadkfhsadkfhsadklsdjfldasjklfasjflasjflasjflasjflkasjfaslfjkaslfjaslkjfsakljfkasljfkasljfklasjfalskfjaslkfjsalfsakjf");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
       /* Window dialogWindow = context.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);*/
        normalDialog.show();
    }

}
