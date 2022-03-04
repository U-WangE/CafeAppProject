package com.example.cafeappproject.fragment

import android.content.Context
import android.content.SharedPreferences
import com.example.cafeappproject.R

object MySharedPreferences {
    private val myAccount : String = "account"

    // MySharedPreferences 초기화
    fun clearUser(context: Context) {
        val prefs : SharedPreferences = context?.getSharedPreferences(myAccount, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()

        editor.clear()
        editor.commit()
    }

    // Email 정보 저장
    fun setUserEmail(context: Context, input: String) {
        val prefs : SharedPreferences = context?.getSharedPreferences(myAccount, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("myEmail", input)
        editor.commit()
    }

    // 저장된 Email 반환
    fun getUserEmail(context: Context) : String {
        var prefs : SharedPreferences = context?.getSharedPreferences(myAccount, Context.MODE_PRIVATE)

        return prefs.getString("myEmail", "").toString()
    }

    // Password 정보 저장
    fun setUserPassword(context: Context, input: String) {
        val prefs : SharedPreferences = context?.getSharedPreferences(myAccount, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("myPassword", input)
        editor.commit()
    }

    // 저장된 Password 반환
    fun getUserPassword(context: Context) : String {
        var prefs : SharedPreferences = context?.getSharedPreferences(myAccount, Context.MODE_PRIVATE)

        return prefs.getString("myPassword", "").toString()
    }

    // AutoLogin 여부 저장
    fun setAutoLogin(context: Context, input: Boolean) {
        var prefs : SharedPreferences = context?.getSharedPreferences(myAccount, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putBoolean("myAutoLogin", input)
        editor.commit()
    }

    // 저장된 AutoLogin 여부 반환
    fun getAutoLogin(context: Context) : Boolean{
        var prefs : SharedPreferences = context?.getSharedPreferences(myAccount, Context.MODE_PRIVATE)

        return prefs.getBoolean("myAutoLogin", true)
    }
}