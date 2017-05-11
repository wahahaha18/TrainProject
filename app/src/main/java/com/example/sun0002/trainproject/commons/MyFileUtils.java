package com.example.sun0002.trainproject.commons;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 自定义的文件工具类(上传商品图片时,用到)
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class MyFileUtils {
    private static final String TAG = MyFileUtils.class.getName();

    public static String SD_PATH = Environment.getExternalStorageDirectory()
            + "/Photo_Train/";


    public static void saveBitmap(Bitmap bm, int typeNum, String picName) {
        try {
            Log.e(TAG, "saveBitmap: "+isFileExist(typeNum));
            if (!isFileExist(typeNum)) {

                createSDDir(typeNum);
            }
            File f = new File(SD_PATH + typeNum + File.separator, picName + ".JPEG");
            Log.e(TAG, "saveBitmap: " + SD_PATH + typeNum + File.separator);
            Log.e(TAG, "saveBitmap: " + f.exists());
            if (f.exists()) {
                f.delete();
            }
            Log.e(TAG, "saveBitmap: "+f );
            FileOutputStream out = new FileOutputStream(f);
            Log.e(TAG, "saveBitmap: "+out );
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.e(TAG, "saveBitmap: " + f.exists());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBitmap(Bitmap bm, String picName) {
        try {
            if (!isFileExist()) {
                createSDDir();
            }
            File f = new File(SD_PATH, picName + ".JPEG");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public static File createSDDir(int typeNum) {
        File dir = new File(SD_PATH + ""+typeNum+File.separator);
        Log.e(TAG, "createSDDir: " +Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED));
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            System.out.println("createSDDir:" + dir.getAbsolutePath());
            System.out.println("createSDDir:" + dir.mkdir());
        }
        Log.e(TAG, "createSDDir: "+dir );
        return dir;
    }
    @SuppressWarnings("UnusedReturnValue")
    public static File createSDDir() {
        File dir = new File(SD_PATH + "");

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {


            System.out.println("createSDDir:" + dir.getAbsolutePath());
            System.out.println("createSDDir:" + dir.mkdir());
        }

        return dir;
    }
    public static boolean isFileExist(int typeNum) {
        File file = new File(SD_PATH + ""+typeNum+File.separator);
        Log.e(TAG, "isFileExist: "+ file.isFile());
        file.isFile();
        return file.exists();
    }
    public static boolean isFileExist() {

        File file = new File(SD_PATH + "");
        file.isFile();
        return file.exists();
    }

    /*删除指定文件*/
    public static void delFile(int typeNum1,String fileName) {
        File file = new File(SD_PATH +typeNum1+File.separator+ fileName);
        Log.e(TAG, "delFile: "+ file.isFile());
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }
    /*删除指定文件*/
    public static void delFile(String fileName) {
        File file = new File(SD_PATH + fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }

    /*删除缓存文件夹*/
    public static void deleteDir() {
        File dir = new File(SD_PATH);
        if (!dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete();
            else if (file.isDirectory())
                deleteDir();
        }
        dir.delete();
    }

    /*返回文件的(路径+文件名)的字符串集合*/
    @SuppressWarnings("UnusedReturnValue")
    public static ArrayList<String> getAllFilePath() {
        ArrayList<String> list = new ArrayList<>();
        File dir = new File(SD_PATH);
        if (!dir.exists() || !dir.isDirectory())
            return list;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                list.add(SD_PATH + file.getName());
            else if (file.isDirectory())
                getAllFilePath();
        }
        return list;
    }

    public static boolean fileIsExists(String path) {
        try {
            File f = new File(SD_PATH + path);
            if (f.exists()) {
                return true;
            }
        } catch (Exception e) {

            return false;
        }
        return false;
    }

}
