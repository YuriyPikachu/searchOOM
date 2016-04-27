package net.tatans.coeus.network.josn.analysis;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;

@SuppressWarnings("unchecked")
@SuppressLint("DefaultLocale")
public class JsonDeepUtil {

	/**
	 * ʹ�ø÷������Խ�json�ַ���תΪһ��ʵ����
	 * 
	 * @param jsonStr
	 * @param c
	 * @return
	 * @throws JSONException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public <T> T[] getEntity(String jsonStr, Class<T> c) throws JSONException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		T[] array = (T[]) Array.newInstance(c, 1);
		if (jsonStr.charAt(0) == '[') {
			JSONArray jsonArray = new JSONArray(jsonStr);
			return getEntity(jsonArray, c);
		} else if (jsonStr.charAt(0) == '{') {
			JSONObject jsonObject = new JSONObject(jsonStr);
			array[0] = getEntity(jsonObject, c);
		}
		return array;
	}

	/**
	 * @param jsonStr
	 * @param c
	 * @return
	 * @throws JSONException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public <T> T getEntityJson(String jsonStr, Class<T> c) throws JSONException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		JSONObject jsonObject = new JSONObject(jsonStr);
		return getEntity(jsonObject, c);
	}

	/**
	 * ʹ�ø÷������Խ�json��ʽתΪһ��ʵ����
	 * 
	 * @param jsonObject
	 * @param c
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws JSONException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public <T> T getEntity(JSONObject jsonObject, Class<T> c) throws InstantiationException, IllegalAccessException, JSONException,
			IllegalArgumentException, InvocationTargetException {
		T t = c.newInstance();
		Field[] fs = c.getDeclaredFields();
		for (Field f : fs) {
			f.setAccessible(true);
			String key = f.getName();
			Type keyType = f.getGenericType();
			String keyU = key.replaceFirst(key.substring(0, 1), key.substring(0, 1).toUpperCase());
			Method[] methods = c.getDeclaredMethods();
			for (Method method : methods) {
				Class<?>[] clazzs = method.getParameterTypes();
				if (clazzs.length == 1 && method.getName().equals("set" + keyU)) {
					Class<?> clazz = clazzs[0];
					LoggerJsonMsg.e("��������" + method.getName() + "��������" + clazz);
					boolean lean = clazz.isArray();
					if (clazz.equals(keyType)) {
						// Object obj = jsonObject.get(key);
						Object obj = getValue(key, jsonObject, clazz);
						if (obj != null) {
							if (obj instanceof JSONArray) {
								JSONArray jsonArray = (JSONArray) obj;
								int len = jsonArray.length();
								Class<?> clazs = clazz.getComponentType();
								Object[] array = (Object[]) Array.newInstance(clazs, len);
								for (int i = 0; i < len; i++) {
									array[i] = getEntity(jsonArray.getJSONObject(i), clazs);
								}
								method.invoke(t, new Object[] { array });
							} else if (obj instanceof JSONObject) {
								if (lean) {
									Class<?> clazs = clazz.getComponentType();
									Object[] array = (Object[]) Array.newInstance(clazs, 1);
									array[0] = getEntity((JSONObject) obj, clazz);
									method.invoke(t, new Object[] { array });
								} else {
									method.invoke(t, getEntity((JSONObject) obj, clazz));
								}
							} else {
								LoggerJsonMsg.e("��������" + method.getName() + "��������" + clazz + "����ֵ" + obj);
								method.invoke(t, obj);
							}
						}
					}
				}
			}
		}
		return t;
	}

	/**
	 * ʹ�ø÷������Խ�json����תΪһ��ʵ����
	 * 
	 * @param jsonArray
	 * @param c
	 * @return
	 * @throws JSONException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public <T> T[] getEntity(JSONArray jsonArray, Class<T> c) throws JSONException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		int len = jsonArray.length();
		T[] array = (T[]) Array.newInstance(c, len);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObjectArray = jsonArray.getJSONObject(i);
			array[i] = getEntity(jsonObjectArray, c);
		}
		return array;
	}

	/**
	 * ����json�е�keyֵ��������ʵ����������ƥ��,ƥ��ɹ���ȡ��ֵ
	 */
	private <E> E getValue(String key, JSONObject jsonObject, Class<E> clazz) throws JSONException {
		LoggerJsonMsg.v("��������" + key + "���ͣ�" + clazz);
		// Object result = jsonObject.get(key);
		if (clazz.equals(String.class)) {
			return (E) jsonObject.getString(key);
		} else if (clazz.equals(int.class)) {
			Integer result = jsonObject.getInt(key);
			return (E) result;
		} else if (clazz.equals(long.class)) {
			Long result = jsonObject.getLong(key);
			return (E) result;
		} else if (clazz.equals(boolean.class)) {
			Boolean result = jsonObject.getBoolean(key);
			return (E) result;
		} else if (clazz.equals(double.class)) {
			Double result = jsonObject.getDouble(key);
			return (E) result;
		} else {
			LoggerJsonMsg.e("��������" + key);
			try {
				Object result = jsonObject.get(key);
				LoggerJsonMsg.e("����ֵ��" + result.toString());
				return (E) result;
			} catch (JSONException e) {
				LoggerJsonMsg.e("���Դ�����json�ַ�����û�ж��õ�json��ʽ" + key);
				return null;
			}
		}
	}
}
