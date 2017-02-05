package net.arvin.afbaselibrary.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by arvin on 2016/2/2 16:38.
 * 文件相关工具方法
 */
@SuppressWarnings("all")
public class FileUtil {

    /**
     * 删除指定目录下文件及目录
     *
     * @param filepath
     */
    public static void deleteFolderFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                        file.delete();
                        return;
                    }
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath());
                    }
                } else {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件夹大小
     * Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
     * Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    public static String getCacheDir(Context context) {
        String cacheDir = "";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            try {
                cacheDir = context.getExternalCacheDir().getAbsolutePath();
            } catch (NullPointerException e) {
                cacheDir = context.getCacheDir().getAbsolutePath();
            }
        } else {
            cacheDir = context.getCacheDir().getAbsolutePath();
        }
        return cacheDir;
    }

    public static String getEnvironmentDir(Context context) {
        String cacheDir = "";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            cacheDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return cacheDir;
    }

}
