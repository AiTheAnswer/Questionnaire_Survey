package com.allen.questionnaire.service.httputil.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Request;
import okio.Buffer;

/**
 * Created by Renjy on 2018/1/30.
 * 工具类
 */

public class Util {
    public static JSONObject HttpParameters2Json(Map<String, String> map) {
        return new JSONObject(map);
    }

    public static String ConvertMap2HttpParams(Map<String, String> map) {
        StringBuilder buffer = new StringBuilder();
        TreeMap sortedMap = new TreeMap();
        sortedMap.putAll(map);
        Iterator var3 = sortedMap.entrySet().iterator();

        while (var3.hasNext()) {
            Map.Entry entry = (Map.Entry) var3.next();
            String value = (String) entry.getValue();
            if (value != null) {
                buffer.append((String) entry.getKey()).append("=").append(value).append("&");
            }
        }

        if (!map.isEmpty()) {
            buffer.deleteCharAt(buffer.length() - 1);
        }

        return buffer.toString();
    }

    public static Map<String, String> encoderName(Map<String, String> map) {
        Iterator var1 = map.entrySet().iterator();

        while (var1.hasNext()) {
            Map.Entry entry = (Map.Entry) var1.next();
            String key = (String) entry.getKey();
            if (key.equals("q") || key.equals("tag_name")) {
                String value = null;

                try {
                    value = URLEncoder.encode((String) entry.getValue(), "UTF-8");
                } catch (UnsupportedEncodingException var6) {
                    var6.printStackTrace();
                }

                map.put(key, value);
                break;
            }
        }

        return map;
    }

    public static String readInputStream(InputStream inStream) throws Exception {
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new InputStreamReader(inStream));
        StringBuffer buffer = new StringBuffer();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            buffer.append(line.trim());
        }

        return buffer.toString();
    }

    public static boolean isEmpty(String str) {
        return !TextUtils.isEmpty(str) && !"null".equals(str);
    }

    public static Map<String, String> cType(Map<String, Object> map) {
        HashMap param = new HashMap();
        Iterator var2 = map.entrySet().iterator();

        while (var2.hasNext()) {
            Map.Entry entry = (Map.Entry) var2.next();
            param.put(entry.getKey(), String.valueOf(entry.getValue()));
        }

        return param;
    }

    /**
     * 通过request 获取请求的参数，并将参数用MD5转换后返回
     *
     * @param request         请求体
     * @param isMD5Encryption 是否用MD5加密
     * @return 参数加密后返回的结果
     */
    public static String getParams(Request request, boolean isMD5Encryption) {
        String params = "";
        Buffer buffer = new Buffer();
        try {
            request.body().writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        params = buffer.readUtf8(); //获取请求参数
        if (null != params) {
            if (isMD5Encryption) {
                return Util.MD5Encryption(params);
            } else {
                return params;
            }
        } else {
            params = "";
        }
        return params;
    }

    /**
     * 将map集合参数进行拼接，并用MD5进行加密返回
     *
     * @param params 参数集合
     * @return 加密后的参数
     */
    public static String MD5Encryption(Map<String, String> params) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.putAll(params);
        String httpParams = ConvertMap2HttpParams(hashMap);
        String md5Params = MD5Encryption(httpParams);
        return md5Params;
    }

    /**
     * 使用MD5进行加密
     *
     * @param params 需要加密的参数
     * @return 加密后的数据
     */
    public static String MD5Encryption(String params) {
        StringBuilder sb = new StringBuilder();
        //MD5是加密方式
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        //将二进制的byte数组进行加密
        byte[] digest = messageDigest.digest(params.getBytes());
        //遍历
        for (int i = 0; i < digest.length; i++) {
            //进行加密 & int值的 255   000...00010000001
            int result = digest[i] & 0xff;
            //将int值转换为16进制
            String hexString = Integer.toHexString(result);
            //String hexString = Integer.toHexString(result) + 1 ;//这里加1可以进行2次加密---加盐
            //这里会输出一个长度小于2的一段字符，所以前面加个0
            if (hexString.length() < 2) {
                sb.append("0");
            }
            sb.append(hexString);
        }
        //这里返回一个加密过的结果
        return sb.toString();
    }

    //------------------------------------------------
    public static Object getPreference(Context context, String key, String type) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        if (type.equals("S")) {
            return sharedPref.getString(key, null);
        }
        if (type.equals("F")) {
            return sharedPref.getFloat(key, 0.0F);
        }
        if (type.equals("I")) {
            return sharedPref.getInt(key, 0);
        }
        if (type.equals("L")) {
            return sharedPref.getLong(key, 0L);
        }
        if (type.equals("B")) {
            return sharedPref.getBoolean(key, false);
        }
        return null;
    }

    public static void setPreference(Context context, String key, Object value, String type) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (type.equals("S")) {
            editor.putString(key, (String) value);
        }
        if (type.equals("B")) {
            editor.putBoolean(key, (Boolean) value);
        }
        if (type.equals("L")) {
            editor.putLong(key, (Long) value);
        }
        if (type.equals("I")) {
            editor.putInt(key, (Integer) value);
        }
        editor.apply();
    }
}
