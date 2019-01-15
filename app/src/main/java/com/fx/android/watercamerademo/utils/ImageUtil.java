package com.fx.android.watercamerademo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class ImageUtil {


    //保存处理后的图并且传递
    public static boolean saveBitmap(Bitmap bitmap, String path) {
        File file = new File(path);
        Boolean is = false;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            is = bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("PanoCamera", "压缩后大小(KB)" + file.length() / 1024);
        if ((file.length() / 1024) > 400) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;//压缩为原图的1/2
            saveBitmap(BitmapFactory.decodeFile(path, options),path);
        }
        return is;
    }

    /**
     * dip转pix
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


}