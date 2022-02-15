package com.example.cafeappproject.fragment

import android.os.Bundle
import android.util.Log
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
                overlapOrRulesCheck.NicknameCheck(
                    it,
                    binding.idTxtSignupNickname.text.toString()
                )
            }
        }

        // EmailCheck
        binding.idBtnSignup.setOnClickListener {
            context?.let { it ->
                overlapOrRulesCheck.EmailCehck(
                    it,
                    binding.idTxtSignupNickname.text.toString()
                )
            }
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