package net.tatans.coeus.network.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
/**
 * @author ���� <br/> 
 * ���̲�����
 * <br/> 2015-7-22  
 * 
 */
public class TatansKeyBoard  
{  
  /**
   * �������
   *  
   * @param mEditText
   *            �����
   */
  @SuppressLint("NewApi")
public static void openKeybord(EditText mEditText)  
  {  
      InputMethodManager imm = (InputMethodManager) TatansApplication.getContext()  
              .getSystemService(Context.INPUT_METHOD_SERVICE);  
      imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);  
      imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,  
              InputMethodManager.HIDE_IMPLICIT_ONLY);  
  }  

  /**
   * �ر������
   *  
   * @param mEditText
   *            �����
   */
  @SuppressLint("NewApi")
public static void closeKeybord(EditText mEditText)  
  {  
      InputMethodManager imm = (InputMethodManager) TatansApplication.getContext()  
              .getSystemService(Context.INPUT_METHOD_SERVICE);  

      imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);  
  }  
}
