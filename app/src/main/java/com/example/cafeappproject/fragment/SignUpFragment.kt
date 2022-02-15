package com.example.cafeappproject.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cafeappproject.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private var mBinding: FragmentSignUpBinding? = null
    private val binding get() = mBinding!!

    data class User(var nickname: String?, var email: String?, var password: String?)

    private var overlapOrRulesCheck = OverlapOrRulesCheck()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentSignUpBinding.inflate(inflater, container, false)

        // NicknameCheck
        binding.idBtnSignupNicknamecheck.setOnClickListener {
            context?.let { it ->
                overlapOrRulesCheck.NicknameRulesCheck(
                    it,
                    binding.idTxtSignupNickname.text.toString()
                )
            }
        }

        // EmailCheck
        binding.idTxtSignupEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                context?.let { it ->
                    overlapOrRulesCheck.EmailRulesCheck(it, binding.idSubtxtSignupEmail, s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) { }
        })

        binding.idTxtSignupPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                context?.let { it ->
                    overlapOrRulesCheck.PasswordRulesCheck(it, binding.idSubtxtSignupPassword, s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) { }
        })

        binding.idTxtSignupReconfirmpassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                context?.let { it ->
                    overlapOrRulesCheck.ReconfirmPassword(it, binding.idSubtxtSignupReconfirmpassword, binding.idTxtSignupPassword, s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) { }

        })

        binding.idBtnSignup.setOnClickListener {

        }


        return binding.root
    }

    // 올바른 nickname인지 검사
    // 1: 올바른 입력 o, 중복x
    // 2: 올바른 입력 o, 중복o
    // 3: 올바른 입력 x
    // 4: null


    // 프래그먼트가 destroy (파괴) 될때.. ????
    override fun onDestroyView() {
        // onDestroyView 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroyView()
    }

}

// 중복성 검사만을 위한 class 파일을 만들까?