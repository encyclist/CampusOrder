package com.erning.common.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by encyc on 2017/11/11.
 */

@SuppressWarnings({"WeakerAccess", "ResultOfMethodCallIgnored"})
public class FileUtil {
    /**
     * 根据uri获取文件
     */
    public static String getRealFilePath(Context context, Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String realPath = null;
        if (scheme == null)
            realPath = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            realPath = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA},
                    null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        realPath = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        if (TextUtils.isEmpty(realPath)) {
            String uriString = uri.toString();
            int index = uriString.lastIndexOf("/");
            String imageName = uriString.substring(index);
            File storageDir;

            storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            File file = new File(storageDir, imageName);
            if (file.exists()) {
                realPath = file.getAbsolutePath();
            } else {
                storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File file1 = new File(storageDir, imageName);
                realPath = file1.getAbsolutePath();
            }
        }
        return realPath;
    }

    /** 保存方法 */
    public static boolean saveBitmap(Bitmap bm,String picName) {
        try {
            File f = new File(picName);
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            if (picName.endsWith(".jpg") || picName.endsWith(".JPG") || picName.endsWith(".jpeg") || picName.endsWith(".JPEG"))
                bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            else
                bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean copyFile(File cacheFile, String localSavePath){
        if (cacheFile == null || localSavePath.isEmpty())
            return false;

        FileInputStream ins = null;
        FileOutputStream out = null;
        try {
            ins = new FileInputStream(cacheFile);
            out = new FileOutputStream(new File(localSavePath));
            byte[] b = new byte[1024];
            int n;
            while((n = ins.read(b))!=-1){
                out.write(b, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e){
            return false;
        }finally {
            try {
                if (ins!=null && out!=null) {
                    ins.close();
                    out.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * 格式化单位
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    // 获取文件夹大小
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File value : fileList) {
                // 如果下面还有文件
                if (value.isDirectory()) {
                    size = size + getFolderSize(value);
                } else {
                    size = size + value.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }
        if (dir != null)
            return dir.delete();
        else
            return false;
    }
}
