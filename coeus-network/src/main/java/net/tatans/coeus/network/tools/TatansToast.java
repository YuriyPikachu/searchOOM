package net.tatans.coeus.network.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;
/**
 * @author ���� <br/> 
 * Toast����ز���
 * <br/> 2015-7-22  
 * 
 */
public class TatansToast  
{  
  private TatansToast()  
  {  
      /** cannot be instantiated**/
      throw new UnsupportedOperationException("cannot be instantiated");  
  }  
  private static Toast mToast;
  public static boolean isShow = true;  
/**
   * ��ʱ����ʾToast
   *  
   * @param message
   */
  public static void showShort(CharSequence message)  
  {  
      if (isShow){
    	  mToast =Toast.makeText(TatansApplication.getContext(), message, Toast.LENGTH_SHORT);
    	  mToast.show();
      }  
  }  

  /**
   * ��ʱ����ʾToast
   *  
   * @param message
   */
  public static void showShort( int message)  
  {  
      if (isShow){
    	  mToast=Toast.makeText(TatansApplication.getContext(), message, Toast.LENGTH_SHORT);
    	  mToast.show();
      }  
  }  
  /**
   * ��ʱ����ʾToast��ǰ��cancel��
   *  
   * @param message
   */
  public static void showAndCancel( CharSequence message)  
  {  
	  cancel();
      if (isShow){
    	  mToast=Toast.makeText(TatansApplication.getContext(), message, Toast.LENGTH_SHORT);
    	  mToast.show();
      }  
  }  
  /**
   * ��ʱ����ʾToast
   *  
   * @param message
   */
  public static void showLong( CharSequence message)  
  {  
      if (isShow){
    	  mToast= Toast.makeText(TatansApplication.getContext(), message, Toast.LENGTH_LONG);
    	  mToast.show();
      }  
  }  

  /**
   * ��ʱ����ʾToast
   *  
   * @param message
   */
  public static void showLong( int message)  
  {  
      if (isShow){
    	  mToast= Toast.makeText(TatansApplication.getContext(), message, Toast.LENGTH_LONG);
    	  mToast.show();
      }  
  }  
  /**
   * �Զ�����ʾToastʱ��
   *  
   * @param message
   * @param duration
   */
  public static void show( CharSequence message, int duration)  
  {  
      if (isShow){
    	  mToast=Toast.makeText(TatansApplication.getContext(), message, duration);
    	  mToast.show();
      } 
  }  

  /**
   * �Զ�����ʾToastʱ��
   *  
   * @param message
   * @param duration
   */
  public static void show(int message, int duration)  
  {    
      if (isShow){
    	  mToast=Toast.makeText(TatansApplication.getContext(), message, duration);
    	  mToast.show();
      }  
  }  
  /**
   * ȡ��Toast��ʾ
   *  
   */
  @SuppressLint("NewApi")
public static void cancel()  
  {  
      if (isShow){
    	  try {
    		  AccessibilityManager accessibilityManager = (AccessibilityManager) TatansApplication.getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
    	      accessibilityManager.interrupt();
    	  } catch (Exception e) {
			// TODO: handle exception
    		  TatansLog.e("TatansToast:"+e.toString());
    	  }
    	  if(null!=mToast)
    		  mToast.cancel();
      }  
  }
}

