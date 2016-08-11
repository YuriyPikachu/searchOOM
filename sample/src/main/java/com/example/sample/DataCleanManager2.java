package com.example.sample;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Yuriy on 2016/8/11.
 */

public class DataCleanManager2 {
    public static final String FILE_NAME = Environment.getExternalStorageDirectory().toString()  + "/tatans/data";
    /** * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context */
    public static void cleanInternalCache(String packageName) {
        deleteFilesByDirectory(new File(FILE_NAME +packageName+  "/cache"));
    }
    /** * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context */
    public static void cleanDatabases(String packageName) {
        deleteFilesByDirectory(new File(FILE_NAME +packageName+ "/databases"));
    }
    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
     * context
     */
    public static void cleanSharedPreference(String packageName) {
        deleteFilesByDirectory(new File(FILE_NAME +packageName+  "/shared_prefs"));
    }

    /** * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context */
    public static void cleanFiles(String packageName) {
        deleteFilesByDirectory(new File(FILE_NAME +packageName+  "/files"));
    }
    /** * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }
    /** * 清除本应用所有的数据 * * @param context * @param filepath */
    public static void cleanApplicationData(String packageNam) {
        cleanInternalCache(packageNam);
        cleanDatabases(packageNam);
        cleanSharedPreference(packageNam);
        cleanFiles(packageNam);
       /* for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }*/
    }
    /** * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                Log.d("qqqq","filename:"+item.getName());
                item.delete();
            }
        }
    }
}


