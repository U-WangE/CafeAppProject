package com.example.cafeappproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cafeappproject.R
import com.example.cafeappproject.databinding.FragmentSignUpBinding
import com.google.firebase.database.FirebaseDatabase

class SignUpFragment : Fragment() {

    private var mBinding : FragmentSignUpBinding? = null
    private val binding get() = mBinding!!

    data class User(var nickname: String, var email: String, var password: String)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.idBtnSignupNicknamecheck.setOnClickListener {
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