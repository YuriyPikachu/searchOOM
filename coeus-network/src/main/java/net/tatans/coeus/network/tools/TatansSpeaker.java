package net.tatans.coeus.network.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

public class TatansSpeaker {
	private static String TAG = TatansSpeaker.class.getSimpleName(); 	
	private TatansSpeakerCallback mSpeakerCallback;
	// �����ϳɶ���
	private SpeechSynthesizer mTts;
	// ���Ž���
	private int mPercentForPlaying = 0;
	// �������
	private int mPercentForBuffering = 0;
	private SharedPreferences mSharedPreferences;
	private Context mContext;

	private static final String PREFER_NAME = "com.iflytek.setting";
	// ��ǰ�װ������
	//ApkInstaller mInstaller ;
	
	private static TatansSpeaker instance = null;
	/**
	 * ����ģʽ
	 * 
	 */
	protected static TatansSpeaker getInstance(Context context) {
		if (instance == null) {
			synchronized (TatansSpeaker.class) {
				if (instance == null) {
					instance = new TatansSpeaker(context);
				}
			}
		}
		return instance;
	}
	public static TatansSpeaker create(){
		if (instance == null) {
			synchronized (TatansSpeaker.class) {
				if (instance == null) {
					instance = new TatansSpeaker(TatansApplication.getContext());
				}
			}
		}
		return instance;
	}
	private TatansSpeaker(Context con) {
		mContext=con;
		//����ж�conΪ�յġ�
		// ��ʼ���ϳɶ���
		initLocalSpeech();
	}
	/**
	 * ��ʼ��������
	 */
	private InitListener mTtsInitListener = new InitListener() {
		@Override
		public void onInit(int code) {
			if (code != ErrorCode.SUCCESS) {
				Log.e(TAG, "��ʼ��ʧ��,�����룺"+code);
        	} else {
				// ��ʼ���ɹ���֮����Ե���startSpeaking����
        		// ע���еĿ�������onCreate�����д�����ϳɶ���֮�����Ͼ͵���startSpeaking���кϳɣ�
        		// ��ȷ�������ǽ�onCreate�е�startSpeaking������������
			}		
		}
	};
	/**
	 * �ϳɻص�������
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener() {
		
		@Override
		public void onSpeakBegin() {
			Log.d(TAG,"��ʼ����");
			mSpeakerCallback.onSpeakBegin();
		}

		@Override
		public void onSpeakPaused() {
			Log.d(TAG,"��ͣ����");
			mSpeakerCallback.onSpeakPaused();
		}

		@Override
		public void onSpeakResumed() {
			Log.d(TAG,"��������");
			mSpeakerCallback.onSpeakResumed();
		}

		@Override
		public void onBufferProgress(int percent, int beginPos, int endPos,
				String info) {
			// �ϳɽ���
			mPercentForBuffering = percent;
		//	Log.i(TAG,"BufferPro�������Ϊ"+mPercentForBuffering+"�����Ž���Ϊ"+mPercentForPlaying+"info:"+info);
		}

		@Override
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
			// ���Ž���
			mPercentForPlaying = percent;
			Log.d(TAG,"SpeakPro�������Ϊ"+mPercentForBuffering+"�����Ž���Ϊ"+mPercentForPlaying);
			Log.d(TAG,"��ʼ�ڵ�:"+beginPos+"�������ڵ�:"+endPos);
			mSpeakerCallback.onSpeakProgress(percent, beginPos, endPos);
		}

		@Override
		public void onCompleted(SpeechError error) {
			if (error == null) {
				mSpeakerCallback.onCompleted();
				Log.d(TAG,"�������");
			} else if (error != null) {
				mSpeakerCallback.onCompletedError();
				Log.d(TAG,error.getPlainDescription(true));
			}
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// ���´������ڻ�ȡ���ƶ˵ĻỰid����ҵ�����ʱ���Ựid�ṩ������֧����Ա�������ڲ�ѯ�Ự��־����λ����ԭ��
			// ��ʹ�ñ����������ỰidΪnull
			//	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
			//		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
			//		Log.d(TAG, "session id =" + sid);
			//	}
		}
	};
	private void initLocalSpeech(){
		SpeechUtility.createUtility(mContext, SpeechConstant.APPID + "="
				+ "54013ebb");
		mTts = SpeechSynthesizer.createSynthesizer(mContext, mTtsInitListener);
		mSharedPreferences = mContext.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
		Log.d(TAG, "Initspeed:"+mTts.getParameter(SpeechConstant.SPEED));
		// TODO Auto-generated constructor stub
		//mInstaller = new ApkInstaller((Activity)mContext);
		if (!SpeechUtility.getUtility().checkServiceInstalled()) {
			Log.d(TAG, "��⵽��δ��װ��ǣ�");
			//mInstaller.install();
		}
	}
	/**
	 * ��������
	 * @param
	 * @return 
	 */
	private void setParam(){
		// ��ղ���
		mTts.setParameter(SpeechConstant.PARAMS, null);
		// ���ݺϳ�����������Ӧ����
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
			// ���ñ��غϳɷ����� voicerΪ�գ�Ĭ��ͨ����ǽ���ָ�������ˡ�
		mTts.setParameter(SpeechConstant.VOICE_NAME, "");
			/**
			 * TODO ���غϳɲ��������١�������������Ĭ��ʹ���������
			 * �����������Զ����������ο����ߺϳɲ�������
			 */
		//���ò�������Ƶ������
		mTts.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "3"));
		// ���ò��źϳ���Ƶ������ֲ��ţ�Ĭ��Ϊtrue
		mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
		
		// ������Ƶ����·����������Ƶ��ʽ֧��pcm��wav������·��Ϊsd����ע��WRITE_EXTERNAL_STORAGEȨ��
		// ע��AUDIO_FORMAT���������Ҫ���°汾������Ч
		//mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
		//mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
	}
	
	public void speech(String str){
		speech(str,null);
	}
	public void speech(String str,int speed){
		speech(str,speed,null);
	}
	public void speech(String str,int speed,TatansSpeakerCallback speakerCallback){
		setParam();
		//FlowerCollector.onEvent((Activity)mContext, "tts_play");
		if(speakerCallback==null){
			mTtsListener=null;
		}
		speed(speed);
		mSpeakerCallback = speakerCallback;
		int code = mTts.startSpeaking(str, mTtsListener);
//		/** 
//		 * ֻ������Ƶ�����в��Žӿ�,���ô˽ӿ���ע��startSpeaking�ӿ�
//		 * text:Ҫ�ϳɵ��ı���uri:��Ҫ�������Ƶȫ·����listener:�ص��ӿ�
//		*/
//		String path = Environment.getExternalStorageDirectory()+"/tts.pcm";
//		int code = mTts.synthesizeToUri(text, path, mTtsListener);
		Log.d(TAG,"�����ϳ�:"+str+",������: " + code);
		if (code != ErrorCode.SUCCESS) {
			if(code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED){
				//δ��װ����ת����ʾ��װҳ��
				//mInstaller.install();
			}else {
				Log.d(TAG,"�����ϳ�ʧ��,������: " + code);	
			}
		}
	}
	public void  speech(String str,TatansSpeakerCallback speakerCallback){
		speech(str,50, speakerCallback);
	}
	public void stop(){
		Log.d(TAG, "stop��");
		mTts.stopSpeaking();
	}
	public void pause(){
		Log.d(TAG, "pause��");
		mTts.pauseSpeaking();
	}
	public void resume(){
		Log.d(TAG, "resume��");
		mTts.resumeSpeaking();
	}
	public void speed(int speed){
		Log.d(TAG, "��������:"+mTts.getParameter(SpeechConstant.SPEED));
		mTts.setParameter(SpeechConstant.SPEED,String.valueOf(speed));
	}
	public void destroy(){
		mTts.destroy();
	}
}
