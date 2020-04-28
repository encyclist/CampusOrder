package com.erning.common.sharedperference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtil {
    public static String getSpString(Context context, String key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String rr = null;
        try {
            String k = AesUtils.encrypt(CPPUtils.getSPKey(context),key);
            String r = sp.getString(k, "");
            rr = r.isEmpty() ? "" : AesUtils.decrypt(CPPUtils.getSPKey(context),r);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rr;
    }
    public static void putSpString(Context context,String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            String k = AesUtils.encrypt(CPPUtils.getSPKey(context),key);
            String v= AesUtils.encrypt(CPPUtils.getSPKey(context),value);
            sp.edit().putString(k, v).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long getSpLong(Context context, String key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getLong(key, 0);
    }

    protected static void putSpLong(Context context, String key, long value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putLong(key, value).commit();
    }
}
