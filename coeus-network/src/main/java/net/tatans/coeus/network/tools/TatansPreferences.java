package net.tatans.coeus.network.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * @author ���� <br/> 
 * sharePreference����
 * <br/> 2015-7-22  
 * 
 */
public class TatansPreferences  
{  
  /**
   * �������ֻ�������ļ���
   */
  public static final String FILE_NAME = "tatans";  

  /**
   * �������ݵķ�����������Ҫ�õ��������ݵľ������ͣ�Ȼ��������͵��ò�ͬ�ı��淽��
   * @param key
   * @param object
   */
  public static void put(String key, Object object)  
  {  

      SharedPreferences sp = TatansApplication.getContext().getSharedPreferences(FILE_NAME,  
              Context.MODE_PRIVATE);  
      SharedPreferences.Editor editor = sp.edit();  

      if (object instanceof String)  
      {  
          editor.putString(key, (String) object);  
      } else if (object instanceof Integer)  
      {  
          editor.putInt(key, (Integer) object);  
      } else if (object instanceof Boolean)  
      {  
          editor.putBoolean(key, (Boolean) object);  
      } else if (object instanceof Float)  
      {  
          editor.putFloat(key, (Float) object);  
      } else if (object instanceof Long)  
      {  
          editor.putLong(key, (Long) object);  
      } else
      {  
          editor.putString(key, object.toString());  
      }  

      SharedPreferencesCompat.apply(editor);  
  }  
  /**
   * �õ��������ݵķ��������Ǹ���Ĭ��ֵ�õ���������ݵľ������ͣ�Ȼ���������ڵķ�����ȡֵ
   *  
   * @param key
   * @param defaultObject
   * @return
   */
  public static Object get( String key, Object defaultObject)  
  {  
      SharedPreferences sp = TatansApplication.getContext().getSharedPreferences(FILE_NAME,  
              Context.MODE_PRIVATE);  

      if (defaultObject instanceof String)  
      {  
          return sp.getString(key, (String) defaultObject);  
      } else if (defaultObject instanceof Integer)  
      {  
          return sp.getInt(key, (Integer) defaultObject);  
      } else if (defaultObject instanceof Boolean)  
      {  
          return sp.getBoolean(key, (Boolean) defaultObject);  
      } else if (defaultObject instanceof Float)  
      {  
          return sp.getFloat(key, (Float) defaultObject);  
      } else if (defaultObject instanceof Long)  
      {  
          return sp.getLong(key, (Long) defaultObject);  
      }  

      return null;  
  }  
  /**
   * �Ƴ�ĳ��keyֵ�Ѿ���Ӧ��ֵ
   * @param key
   */
  public static void remove( String key)  
  {  
      SharedPreferences sp = TatansApplication.getContext().getSharedPreferences(FILE_NAME,  
              Context.MODE_PRIVATE);  
      SharedPreferences.Editor editor = sp.edit();  
      editor.remove(key);  
      SharedPreferencesCompat.apply(editor);  
  }  

  /**
   * �����������
   */
  public static void clear()  
  {  
      SharedPreferences sp = TatansApplication.getContext().getSharedPreferences(FILE_NAME,  
              Context.MODE_PRIVATE);  
      SharedPreferences.Editor editor = sp.edit();  
      editor.clear();  
      SharedPreferencesCompat.apply(editor);  
  }  

  /**
   * ��ѯĳ��key�Ƿ��Ѿ�����
   * @param key
   * @return
   */
  public static boolean contains(String key)  
  {  
      SharedPreferences sp = TatansApplication.getContext().getSharedPreferences(FILE_NAME,  
              Context.MODE_PRIVATE);  
      return sp.contains(key);  
  }  
  /**
   * �������еļ�ֵ��
   * @return
   */
  public static Map<String, ?> getAll()  
  {  
      SharedPreferences sp = TatansApplication.getContext().getSharedPreferences(FILE_NAME,  
              Context.MODE_PRIVATE);  
      return sp.getAll();  
  }  

  /**
   * ����һ�����SharedPreferencesCompat.apply������һ��������
   *  
   * @author zhy
   *  
   */
  private static class SharedPreferencesCompat  
  {  
      private static final Method sApplyMethod = findApplyMethod();  

      /**
       * �������apply�ķ���
       *  
       * @return
       */
      @SuppressWarnings({ "unchecked", "rawtypes" })  
      private static Method findApplyMethod()  
      {  
          try
          {  
              Class clz = SharedPreferences.Editor.class;  
              return clz.getMethod("apply");  
          } catch (NoSuchMethodException e)  
          {  
          }  

          return null;  
      }  
      /**
       * ����ҵ���ʹ��applyִ�У�����ʹ��commit
       *  
       * @param editor
       */
      public static void apply(SharedPreferences.Editor editor)  
      {  
          try
          {  
              if (sApplyMethod != null)  
              {  
                  sApplyMethod.invoke(editor);  
                  return;  
              }  
          } catch (IllegalArgumentException e)  
          {  
          } catch (IllegalAccessException e)  
          {  
          } catch (InvocationTargetException e)  
          {  
          }  
          editor.commit();  
      }  
  }  

}

