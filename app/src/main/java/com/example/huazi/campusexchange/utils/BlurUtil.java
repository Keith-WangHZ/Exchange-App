package com.example.huazi.campusexchange.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import static android.R.attr.radius;

/**
 * Created by huazi on 2017/11/7.
 * Email:wanghuazhi_beijing@163.com
 */

public class BlurUtil {

    public static Bitmap bitMapBlur(Drawable drawable, float scaleWidth, float scaleHeight, float blurRadius) {
        Bitmap scaledBitmap = drawableToBitmap(drawable);
        scaledBitmap = Bitmap.createScaledBitmap(scaledBitmap,
                (int) (scaledBitmap.getWidth() / scaleWidth),
                (int) (scaledBitmap.getHeight() / scaleHeight),
                false);
        scaledBitmap = FastBlur.doBlur(scaledBitmap, (int) blurRadius, true);
        return scaledBitmap;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}
