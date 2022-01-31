package com.example.cafeappproject.fragment

import android.service.autofill.UserData
import android.util.Log
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OverlapOrRulesCheck {
    val mDatabase = Firebase.firestore
    
    public fun NicknameRulesCheck(nickname: String?) : String {
        val exp = Regex("^[가-힣ㄱ-ㅎa-zA-Z0-9._ -]{2,}\$")  // password 정규식(숫자, 영어, 한국어와 언더스코어) 허용

        if(nickname.isNullOrEmpty()) return "NullOrEmpty"
        else if(!exp.matches(nickname)) return "WrongInput"
        else if(!IsOverlap(nickname, "nickname")) return "CurrectInput" else return "Overlap" // database 에서 닉네임 가져와 중복 검사
    }

    public fun EmailRulesCheck(email: String): String {
        val exp = Regex("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\$")  // email 정규식

        IsOverlap(email, "email");

        if(email.isNullOrEmpty()) return "NullOrEmpty"
        else if(!exp.matches(email)) return "WrongInput"
        else if(!IsOverlap(email, "email")) return "CurrectInput" else return "Overlap" // database 에서 이메일 가져와 중복 검사
    }

    // cloud firestore  내의 중복 값 확인 (x realtime database)
    private fun IsOverlap(text: String, type: String): Boolean {
        var isOverlap = false

        mDatabase.collection("member")
            .get()
            .addOnSuccessListener { result ->
                for(document in result) {
                    if(document.data.getValue(type).equals(text))  // 중복 값 판별
                        isOverlap = true
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Overlap => ", "Error getting Overlap", exception)
            }

        return isOverlap
    }
}