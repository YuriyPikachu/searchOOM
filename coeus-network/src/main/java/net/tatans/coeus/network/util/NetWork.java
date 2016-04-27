package net.tatans.coeus.network.util;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import android.content.Context;
import android.util.Log;

/**
 * @author Yuliang
 * @time 2014-11-21
 * @version 1.0
 */
public class NetWork {
	/**
	 * 
	 * @param url
	 *            ���������URL
	 * @param rawParams
	 *            �������
	 * @param context
	 *            ��ǰContext������
	 * @param oauth
	 *            �Ƿ���Ȩyes or no
	 * @param requestType
	 *            ��������get or post
	 * @return ��������Ӧ�ַ���
	 * @throws Exception
	 */
	public String httpRequest(final String url,final Map<String, String> rawParams, final Context context,final String oauth, final String requestType) {
		HttpCallable<String> httpCallable = new HttpCallable<String>(
				requestType, url, oauth, rawParams, context, null);
		FutureTask<String> task = new FutureTask<String>(httpCallable);
		new Thread(task).start();
		String result = null;
		try {
			result = task.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("NetWork", "NetWork->InterruptedException:"+e.toString());
			HttpProces.failHttp();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HttpProces.failHttp();
			Log.e("NetWork", "NetWork->ExecutionException:"+e.toString());
		} catch (Exception e) {
			HttpProces.failHttp();
			Log.e("NetWork", "NetWork->Exception:"+e.toString());
		}
		return result;
	}

	public File downloadFile(final File file, final String url,final Map<String, String> rawParams, final Context context,final String oauth) {
		File ffile = null;
		HttpCallable<File> httpCallable = new HttpCallable<File>("file", url,
				oauth, rawParams, context, file);
		FutureTask<File> task = new FutureTask<File>(httpCallable);
		new Thread(task).start();
		try {
			ffile = task.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HttpProces.failHttp();
			Log.e("NetWork", "NetWork->InterruptedException:"+e.toString());
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HttpProces.failHttp();
			Log.e("NetWork", "NetWork->ExecutionException:"+e.toString());
		}catch (Exception e) {
			HttpProces.failHttp();
			Log.e("NetWork", "NetWork->Exception:"+e.toString());
		}
		return ffile;
	}
}
