package com.example.cafeappproject.fragment

import android.app.PendingIntent.getActivity
import android.service.autofill.UserData
import android.util.Log
import android.widget.Toast
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
        else if(!IsOverlap(nickname, "nickname")) return "CurrectInput" else return "OverlapInput" // database 에서 닉네임 가져와 중복 검사
    }

    public fun EmailRulesCheck(email: String): String {
        val exp = Regex("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\$")  // email 정규식

        IsOverlap(email, "email");

        if(email.isNullOrEmpty()) return "NullOrEmpty"
        else if(!exp.matches(email)) return "WrongInput"
        else if(!IsOverlap(email, "email")) return "CurrectInput" else return "OverlapInput" // database 에서 이메일 가져와 중복 검사
    }

    // cloud firestore  내의 중복 값 확인 (x realtime database)
    private fun IsOverlap(text: String, type: String): Boolean {
        var isOverlap = false

        mDatabase.collection("member")
            .get()
            .addOnSuccessListener { result ->
                for(document in result) {
                    if(document.data.getValue(type).equals(text))  // 중복 값 판별
                    {
                        Log.d("중복 값 => ", "이거 : " + text)
                        isOverlap = true
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Overlap => ", "Error getting Overlap", exception)
            }

        Log.d("Overlap 값 => ", "이거 : " + isOverlap)   // firebase 검사하는 시간동안 딜레이 발생으로 중복 검사 결과 isOverlap에 저장 안됨
        //https://todaycode.tistory.com/10
        return isOverlap
    }
}