package com.example.cafeappproject.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.cafeappproject.R
import com.example.cafeappproject.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private var mBinding: FragmentSignUpBinding? = null
    private val binding get() = mBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentSignUpBinding.inflate(inflater, container, false)

        var overlapOrRulesCheck = OverlapOrRulesCheck()

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
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                context?.let { it ->
                    overlapOrRulesCheck.EmailRulesCheck(
                        it,
                        binding.idSubtxtSignupEmail,
                        s.toString()
                    )
                }
            }
        })

        // Password
        binding.idTxtSignupPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                context?.let { it ->
                    overlapOrRulesCheck.PasswordRulesCheck(
                        it,
                        binding.idSubtxtSignupPassword,
                        s.toString()
                    )
                }
            }
        })

        binding.idTxtSignupReconfirmpassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                context?.let { it ->
                    overlapOrRulesCheck.ReconfirmPassword(
                        it,
                        binding.idSubtxtSignupReconfirmpassword,
                        binding.idTxtSignupPassword,
                        s.toString()
                    )
                }
            }
        })

        // 회원가입 버튼 클릭
        binding.idBtnSignup.setOnClickListener {
            // 이거
            overlapOrRulesCheck.CheckAllInput(binding)
            it.findNavController().navigate(R.id.action_signUpFragment_to_mainFragment)
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
