package net.tatans.coeus.network.utils;

import java.io.File;

import android.os.Environment;

public class TatansDirPath {
	/**
	 * 获取或创建Cache目录
	 * 
	 * @param sDir
	 *         文件保存路径
	 * @param  apkName
	 * 		保存文件的名字
	 */			
	public static String getMyCacheDir(String sDir,String apkName) {
		String dir;

		// 保证目录名称正确
		if (sDir != null) {
			if (!sDir.equals("")) {
				if (!sDir.endsWith("/")) {
					sDir = sDir + "/";
				}
			}
		}

		String joyrun_default = "/tatans/";

		if (isSDCardExist()) {
			dir = Environment.getExternalStorageDirectory().toString() + joyrun_default + sDir;
		} else {
			dir = Environment.getDownloadCacheDirectory().toString() + joyrun_default + sDir;
		}

		File f = new File(dir);
		if (!f.exists()) {
			f.mkdirs();
		}
		return dir+apkName;
	}
	/**
	 * 获取或创建Cache目录
	 *
	 * @param sDir
	 *         文件保存路径
	 */
	public static String getMyCacheDir(String sDir) {
		String dir;

		// 保证目录名称正确
		if (sDir != null) {
			if (!sDir.equals("")) {
				if (!sDir.endsWith("/")) {
					sDir = sDir + "/";
				}
			}
		}
		if (isSDCardExist()) {
			dir = Environment.getExternalStorageDirectory().toString()  + "/"+sDir;
		} else {
			dir = Environment.getDownloadCacheDirectory().toString()  + "/"+sDir;
		}

		File f = new File(dir);
		if (!f.exists()) {
			f.mkdirs();
		}
		return dir;
	}
	public static boolean isSDCardExist() {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return false;
		return true;
	}
}
