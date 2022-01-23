package com.example.cafeappproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.cafeappproject.R
import com.example.cafeappproject.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: FragmentLoginBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentLoginBinding.inflate(inflater, container, false)

        // Navigation 화면 전환
        // Login -> Main 화면 전환
        // 화면 전환시 회원인지 파악 필요

        binding.idBtnLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }

        // 회원 정보 없을 시 Toast Message
        Toast.makeText(activity, R.string.non_registered, Toast.LENGTH_SHORT).show()

        // Login -> Signup 화면 전환
        binding.idBtnSignup.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }



        return binding.root
    }

    // 프래그먼트가 destroy (파괴) 될때..
    override fun onDestroyView() {
        // onDestroyView 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroyView()
    }
}