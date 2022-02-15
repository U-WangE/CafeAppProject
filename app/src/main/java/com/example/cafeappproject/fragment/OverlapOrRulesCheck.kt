package com.example.cafeappproject.fragment

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.cafeappproject.R
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class OverlapOrRulesCheck {
    val mDatabase = Firebase.firestore

    fun OverlapCallback(text: String?, type: String, callback: ((Boolean) -> Unit)) {
        var isOverlap = false
        mDatabase.collection("member")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.data.getValue(type).equals(text))  // 중복 값 판별
                        isOverlap = true
                }
                callback.invoke(isOverlap)
            }
            .addOnFailureListener { exception ->
                Log.e("Overlap => ", "Error getting Overlap", exception)
                isOverlap = false
                callback.invoke(isOverlap)
            }
    }


    fun NicknameCheck(context: Context, nickname: String) {
        val resultText = NicknameRulesCheck(nickname)

        OverlapCallback(nickname, "nickname") {
            when (resultText) {
                "WrongInput" -> Toast.makeText(
                    context,
                    R.string.nickname_invalid_input,
                    Toast.LENGTH_SHORT
                ).show()
                "NullOrEmpty" -> Toast.makeText(
                    context,
                    R.string.non_input,
                    Toast.LENGTH_SHORT
                ).show()
                "DoOverlapCheck" -> {
                    if (!it)
                        Toast.makeText(context, R.string.nickname_correct_input, Toast.LENGTH_SHORT)
                            .show()
                    else
                        Toast.makeText(context, R.string.nickname_overlap_input, Toast.LENGTH_SHORT)
                            .show()
                }
                else -> Log.e("Nickname => ", "Error getting Overlap")
            }
        }
    }

    fun NicknameRulesCheck(nickname: String?): String {
        val exp = Regex("^[가-힣ㄱ-ㅎa-zA-Z0-9._-]{2,}\$")  // password 정규식(숫자, 영어, 한국어와 언더스코어) 허용

        if (nickname.isNullOrEmpty()) return "NullOrEmpty"
        else if (!exp.matches(nickname)) return "WrongInput"
        else
            return "DoOverlapCheck"
    }


    fun EmailCehck(context: Context, email: String) {
        val resultText = EmailRulesCheck(email)

        OverlapCallback(email, "email") {
            when (resultText) {
                "WrongInput" -> Toast.makeText(
                    context,
                    R.string.email_invalid_input,
                    Toast.LENGTH_SHORT
                ).show()
                "NullOrEmpty" -> Toast.makeText(
                    context,
                    R.string.non_input,
                    Toast.LENGTH_SHORT
                ).show()
                "DoOverlapCheck" -> {
                    if (!it)
                        Toast.makeText(context, R.string.nickname_correct_input, Toast.LENGTH_SHORT)
                            .show()
                    else
                        Toast.makeText(context, R.string.email_overlap_input, Toast.LENGTH_SHORT)
                            .show()
                }
                else -> Log.e("Email => ", "Error getting Overlap")
            }
        }
    }

    fun EmailRulesCheck(email: String): String {
        val exp = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}\$")  // email 정규식

        if (email.isNullOrEmpty()) return "NullOrEmpty"
        else if (!exp.matches(email)) return "WrongInput"
        else
            return "DoOverlapCheck"
    }

}