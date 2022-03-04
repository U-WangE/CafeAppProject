package com.example.cafeappproject.fragment

open class SaveSignUpData {
    private var email : String? = null
    private var nicknameCheck : Boolean? = false
    private var nickname : String? = null
    private var emailCheck : Boolean? = false
    private var password : String? = null
    private var passwordCheck : Boolean? = false

    data class User(var email: String?, var nickname: String?, var password: String?)

    // 올바른 이메일 입력 -> email 저장
    fun Email(email: String?) {
        this.email = email
        emailCheck = true
    }

    // 올바른 닉네임 입력 -> nickname 저장
    fun Nickname(nickname: String?) {
        this.nickname = nickname
        nicknameCheck = true
    }

    // 올바른 비밀번호 입력 -> password 저장
    fun Password(password: String?) {
        this.password = password
        passwordCheck = true
    }

    // 회원 정보 입력 확인
    fun Check() : Boolean{
        return emailCheck == true && nicknameCheck == true && passwordCheck == true
    }

    // firestore에 저장할 user data class
    fun SignUp() : User{
        var user = User(email, nickname, password).copy()
        return user
    }
}