package net.tatans.rhea.network.register;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import net.tatans.coeus.network.tools.TatansApplication;

/**
 * Created by SiLiPing on 2016/5/25.
 */
public class NetworkUtil {

	/**
	 * ≈–∂œ «∑Ò”–Õ¯¬Á
	 * @return
     */
	public static boolean isNetwork() {
		ConnectivityManager connectivity = (ConnectivityManager) TatansApplication.getContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
