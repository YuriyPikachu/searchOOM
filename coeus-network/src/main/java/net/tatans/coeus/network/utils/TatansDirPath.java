package net.tatans.coeus.network.utils;

import java.io.File;

import android.os.Environment;

public class TatansDirPath {
	/**
	 * ��ȡ�򴴽�CacheĿ¼
	 * 
	 * @param sDir
	 *         �ļ�����·��
	 * @param  apkName
	 * 		�����ļ�������
	 */			
	public static String getMyCacheDir(String sDir,String apkName) {
		String dir;

		// ��֤Ŀ¼������ȷ
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
	 * ��ȡ�򴴽�CacheĿ¼
	 *
	 * @param sDir
	 *         �ļ�����·��
	 */
	public static String getMyCacheDir(String sDir) {
		String dir;

		// ��֤Ŀ¼������ȷ
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
