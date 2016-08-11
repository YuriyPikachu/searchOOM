package com.example.sample;
import java.io.File;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * Created by Yuriy on 2016/8/11.
 */

public class DataCleanManager {
    public static final String FILE_NAME = Environment.getExternalStorageDirectory().toString()  + "/tatans/data/";
    /** * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context */
    public static void cleanInternalCache(String packageName) {
        File directory =new File(FILE_NAME +packageName+  "/cache");
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }
    public static void cleanUserData(String packageName) {
        File directory =new File(FILE_NAME +packageName);
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                Log.d("qqqq","filename:"+item.getName());
                if (!"lib".equals(item.getName())){
                    item.delete();
                }
            }
        }
    }
}


