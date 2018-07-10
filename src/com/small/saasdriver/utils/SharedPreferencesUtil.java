package com.small.saasdriver.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 偏好设置，项目配置文件保存工具
 * 
 * @author ZhagnKui
 * @Data 2016-09-26
 */
public class SharedPreferencesUtil {

	/** 单例对象 */
	private static SharedPreferencesUtil intance = null;

	/*-----------------------------用户公有信息------------------------*/
	/** 用户名 */
	public static final String USERNAME = "user_name";
	/** 手机号 */
	public static final String TELEPHONE = "telephone";
	/** 密码 */
	public static final String PASSWORD = "password";
	/** 邮箱 */
	public static final String EMAIL = "email";
	/*-----------------------------订单相关信息------------------------*/
	/** 订单接收开关 */
	public static final String ORDER_SWITCH = "orderSwitch";
	/** 订单接收类型 */
	public static final String ORDER_TYPE = "orderType";
	/**
	 * 保存在手机里面的文件名
	 */
	public static final String FILE_NAME = "saasdriver_data";

	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public static SharedPreferencesUtil getIntance() {
		if (intance == null) {
			intance = new SharedPreferencesUtil();
		}
		return intance;
	}

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            键值对名称
	 * @param object
	 *            [String,Integer,Boolean,Float,Long,Object]
	 *            如果传入的object的值的类型在以上范围内， 则会自动调用相应的方法存值
	 */
	public void put(Context context, String key, Object object) {

		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else {
			editor.putString(key, object.toString());
		}

		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对应的方法获取值
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            键值对名称
	 * @param defaultObject
	 *            取值失败时默认返回的值,根据该默认值的类型调用相应的方法取值
	 * @return 
	 *         [String,Integer,Boolean,Float,Long]如果传入的defaultObject的值的类型不在以上范围内，
	 *         返回-1
	 */
	public Object get(Context context, String key, Object defaultObject) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);

		if (defaultObject instanceof String) {
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer) {
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return sp.getLong(key, (Long) defaultObject);
		}

		return -1;
	}

	/**
	 * 移除某个key值已经对应的值
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            键值对名称
	 */
	public void remove(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 清除所有数据
	 * 
	 * @param context
	 *            上下文
	 */
	public void clear(Context context) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 查询某个key是否已经存在
	 * 
	 * @param context
	 *            上下文
	 * @param key
	 *            要查询的key
	 * @return
	 */
	public boolean contains(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.contains(key);
	}

	/**
	 * 返回所有的键值对
	 * 
	 * @param context
	 *            上下文
	 * @return Map< String, ? >
	 */
	public Map<String, ?> getAll(Context context) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.getAll();
	}

	/**
	 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	 */
	private static class SharedPreferencesCompat {
		private final static Method sApplyMethod = findApplyMethod();

		/**
		 * 反射查找apply的方法
		 * 
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod() {
			try {
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e) {
			}

			return null;
		}

		/**
		 * 如果找到则使用apply执行，否则使用commit
		 * 
		 * @param editor
		 */
		public static void apply(SharedPreferences.Editor editor) {
			try {
				if (sApplyMethod != null) {
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			editor.commit();
		}
	}

}
