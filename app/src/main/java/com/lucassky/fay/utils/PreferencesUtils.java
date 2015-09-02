package com.lucassky.fay.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtils {
	
	private static final String config = "config";
	
	
	/**
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveValue(Context context, String key, String value){
		SharedPreferences sp = context.getSharedPreferences(config, 0);
		sp.edit().putString(key, value).commit();
	}
	
	/**
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveValue(Context context, String key, boolean value){
		SharedPreferences sp = context.getSharedPreferences(config, 0);
		sp.edit().putBoolean(key, value).commit();
	}
	
	/**
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveValue(Context context, String key, float value){
		SharedPreferences sp = context.getSharedPreferences(config, 0);
		sp.edit().putFloat(key, value).commit();
	}
	
	
	/**
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveValue(Context context, String key, int value){
		SharedPreferences sp = context.getSharedPreferences(config, 0);
		sp.edit().putInt(key, value).commit();
	}
	
	/**
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveValue(Context context, String key, long value){
		SharedPreferences sp = context.getSharedPreferences(config, 0);
		sp.edit().putLong(key, value).commit();
	}
	
	
	/**
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static long getValue(Context context, String key, long defaultValue){
		SharedPreferences sp = context.getSharedPreferences(config, 0);
		return sp.getLong(key, defaultValue);
	}
	
	
	
	/**
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getValue(Context context, String key, boolean defaultValue){
		SharedPreferences sp = context.getSharedPreferences(config, 0);
		return sp.getBoolean(key, defaultValue);
	}
	
	
	/**
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static float getValue(Context context, String key, float defaultValue){
		SharedPreferences sp = context.getSharedPreferences(config, 0);
		return sp.getFloat(key, defaultValue);
	}
	
	
	/**
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getValue(Context context, String key, int defaultValue){
		SharedPreferences sp = context.getSharedPreferences(config, 0);
		return sp.getInt(key, defaultValue);
	}
	
	
	/**
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getValue(Context context, String key, String defaultValue){
		SharedPreferences sp = context.getSharedPreferences(config, 0);
		return sp.getString(key, defaultValue);
	}
	
	/**
	 * @param context
	 * @param key
	 */
	public static void removeKey(Context context, String key){
		SharedPreferences sp = context.getSharedPreferences(config, 0);
		sp.edit().remove(key).commit();
	}

}
