/*
package net.tatans.coeus.network.account;


import net.tatans.coeus.network.activity.AuthenticatorActivity;
import net.tatans.coeus.network.util.HttpProces;
import net.tatans.coeus.network.util.NetworkUtilities;
import android.content.Context;
import android.content.Intent;

*/
/**
 * @author Yuliang 
 * @time 2014-11-21 
 * @version 1.0
 *//*

public class Accounts {
	public static Boolean bLogin = false;//�ж��ǵ�¼�״ε�¼������token����
	*/
/**
	 * @param context
	 *            ��ǰContext������
	 * @return �˻�ע�ᱣ����Ϣ
	 *//*

	public void registerAccount(Context context){
		*/
/*AuthenticatorActivity aa = new AuthenticatorActivity();
		aa.onRegisterResult(NetworkUtilities.register(aa.getSerialNumber(), "",context), context);*//*

	}
	*/
/**
	 * @param context
	 *            ��ǰContext������
	 * @return ���˻��л�ȡ��ǰ�û�Id
	 *//*

	public static String getUserId(Context context) {
		AccountAcquire acount = new AccountAcquire();
		return acount.getAccountInformation(context, "UserId");
	}
	*/
/**
	 * @param context
	 *            ��ǰContext������
	 * @return ���˻��л�ȡ��ǰ�û�����
	 *//*

	public static String getUserName(Context context) {
		AccountAcquire acount = new AccountAcquire();
		return acount.getAccountInformation(context, "UserName");
	}
	*/
/**
	 * @param context 
	 * 		 ��ǰContext������
	 *//*

	public void initAccountCall(Context context,initAccountCallBackInterface callback){
		AccountAcquire acount = new AccountAcquire();
		HttpProces.initHttp(context);
		AuthenticatorActivity aa = new AuthenticatorActivity();
		aa.setContext(context);
		aa.setAccountCallBack(callback);
		if (acount.AccountsAcquire(context).length == 0) {
			bLogin=true;
			Intent it = new Intent(context, AuthenticatorActivity.class);
			it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(it);
		}else{
			callback.initAccountCallBack();
		}
	}
	public interface initAccountCallBackInterface {
		public void initAccountCallBack();
	}
}
*/
