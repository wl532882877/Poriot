package com.pay.poriot.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class BitmapUtils {

    public static void saveBitmap2Jpg(Context context, Bitmap bitmap, File photoFile) {
        saveBitmap2Jpg(context, bitmap, photoFile, 90);
    }

    public static void saveBitmap2Jpg(Context context, Bitmap bitmap, File photoFile, int quality) {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(photoFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IoUtils.closeStream(stream);
        }
        scanMedia(context, Uri.decode(photoFile.getAbsolutePath()));
    }


    public static void scanMedia(Context context, String path) {
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        Intent scanFileIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(scanFileIntent);
    }


    public static Bitmap getBitmapFromView(View view) {
        if (view == null) return null;
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), View.MeasureSpec.EXACTLY));
            view.layout((int) view.getX(), (int) view.getY(),
                    (int) view.getX() + view.getMeasuredWidth(), (int) view.getY() + view.getMeasuredWidth() + 1);
        } else {
            view.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        }
        Bitmap b = Bitmap.createBitmap(view.getDrawingCache(),
                0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return b;
    }
}
