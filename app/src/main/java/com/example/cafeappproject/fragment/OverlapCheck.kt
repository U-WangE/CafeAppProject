package com.example.cafeappproject.fragment

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class OverlapCheck {

    private val mDatabase = FirebaseDatabase.getInstance().getReference();

    public fun NicknameRulesCheck(nickname: String?) : String {
        val exp = Regex("^[가-힣ㄱ-ㅎa-zA-Z0-9._ -]{2,}\$")  // password 정규식(숫자, 영어, 한국어와 언더스코어) 허용

        if(nickname.isNullOrEmpty()) return "NullOrEmpty"
        else if(!exp.matches(nickname)) return "WrongInput"
        else if(!IsOverlap(nickname, "nickname")) return "CurrectInput" else return "Overlap" // database 에서 닉네임 가져와 중복 검사
    }

    // email 저장
    public fun EmailRulesCheck(email: String): String {
        val exp = Regex("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\$")  // email 정규식

        if(email.isNullOrEmpty()) return "NullOrEmpty"
        else if(!exp.matches(email)) return "WrongInput"
        else if(!IsOverlap(email, "email")) return "CurrectInput" else return "Overlap" // database 에서 이메일 가져와 중복 검사
    }

    private fun IsOverlap(text: String, type: String): Boolean {
        when(type) {
            //"nickname" ->
        }

        fun onDataChange(dataSnapshot: DataSnapshot?) {
                //if(dataSnapshot.getValue() != null) {

                //}
        }

        return false
    }
}