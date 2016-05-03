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
	private static Boolean bIsPause ;
	private static Boolean bIsAudioFocus ;
	private TatansSpeakerCallback mSpeakerCallback;
	// 语音合成对象
	private SpeechSynthesizer mTts;
	// 播放进度
	private int mPercentForPlaying = 0;
	// 缓冲进度
	private int mPercentForBuffering = 0;
	private SharedPreferences mSharedPreferences;
	private Context mContext;

	private static final String PREFER_NAME = "com.iflytek.setting";
	// 语记安装助手类
	//ApkInstaller mInstaller ;

	private static TatansSpeaker instance = null;
	/**
	 * 单例模式
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
		//差个判断con为空的。
		// 初始化合成对象
		initLocalSpeech();
	}
	/**
	 * 初始化监听。
	 */
	private InitListener mTtsInitListener = new InitListener() {
		@Override
		public void onInit(int code) {
			if (code != ErrorCode.SUCCESS) {
				Log.e(TAG, "初始化失败,错误码："+code);
			} else {
				// 初始化成功，之后可以调用startSpeaking方法
				// 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
				// 正确的做法是将onCreate中的startSpeaking调用移至这里
			}
		}
	};
	/**
	 * 合成回调监听。
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener() {

		@Override
		public void onSpeakBegin() {
			Log.d(TAG,"onSpeakBegin");
			mSpeakerCallback.onSpeakBegin();
		}

		@Override
		public void onSpeakPaused() {
	//		Log.d(TAG,"onSpeakPaused");
			mSpeakerCallback.onSpeakPaused();
		}

		@Override
		public void onSpeakResumed() {
		//	Log.d(TAG,"onSpeakResumed");
			mSpeakerCallback.onSpeakResumed();
		}

		@Override
		public void onBufferProgress(int percent, int beginPos, int endPos,
									 String info) {
			// 合成进度
			mPercentForBuffering = percent;
			//	Log.i(TAG,"BufferPro缓冲进度为"+mPercentForBuffering+"，播放进度为"+mPercentForPlaying+"info:"+info);
		}

		@Override
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
			// 播放进度
			mPercentForPlaying = percent;
			/*Log.d(TAG,"SpeakPro缓冲进度为"+mPercentForBuffering+"，播放进度为"+mPercentForPlaying);
			Log.d(TAG,"开始节点:"+beginPos+"，结束节点:"+endPos);*/
			mSpeakerCallback.onSpeakProgress(percent, beginPos, endPos);
		}

		@Override
		public void onCompleted(SpeechError error) {
			if (error == null) {
				mSpeakerCallback.onCompleted();
				Log.d(TAG,"onCompleted");
			} else if (error != null) {
				mSpeakerCallback.onCompletedError();
				Log.d(TAG,error.getPlainDescription(true));
			}
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
			// 若使用本地能力，会话id为null
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
		// TODO Auto-generated constructor stub
		//mInstaller = new ApkInstaller((Activity)mContext);
		if (!SpeechUtility.getUtility().checkServiceInstalled()) {
			Log.d(TAG, "检测到您未安装语记！");
			//mInstaller.install();
		}
	}
	/**
	 * 参数设置
	 * @param
	 * @return
	 */
	private void setParam(){
		// 清空参数
		mTts.setParameter(SpeechConstant.PARAMS, null);
		// 根据合成引擎设置相应参数
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
		// 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
		mTts.setParameter(SpeechConstant.VOICE_NAME, "");
		/**
		 * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
		 * 开发者如需自定义参数，请参考在线合成参数设置
		 */
		//设置播放器音频流类型
		mTts.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "3"));
		// 设置播放合成音频打断音乐播放，默认为true
		mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

		// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
		// 注：AUDIO_FORMAT参数语记需要更新版本才能生效
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
		bIsPause=false;
		setParam();
		//FlowerCollector.onEvent((Activity)mContext, "tts_play");
		if(speakerCallback==null){
			mTtsListener=null;
		}
		speed(speed);
		/*if(!bIsAudioFocus){
			Log.d(TAG, "音频焦点2:"+bIsAudioFocus);
			mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS,String.valueOf(bIsAudioFocus));
		}*/
		mSpeakerCallback = speakerCallback;
		int code = mTts.startSpeaking(str, mTtsListener);
//		/**
//		 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
//		 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
//		*/
//		String path = Environment.getExternalStorageDirectory()+"/tts.pcm";
//		int code = mTts.synthesizeToUri(text, path, mTtsListener);
		Log.d(TAG,"语音合成:"+str+",错误码: " + code);
		if (code != ErrorCode.SUCCESS) {
			if(code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED){
				//未安装则跳转到提示安装页面
				//mInstaller.install();
			}else {
				Log.d(TAG,"语音合成失败,错误码: " + code);
			}
		}
	}
	public void  speech(String str,TatansSpeakerCallback speakerCallback){
		speech(str,50, speakerCallback);
	}
	public void stop(){
		Log.d(TAG, "stop！");
		bIsPause=false;
		mTts.stopSpeaking();
	}
	public void pause(){
		Log.d(TAG, "pause！");
		bIsPause=true;
		mTts.pauseSpeaking();
	}
	public void resume(){
		Log.d(TAG, "resume！");
		bIsPause=false;
		mTts.resumeSpeaking();
	}
	public void speed(int speed){
		Log.d(TAG, "现在语速:"+mTts.getParameter(SpeechConstant.SPEED));
		mTts.setParameter(SpeechConstant.SPEED,String.valueOf(speed));
	}
	public void setAudioFoucus(Boolean flag){
		Log.d(TAG, "音频焦点:"+flag);
		bIsAudioFocus=flag;
	}
	public void destroy(){
		mTts.destroy();
	}
	public boolean isStop(){
		return mTts.isSpeaking();
	}
	public boolean isPause(){
		return bIsPause;
	}
}
