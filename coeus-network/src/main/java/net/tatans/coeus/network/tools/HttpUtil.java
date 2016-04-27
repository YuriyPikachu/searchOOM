package net.tatans.coeus.network.tools;

import java.io.File;
import java.util.Map;

import net.tatans.coeus.network.util.HttpProces;
import net.tatans.coeus.network.util.NetWork;
import android.app.Activity;
import android.content.Context;
/**
 * @author ���� 
 * @time 2014-11-21 
 * @version 2.0
 */
public class HttpUtil{
	 
	/**
	 * 
	 * @param url
	 *            ���������URL
	 * @param context
	 *            ��ǰContext������
	 * @return ��������Ӧ�ַ���
	 * @throws Exception
	 */
	public static String postRequest(String url,Context context){
		HttpProces.bSound=true;
		NetWork nw = new NetWork();
		return nw.httpRequest(url, null, context,"no","post");
	}
	public static String postCasRequest(String url,Activity context){
		HttpProces.bSound=true;
		NetWork nw = new NetWork();
		return nw.httpRequest(url, null, context,"yes","post");
	}
	public static String postServiceCasRequest(String url,Context context){
		HttpProces.bSound=true;
		NetWork nw = new NetWork();
		return nw.httpRequest(url, null, context,"yes","post");
	}
	/**
	 * 
	 * @param url
	 *            ���������URL
	 * @param rawParams
	 *            �������
	 * @param context
	 *            ��ǰContext������
	 * @return ��������Ӧ�ַ���
	 * @throws Exception
	 */
	public static String postRequest(String url, Map<String ,String> rawParams,Context context){
		HttpProces.bSound=true;
		NetWork nw = new NetWork();
		return nw.httpRequest(url, rawParams, context,"no","post");
	}
	public static String postCasRequest(String url,Map<String ,String> rawParams,Activity context){
		HttpProces.bSound=true;
		NetWork nw = new NetWork();
		return nw.httpRequest(url, rawParams, context,"yes","post");
	}
	public static String postServiceCasRequest(String url,Map<String ,String> rawParams,Context context){
		HttpProces.bSound=true;
		NetWork nw = new NetWork();
		return nw.httpRequest(url, rawParams, context,"yes","post");
	}
	
	public static String postCasRequest(String url,Map<String ,String> rawParams,Activity context,Boolean bFlag){
		HttpProces.bSound=false;
		return postCasRequest( url, rawParams,context);
	}
	/**
	 * 
	 * @param url
	 *            ���������URL
	 * @param context
	 *            ��ǰContext������
	 * @return ��������Ӧ�ַ���
	 * @throws Exception
	 */
	public static String getRequest(String url, Context context){
		HttpProces.bSound=true;
		NetWork nw = new NetWork();
		return nw.httpRequest(url, null, context,"no","get");
	}
	public static String getCasRequest(String url,Activity context){
		HttpProces.bSound=true;
		NetWork nw = new NetWork();
		return nw.httpRequest(url, null, context,"yes","get");
	}
	public static String getServiceCasRequest(String url,Context context){
		NetWork nw = new NetWork();
		return nw.httpRequest(url, null, context,"yes","get");
	}
	public static String getServiceCasRequest(String url,Context context,Boolean bflag){
		HttpProces.bSound=false;
		return getServiceCasRequest( url,context);
	}
	public static File downLoadFile(File file,String url,Map<String ,String> rawParams, Context context){
		HttpProces.bSound=true;
		NetWork nw = new NetWork();
		return nw.downloadFile(file, url, rawParams, context, "no");
	}
}
