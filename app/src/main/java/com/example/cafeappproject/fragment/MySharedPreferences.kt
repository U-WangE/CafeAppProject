package com.example.cafeappproject.fragment

import android.content.Context
import android.content.SharedPreferences
import com.example.cafeappproject.R

object MySharedPreferences {
    private val myAccount : String = "account"

    fun setUserEmail(context: Context, input: String) {
        val prefs : SharedPreferences = context?.getSharedPreferences(myAccount, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("myEmail", input)
        editor.commit()
    }

    fun getUserEmail(context: Context) : String {
        var prefs : SharedPreferences = context?.getSharedPreferences(myAccount, Context.MODE_PRIVATE)

        return prefs.getString("myEmail", "").toString()
    }

    fun setUserPassword(context: Context, input: String) {
        val prefs : SharedPreferences = context?.getSharedPreferences(myAccount, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("myPassword", input)
        editor.commit()
    }

    fun getUserPassword(context: Context) : String {
        var prefs : SharedPreferences = context?.getSharedPreferences(myAccount, Context.MODE_PRIVATE)

        return prefs.getString("myPassword", "").toString()
    }

    fun clearUser(context: Context) {
        val prefs : SharedPreferences = context?.getSharedPreferences(myAccount, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()

        editor.clear()
        editor.commit()
    }
}