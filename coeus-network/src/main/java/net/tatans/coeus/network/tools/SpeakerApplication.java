package net.tatans.coeus.network.tools;

import android.app.Application;

/**
 * SearchOOM [v 2.0.0]
 * classes : net.tatans.coeus.network.tools.SpeakerApplication
 * yulia create at 2016/4/21 13:10
 */

class SpeakerApplication extends Application{
	private static TatansSpeaker mSpeaker;
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
		if (mSpeaker!=null)
			mSpeaker.stop();
	}

	public static void setAppSpeaker() {
		mSpeaker = TatansSpeaker.create();
	}

	public static void speech(String string){
		mSpeaker.speech(string);
	}
	public static void speech(String str,int speed){
		mSpeaker.speech(str,speed);
	}
	public static void speech(String str,int speed,TatansSpeakerCallback speakerCallback){
		mSpeaker.speech( str, speed, speakerCallback);
	}
	public static void speech(String str,TatansSpeakerCallback speakerCallback){
		mSpeaker.speech( str, speakerCallback);
	}
	public static void stop(){
		mSpeaker.stop();
	}
	public static void pause(){
		mSpeaker.pause();
	}
	public static void resume(){
		mSpeaker.resume();
	}
	public static void speed(int speed){
		mSpeaker.speed(speed);
	}
	public static boolean isStop(){
		return mSpeaker.isStop();
	}
	public static boolean isPause(){
		return mSpeaker.isPause();
	}
	public static void destroy(){
		 mSpeaker.destroy();
	}
	public static void setAudioFoucus(Boolean flag){
		mSpeaker.setAudioFoucus(flag);
	}
}
