package net.tatans.coeus.network.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * @deprecated Use the new {@link TatansCrashHandle}
 * UncaughtException������,��������Uncaught�쳣��ʱ��,�и������ӹܳ���,����¼���ʹ��󱨸�.
 * @author Yuliang
 *
 */
@SuppressLint("SdCardPath")
public class CrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandler";
	public static String CRASH_PATH = "/tatans/crash/";
	// CrashHandler ʵ��
	private static CrashHandler INSTANCE = new CrashHandler();
	// ����� Context ����
	private Context mContext;

	// ϵͳĬ�ϵ� UncaughtException ������
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	// �����洢�豸��Ϣ���쳣��Ϣ
	private Map<String, String> infos = new HashMap<String, String>();

	// ������ʾToast�е���Ϣ
	private String error = "��Ӧ�ó���һ��С���⣬���Եȡ�";

	private static final Map<String, String> regexMap = new HashMap<String, String>();

	// ���ڸ�ʽ������,��Ϊ��־�ļ�����һ����
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
			Locale.CHINA);


	/** ��ֻ֤��һ�� CrashHandler ʵ�� */
	CrashHandler() {
		//
	}

	/** ��ȡ CrashHandler ʵ�� ,����ģʽ */
	public static CrashHandler getInstance() {
		// initMap();
		return INSTANCE;
	}

	/**
	 * ��ʼ��
	 * 
	 * @param context
	 */
	public void init() {
		mContext = TatansApplication.getContext();
		// ��ȡϵͳĬ�ϵ� UncaughtException ������
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

		// ���ø� CrashHandler Ϊ�����Ĭ�ϴ�����
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	/**
	 * ��ʼ��
	 * 
	 * @param sDir 
	 * 	Ĭ�ϱ����·��
	 */
	public void init(String sDir) {
		CRASH_PATH="/"+sDir+"/crash/";
		init();
	}
	/**
	 * ��ʼ��
	 * @param sDir 
	 * 	Ĭ�ϱ����·��
	 */
	public void initTatans(String sDir) {
		CRASH_PATH="/tatans/"+sDir+"/crash/";
		init();
	}
	/**
	 * �� UncaughtException ����ʱ��ת��ú���������
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// ����û�û�д�������ϵͳĬ�ϵ��쳣������������
		//	mDefaultHandler.uncaughtException(thread, ex);
			Log.d("TEST", "defalut");
		} else {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Log.e(TAG, "error : ", e);
			}
			// �˳�����
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	/**
	 * �Զ���������ռ�������Ϣ�����ʹ��󱨸�Ȳ������ڴ����
	 * 
	 * @param ex
	 * @return true����������˸��쳣��Ϣ�����򷵻� false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}

		// �ռ��豸������Ϣ
		// collectDeviceInfo(mContext);
		// ������־�ļ�
		saveCrashInfo2File(ex);
		// ʹ�� Toast ����ʾ�쳣��Ϣ
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();
		return true;
	}

	/**
	 * �ռ��豸������Ϣ
	 * 
	 * @param ctx
	 */
	private String collectDeviceInfo(Context ctx, boolean flag) {
		String string_buf;
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);

			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
			infos.put("versionName", "unknow");
			infos.put("versionCode", "unknow");
		}

		string_buf = "versionName:" + infos.get("versionName")
				+ ", versionCode:" + infos.get("versionCode") + '\n';

		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.e(TAG, field.getName() + " : " + field.get(null));
				if (flag)
					string_buf += field.getName() + ": "
							+ infos.get(field.getName()) + '\n';
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
		return string_buf;
	}

	/**
	 * ���������Ϣ���ļ��� *
	 * 
	 * @param ex
	 * @return �����ļ�����,���ڽ��ļ����͵�������
	 */
	private String saveCrashInfo2File(Throwable ex) {
		StringBuffer sb = getTraceInfo(ex);
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();

		String result = writer.toString();
		sb.append(result);
		try {
			long timestamp = System.currentTimeMillis();
			String time = formatter.format(new Date());
			String fileName =  time + "-" + timestamp + ".log";

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String path = Environment.getExternalStorageDirectory()
						+ CRASH_PATH;
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(path+fileName);
				fos.write(sb.toString().getBytes());
				fos.close();
			}

			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}

		return null;
	}

	/**
	 * �����쳣��Ϣ
	 * 
	 * @param e
	 * @return
	 */
	private StringBuffer getTraceInfo(Throwable e) {
		StringBuffer sb = new StringBuffer();

		Throwable ex = e.getCause() == null ? e : e.getCause();
		StackTraceElement[] stacks = ex.getStackTrace();
		sb.append(collectDeviceInfo(mContext, false));
		for (int i = 0; i < stacks.length; i++) {
			if (i == 0) {
				setError(ex.toString());
			}
			sb.append("class: ").append(stacks[i].getClassName())
					.append("; method: ").append(stacks[i].getMethodName())
					.append("; line: ").append(stacks[i].getLineNumber())
					.append(";  Exception: ").append(ex.toString() + "\n");
		}
		Log.e(TAG, sb.toString());
		return sb;
	}

	/**
	 * ���ô������ʾ��
	 * 
	 * @param e
	 */
	public void setError(String strErr) {
		Pattern pattern;
		Matcher matcher;
		for (Entry<String, String> m : regexMap.entrySet()) {
			Log.e(TAG, strErr + "key:" + m.getKey() + "; value:" + m.getValue());
			pattern = Pattern.compile(m.getKey());
			matcher = pattern.matcher(strErr);
			if (matcher.matches()) {
				error = m.getValue();
				break;
			}
		}
	}

}