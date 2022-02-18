package com.example.cafeappproject.fragment

open class SignupFinalCheck {
    private var email : String? = null
    private var nicknameCheck : Boolean? = false
    private var nickname : String? = null
    private var emailCheck : Boolean? = false
    private var password : String? = null
    private var passwordCheck : Boolean? = false

    data class User(var email: String?, var nickname: String?, var password: String?)

    fun Email(email: String?) {
        this.email = email
        emailCheck = true
    }

    fun Nickname(nickname: String?) {
        this.nickname = nickname
        nicknameCheck = true
    }

    fun Password(password: String?) {
        this.password = password
        passwordCheck = true
    }

    fun Check() : Boolean{
        return emailCheck == true && nicknameCheck == true && passwordCheck == true
    }

    fun SignUp() : User{
        var user = User(email, nickname, password).copy()
        return user
    }
}