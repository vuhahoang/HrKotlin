package com.example.hrkotlin.common.pref

import android.content.Context
import android.content.SharedPreferences

object SharePrefManager {
    val MY_PREFERENCES = "Pref"
    val ACCESS_TOKEN = "accessToken"
    val IS_LOGIN = "islogin"

    @Synchronized
    fun getPreference(context: Context?): SharedPreferences? {
        return context?.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getAccessToken(context: Context?): String? {
        val preferences: SharedPreferences? = getPreference(context)
        if (preferences != null) {
            return preferences.getString(ACCESS_TOKEN, null)
        }
        return null
    }

    fun saveAccessToken(context: Context?, accessToken: String?) {
        val preferences: SharedPreferences? = getPreference(context)
        val editor = preferences?.edit()
        editor?.putString(ACCESS_TOKEN,accessToken)
        editor?.apply()
    }

    fun getIsLogin(context: Context?) : Boolean {
        val preferences: SharedPreferences? = getPreference(context)
        if (preferences != null) {
            return preferences.getBoolean(IS_LOGIN,false)
        }
        return false
    }

    fun saveIsLogin(context: Context?,isLogin: Boolean) {
        val preferences: SharedPreferences? = getPreference(context)
        val editor = preferences?.edit()
        editor?.putBoolean(IS_LOGIN,isLogin);
        editor?.apply()
    }
}