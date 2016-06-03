package net.tatans.coeus.network.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
* @author ���� <br/>
* ��App��صĸ�����  
*/
public class TatansApp  
{  

  private TatansApp()  
  {  
      /**cannot be instantiated **/
      throw new UnsupportedOperationException("cannot be instantiated");  

  }  

  /**
   * ��ȡӦ�ó�������
   */
  public static String getAppName()  
  {  
      try
      {  
          PackageManager packageManager = TatansApplication.getContext().getPackageManager();  
          PackageInfo packageInfo = packageManager.getPackageInfo(  
        		  TatansApplication.getContext().getPackageName(), 0);  
          int labelRes = packageInfo.applicationInfo.labelRes;  
          return TatansApplication.getContext().getResources().getString(labelRes);  
      } catch (NameNotFoundException e)  
      {  
          e.printStackTrace();  
      }  
      return null;  
  }  

  /**
   * [��ȡӦ�ó���汾������Ϣ]
   *  
   * @return ��ǰӦ�õİ汾����
   */
  public static String getVersionName()  
  {  
      try
      {  
          PackageManager packageManager = TatansApplication.getContext().getPackageManager();  
          PackageInfo packageInfo = packageManager.getPackageInfo(  
        		  TatansApplication.getContext().getPackageName(), 0);  
          return packageInfo.versionName;  

      } catch (NameNotFoundException e)  
      {  
          e.printStackTrace();  
      }  
      return null;  
  }
    /**
     * [MD5���ܹ���]
     *
     * @return ��ǰӦ�õİ汾����
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
    /**
     * ��ȡӦ��ǩ��
     * @return
     */

    public static String getSignature() {
         PackageManager manager;
         PackageInfo packageInfo;
         Signature[] signatures;
         StringBuilder builder;
         String signature;
         manager = TatansApplication.getContext().getPackageManager();
         builder = new StringBuilder();
        try {
            /** ͨ�������������ָ����������ǩ���İ���Ϣ **/
            packageInfo = manager.getPackageInfo(TatansApplication.getContext().getPackageName(), PackageManager.GET_SIGNATURES);
            /******* ͨ�����صİ���Ϣ���ǩ������ *******/
            signatures = packageInfo.signatures;
            /******* ѭ������ǩ������ƴ��Ӧ��ǩ�� *******/
            for (Signature signature2 : signatures) {
                builder.append(signature2.toCharsString());
            }
            /************** �õ�Ӧ��ǩ�� **************/
            signature = builder.toString();
            return signature;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ��ȡӦ�ð���
     * @return
     */
    public static String getAppInfo(Context mCtx) {
        try {
            String pkName = mCtx.getPackageName();
            String versionName = mCtx.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = mCtx.getPackageManager().getPackageInfo(pkName, 0).versionCode;
            Log.d("signature","getAppInfo��"+pkName + "   " + versionName + "  " + versionCode);
            return pkName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
