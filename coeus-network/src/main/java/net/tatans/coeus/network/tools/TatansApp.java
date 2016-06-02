package net.tatans.coeus.network.tools;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

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
}
