package com.qvdev.apps.twitflick.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.squareup.picasso.Transformation;

/**
 * Created by QVDev on 10/6/13.
 */
public class HudRectTransformation implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int targetWidth = source.getWidth();
        int targetHeight = source.getHeight();
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();

        path.moveTo(0, 0);

        int cornerInset = 15;

        path.lineTo(targetWidth - cornerInset, 0);
        path.lineTo(targetWidth, cornerInset);

        path.lineTo(targetWidth, targetHeight);

        path.lineTo(cornerInset, targetHeight);
        path.lineTo(0, targetHeight - cornerInset);

        path.lineTo(0,0);
        path.close();

        canvas.clipPath(path);
        Bitmap sourceBitmap = source;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth,
                        targetHeight), paint);

        if (targetBitmap != source) {
            source.recycle();
        }

        return targetBitmap;
    }

    @Override
    public String key() {
        return "square()";
    }
}