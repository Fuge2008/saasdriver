//package com.saas.chat.chat;
//
//
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import com.alibaba.fastjson.JSONException;
//import com.alibaba.fastjson.JSONObject;
//
//public class LocalDataUtils {
//    /**
//     * 保存Preference的name
//     */
//    public static final String PREFERENCE_NAME = "userInfo";
//    private static SharedPreferences mSharedPreferences;
//    private static LocalDataUtils mPreferencemManager;
//    private static SharedPreferences.Editor editor;
//    private String SHARED_KEY_USER_INFO = "shared_key_user_info";
//
//
//    private LocalDataUtils(Context cxt) {
//        mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
//        editor = mSharedPreferences.edit();
//    }
//
//    public static synchronized void init(Context cxt) {
//        if (mPreferencemManager == null) {
//            mPreferencemManager = new LocalDataUtils(cxt);
//        }
//    }
//
//    /**
//     * 单例模式，获取instance实例
//     *
//     * @param
//     * @return
//     */
//    public synchronized static LocalDataUtils getInstance() {
//        if (mPreferencemManager == null) {
//            throw new RuntimeException("please init first!");
//        }
//
//        return mPreferencemManager;
//    }
//
//
//    public void setUserJson(JSONObject userJson) {
//        String userInfo = "";
//        if (userJson != null) {
//            try {
//                userInfo = userJson.toJSONString();
//            } catch (JSONException e) {
//            }
//        }
//        editor.putString(SHARED_KEY_USER_INFO, userInfo);
//        editor.commit();
//    }
//
//    public JSONObject getUserJson() {
//        JSONObject userJson = new JSONObject();
//
//        String userStr = mSharedPreferences.getString(SHARED_KEY_USER_INFO, null);
//        if (userStr != null) {
//            userJson = JSONObject.parseObject(userStr);
//
//        }
//        return userJson;
//
//    }
//
//    public void removeCurrentUserInfo() {
//        editor.remove(SHARED_KEY_USER_INFO);
//        editor.commit();
//    }
//}
