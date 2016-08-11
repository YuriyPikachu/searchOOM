package com.example.sample;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Yuriy on 2016/8/11.
 */

public class DataCleanManager2 {
    public static final String FILE_NAME = Environment.getExternalStorageDirectory().toString()  + "/tatans/data";
    /** * �����Ӧ���ڲ�����(/data/data/com.xxx.xxx/cache) * * @param context */
    public static void cleanInternalCache(String packageName) {
        deleteFilesByDirectory(new File(FILE_NAME +packageName+  "/cache"));
    }
    /** * �����Ӧ���������ݿ�(/data/data/com.xxx.xxx/databases) * * @param context */
    public static void cleanDatabases(String packageName) {
        deleteFilesByDirectory(new File(FILE_NAME +packageName+ "/databases"));
    }
    /**
     * * �����Ӧ��SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
     * context
     */
    public static void cleanSharedPreference(String packageName) {
        deleteFilesByDirectory(new File(FILE_NAME +packageName+  "/shared_prefs"));
    }

    /** * ���/data/data/com.xxx.xxx/files�µ����� * * @param context */
    public static void cleanFiles(String packageName) {
        deleteFilesByDirectory(new File(FILE_NAME +packageName+  "/files"));
    }
    /** * ����Զ���·���µ��ļ���ʹ����С�ģ��벻Ҫ��ɾ������ֻ֧��Ŀ¼�µ��ļ�ɾ�� * * @param filePath */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }
    /** * �����Ӧ�����е����� * * @param context * @param filepath */
    public static void cleanApplicationData(String packageNam) {
        cleanInternalCache(packageNam);
        cleanDatabases(packageNam);
        cleanSharedPreference(packageNam);
        cleanFiles(packageNam);
       /* for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }*/
    }
    /** * ɾ������ ����ֻ��ɾ��ĳ���ļ����µ��ļ�����������directory�Ǹ��ļ������������� * * @param directory */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                Log.d("qqqq","filename:"+item.getName());
                item.delete();
            }
        }
    }
}


