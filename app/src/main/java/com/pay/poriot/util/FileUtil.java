package com.pay.poriot.util;

import android.content.Context;
import android.text.ClipboardManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);

        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff = new BufferedOutputStream(output);

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        //刷新此缓冲的输出流
        outBuff.flush();
        IoUtils.closeStream(inBuff);
        IoUtils.closeStream(outBuff);
        IoUtils.closeStream(output);
        IoUtils.closeStream(input);
    }



    public static File getTargetFile(Context context, String url) {

        if (StringUtil.isNullOrEmpty(url)) {
            url = "";
        }
        String fileName = Md5Utils.md5(url);
        File targetFile = new File(StorageUtils.getCacheDirectory(context, "image"), fileName);
        return targetFile;
    }


    public static void copyText(Context context, String content) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content);
    }
}
