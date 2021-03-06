package com.example.cafeappproject.fragment

import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.cafeappproject.R
import com.example.cafeappproject.databinding.FragmentSignUpBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OverlapOrRulesCheck() : SaveSignUpData() {
    val mDatabase = Firebase.firestore

    fun OverlapCallback(text: String?, type: String?, callback: ((Boolean) -> Unit)) {
        var isOverlap = false

        // 중복 값 판별
        mDatabase.collection("member")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.data.getValue(type).equals(text))
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

    // Nickname
    // Nickname 유효성 검사
    fun NicknameRulesCheck(context: Context, inputText: String?, btnNicknamecheck: Button) {
        val exp = Regex("^[가-힣ㄱ-ㅎa-zA-Z0-9._-]{2,}\$")  // nickname 정규식(숫자, 영어, 한국어와 언더스코어)
        if (inputText.isNullOrEmpty())  // nickname null or Empty 확인
            Toast.makeText(context, R.string.non_input, Toast.LENGTH_SHORT).show()
        else if (!exp.matches(inputText))  // nickname 정규식 유효성 검사
            Toast.makeText(context, R.string.nickname_invalid_input, Toast.LENGTH_SHORT).show()
        else {
            btnNicknamecheck.text = "···"
            NicknameOverlapCheck(context, inputText, btnNicknamecheck)  // 중복성 검사
        }
    }

    // Nickname 중복성 검사
    fun NicknameOverlapCheck(context: Context, inputText: String, btnNicknamecheck: Button) {
        OverlapCallback(inputText, "nickname") {
            if (!it) {
                Toast.makeText(context, R.string.correct_input, Toast.LENGTH_SHORT).show()
                Nickname(inputText)
            } else {  // nickname 중복
                Toast.makeText(context, R.string.nickname_overlap_input, Toast.LENGTH_SHORT).show()
            }
            btnNicknamecheck.text = "확인"
        }
    }

    // Email
    // Email 유효성 검사
    fun EmailRulesCheck(context: Context, textBelow: TextView, inputText: String) {
        if (inputText.isNullOrEmpty())  // Email null or Empty 확인
            textBelow.text = context.getString(R.string.non_input)
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(inputText)
                .matches()
        )  // Email 정규식 유효성 검사
            textBelow.text = context.getString(R.string.email_invalid_input)
        else EmailOverlapCehck(context, textBelow, inputText)  // 중복성 검사
    }

    // Email 중복성 검사
    fun EmailOverlapCehck(context: Context, textBelow: TextView, inputText: String) {
        OverlapCallback(inputText, "email") {
            if (!it) {  // Email 사용 가능
                textBelow.text = context.getString(R.string.correct_input)
                Email(inputText)
            } else  // Email 중복
                textBelow.text = context.getString(R.string.email_overlap_input)
        }
    }

    // Password
    // Password 유효성 검사
    fun PasswordRulesCheck(context: Context, textBelow: TextView, inputText: String) {
        val exp = Regex("(^[a-zA-Z0-9\$`~!@\$!%*#^?&\\\\(\\\\)\\-_=+]).{8,}\$")
        val exp_kor = Regex(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")

        if (inputText.isNullOrEmpty())  // Password null or Empty 확인
            textBelow.text = context.getString(R.string.non_input)
        else if (!exp.matches(inputText) || inputText.matches(exp_kor))   // Password 정규식 유효성 검사
            textBelow.text = context.getString(R.string.password_invalid_input)
        else
            textBelow.text = context.getString(R.string.correct_input)
    }

    // 동일한 Password 입력 확인
    fun ReconfirmPassword(
        context: Context,
        passwordText: TextView,
        textBelow: TextView,
        inputText: String
    ) {
        if (inputText.isNullOrEmpty())  // reconfirmPassword null or Empty 확인
            textBelow.text = context.getString(R.string.non_input)
        else if (inputText.equals(passwordText.text.toString())) {  // reconfirmPassword == password 확인
            textBelow.text = context.getString(R.string.reconfirm_password_currect_input)
            Password(inputText)
        } else
            textBelow.text = context.getString(R.string.reconfirm_password_invalid_input)
    }

    // Any Input Check && Load Firestore
    fun CheckAllInput(binding: FragmentSignUpBinding): Boolean {
        if (Check()) {
            mDatabase.collection("member").document().set(SignUp())
            return true
        } else
            return false
    }
}