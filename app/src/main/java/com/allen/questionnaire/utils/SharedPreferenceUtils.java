package com.allen.questionnaire.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreference的工具类
 *
 * @author Renjy
 */

public class SharedPreferenceUtils {

    private static SharedPreferenceUtils preferenceUtils;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPreferenceUtils(Context context) {
        Context applicationContext = context.getApplicationContext();
        sharedPreferences = applicationContext.getSharedPreferences("questionnaire", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPreferenceUtils getInstance(Context context) {
        if (null == preferenceUtils) {
            preferenceUtils = new SharedPreferenceUtils(context);
        }
        return preferenceUtils;
    }

    /**
     * 保存布尔值
     *
     * @param key   键
     * @param value 值
     */
    public void setPreference(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 保存字符串
     *
     * @param key   键
     * @param value 值
     */
    public void setPreference(String key, String value) {
        editor.putString(key, value);
        editor.commit();
        String string = sharedPreferences.getString(key, "ds");
    }

    /**
     * 保存Int
     *
     * @param key   键
     * @param value 值
     */
    public void setPreference(String key, Integer value) {
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 保存Long
     *
     * @param key   键
     * @param value 值
     */
    public void setPreference(String key, Long value) {
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 保存Float
     *
     * @param key   键
     * @param value 值
     */
    public void setPreference(String key, Float value) {
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * 获取键对应的值
     *
     * @param key 键
     * @return 键对应的值
     */
    public boolean getPreferenceBoolean(String key, boolean defaultValue) {
        if (sharedPreferences.contains(key)) {
            defaultValue = sharedPreferences.getBoolean(key, defaultValue);
        }
        return defaultValue;
    }

    /**
     * 获取键对应的值
     *
     * @param key 键
     * @return 键对应的值
     */
    public String getPreferenceString(String key, String defaultValue) {
        if (sharedPreferences.contains(key)) {
            defaultValue = sharedPreferences.getString(key, defaultValue);
        }
        return defaultValue;
    }

    /**
     * 获取键对应的值
     *
     * @param key 键
     * @return 键对应的值
     */
    public Integer getPreferenceString(String key, Integer defaultValue) {
        if (sharedPreferences.contains(key)) {
            defaultValue = sharedPreferences.getInt(key, defaultValue);
        }
        return defaultValue;
    }

    /**
     * 获取键对应的值
     *
     * @param key 键
     * @return 键对应的值
     */
    public Float getPreferenceString(String key, Float defaultValue) {
        if (sharedPreferences.contains(key)) {
            defaultValue = sharedPreferences.getFloat(key, defaultValue);
        }
        return defaultValue;
    }

    /**
     * 获取键对应的值
     *
     * @param key 键
     * @return 键对应的值
     */
    public Long getPreferenceString(String key, Long defaultValue) {
        if (sharedPreferences.contains(key)) {
            defaultValue = sharedPreferences.getLong(key, defaultValue);
        }
        return defaultValue;
    }


}
