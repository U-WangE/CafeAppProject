package com.example.cafeappproject.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        val overlapOrRulesCheck = OverlapOrRulesCheck()

        // NicknameCheck Button 클릭
        binding.idBtnSignupNicknamecheck.setOnClickListener {
            context?.let { it ->
                overlapOrRulesCheck.NicknameRulesCheck(
                    it,
                    binding.idTxtSignupNickname.text.toString(),
                    binding.idBtnSignupNicknamecheck
                )
            }
        }

        // Email Overlap Check
        binding.idTxtSignupEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                context?.let { it ->
                    overlapOrRulesCheck.EmailRulesCheck(
                        it,
                        binding.idBelowtxtSignupEmail,
                        s.toString()
                    )
                }
            }
        })

        // Password Rule Check
        binding.idTxtSignupPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                context?.let { it ->
                    overlapOrRulesCheck.PasswordRulesCheck(
                        it,
                        binding.idBelowtxtSignupPassword,
                        s.toString()
                    )
                }
            }
        })

        // Reconfirm Password Same Check
        binding.idTxtSignupReconfirmpassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                context?.let { it ->
                    overlapOrRulesCheck.ReconfirmPassword(
                        it,
                        binding.idTxtSignupPassword,
                        binding.idBelowtxtSignupReconfirmpassword,
                        s.toString()
                    )
                }
            }
        })

        // 회원가입 버튼 클릭
        binding.idBtnSignup.setOnClickListener { it ->
            if(overlapOrRulesCheck.CheckAllInput(binding))  // 회원가입 성공 | SignUpFragment -> LoginFragment 화면 이동
                it.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            else
                Toast.makeText(activity, R.string.some_input_errors, Toast.LENGTH_SHORT).show()
        }

        //취소 버튼
        binding.idBtnSignupCancel.setOnClickListener { it ->
            it.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }


        return binding.root
    }


    // 프래그먼트가 destroy (파괴)
    override fun onDestroyView() {
        // onDestroyView 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroyView()
    }

}
