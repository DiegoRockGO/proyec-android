package com.uy.csi.sige.tools;

import android.content.SharedPreferences;

public class ResourceUtil {

    private SharedPreferences preference;

    public ResourceUtil(SharedPreferences preference){
        this.preference = preference;
    }

    public void putStr(String key, String value){
        preference.edit().putString(key, value).commit();
    }

    public void putInt(String key, int value){
        preference.edit().putInt(key, value).commit();
    }

}
