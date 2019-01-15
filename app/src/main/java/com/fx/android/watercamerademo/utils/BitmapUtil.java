package com.fx.android.watercamerademo.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;


/**
 * Created by sreay on 14-10-24.
 */
public class BitmapUtil {

    public static Bitmap rotateAndScale(Bitmap b, int degrees, float maxSideLen, boolean recycle) {
        if (null == b || degrees == 0 && b.getWidth() <= maxSideLen + 10 && b.getHeight() <= maxSideLen + 10) {
            return b;
        }

        Matrix m = new Matrix();
        if (degrees != 0) {
            m.setRotate(degrees);
        }

        try {
            Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
            if (null != b2 && b != b2) {
                if (recycle) {
                    b.recycle();
                }
                b = b2;
            }
        } catch (OutOfMemoryError e) {
        }

        return b;
    }
}
