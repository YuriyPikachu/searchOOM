package net.tatans.coeus.network.callback;

import net.tatans.coeus.network.josn.analysis.JsonDeepUtil;
import net.tatans.coeus.network.util.HttpProces;

/**
 * 
 * @author ����
 *
 * @param <T> Ŀǰ����֧�� String,File, 
 * �Ժ���չ��JSONObject,Bitmap,byte[],XmlDom
 */
public abstract class HttpRequestCallBack<T> {
	
	private boolean progress = true;
	private int rate = 1000 * 1;//ÿ��
	private JsonDeepUtil jdu = new JsonDeepUtil();
//	private Class<T> type;
//	
//	public AjaxCallBack(Class<T> clazz) {
//		this.type = clazz;
//	}
	private Class<?> c;
	public Class<?> getJsonAnaylsisClass(){
		return c;
	}
	
	public boolean isProgress() {
		return progress;
	}
	
	public int getRate() {
		return rate;
	}
	
	/**
	 * ���ý���,����ֻ��������������Ժ�onLoading������Ч��
	 * @param progress �Ƿ����ý�����ʾ
	 * @param rate ���ȸ���Ƶ��
	 */
	public HttpRequestCallBack<T> progress(boolean progress , int rate) {
		this.progress = progress;
		this.rate = rate;
		return this;
	}
	
	public void onStart(){HttpProces.startHttp();};
	/**
	 * onLoading������Чprogress
	 * @param count
	 * @param current
	 */
	public void onLoading(long count,long current){};
	public void onSuccessSuper(T t){
		HttpProces.successHttp();
		onSuccess(t);
	}
	
	public void onSuccess(T t){
		c= getJsonAnaylsisClass();
		if(t instanceof String&&c!=null){
			try {
				Object obj = jdu.getEntityJson((String)t, c);
				onSuccessAnalysis(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	public void onSuccessAnalysis(Object obj) {
		
	}

	public void onFailure(Throwable t,String strMsg){HttpProces.failHttp();};
	
	
}
