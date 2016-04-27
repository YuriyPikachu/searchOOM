package net.tatans.coeus.network.util;

import java.util.HashMap;

import net.tatans.coeus.network.R;
import net.tatans.coeus.network.tools.SoundPoolUtil;
import net.tatans.coeus.network.tools.TatansToast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

/**
 * @author Yuliang
 * @time 2014-11-21
 * @version 1.0
 */
public class HttpProces {
	//private static SoundPoolUtil soundPool;
	public static Boolean bSound = true;
	@SuppressLint("UseSparseArrays")
	public static void initHttp(Context context) {
		Log.d("HttpProces", "initSoundPlay");
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
//		map.put(1, R.raw.basev2_sound_msg2);
//		map.put(2, R.raw.fail);
//		map.put(3, R.raw.basev2_sound_download0);
//		soundPool = new SoundPoolUtil(context, map);
	}

	public static void startHttp() {
		Log.d("HttpProces", "startHttpSound");
		if (bSound) {
			try {
	//			soundPool.soundPlay(3, -1);
			} catch (Exception e) {
				Log.d("HttpProces", "VoiceException" + e.toString());
			}
		}
	}

	public static void successHttp() {
		Log.d("HttpProces", "successHttpSound");
		if (bSound) {
			try {
//				soundPool.stopAll();
//				soundPool.soundPlay(1);
			} catch (Exception e) {
				Log.d("HttpProces", "VoiceException" + e.toString());
			}
		}
	}

	public static void failHttp() {
		Log.d("HttpProces", "failHttpSound");
		try {
//			soundPool.stopAll();
//			soundPool.soundPlay(2);
		} catch (Exception e) {
			Log.d("HttpProces", "VoiceException" + e.toString());
		}
	}
	public static void soundStop() {
		Log.d("HttpProces", "soundStop");
		try {
//			soundPool.stopAll();
		} catch (Exception e) {
			Log.d("HttpProces", "VoiceException" + e.toString());
		}
	}

	public static void cancelHttp(final Context context) {
		TatansToast.showAndCancel("��ǰ����δ���ӣ����������������ٳ��ԡ�");
	/*	final Speaker s = Speaker.getInstance(context.getApplicationContext());
		if (AuthenticatorActivity.bLoginCreate) {
			s.speech("��������ʧ�ܣ����ڽ��������������ӡ�");
			s.setOnSpeechCompletionListener(new onSpeechCompletionListener() {
				@Override
				public void onCompletion(int arg0) {
					if (arg0 == 0) {
						s.setOnSpeechCompletionListener(null);
						Intent intent = new Intent();
						String activityName = "net.tatans.hyperion.settings.activities.WifiSettingActivity";
						String pkgName = "net.tatans.hyperion.settings";
						intent.setComponent(new ComponentName(pkgName,
								activityName));
						try {
							context.startActivity(intent);
						} catch (Exception e) {
							s.speech("��Ӧ�û�Ϊ��װ");
						}
					}
				}
			});

		} else {
			s.speech("��������ʧ�ܣ���鿴�������");
		}
		Log.d("SoundPlayerControl", "cancelHttp");
		soundPool.stopAll();
		soundPool.soundPlay(2);*/
	}
}
