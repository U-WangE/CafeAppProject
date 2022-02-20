package com.example.cafeappproject.fragment

import  android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.cafeappproject.R
import com.example.cafeappproject.databinding.FragmentLoginBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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

        // 로그인 버튼 클릭
        binding.idBtnLogin.setOnClickListener { view ->
            Login(
                binding.idTxtLoginEmail.text.toString(),
                binding.idTxtLoginPassword.text.toString()
            ) {
                if (it) {
                    activity?.let { it -> MySharedPreferences.clearUser(it) }

                    activity?.let { it ->
                        MySharedPreferences.setUserEmail(
                            it,
                            binding.idTxtLoginEmail.text.toString()
                        )
                    }
                    activity?.let { it ->
                        MySharedPreferences.setUserPassword(
                            it,
                            binding.idTxtLoginPassword.text.toString()
                        )
                    }
                    Toast.makeText(activity, R.string.Login, Toast.LENGTH_SHORT).show()
                    view.findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                } else {
                    // 회원 정보 없을 시 Toast Message
                    binding.idTxtLoginEmail.setText(null)
                    binding.idTxtLoginPassword.setText(null)
                    Toast.makeText(activity, R.string.non_registered, Toast.LENGTH_LONG).show()
                }
            }
        }


        // Login -> Signup 화면 전환
        binding.idBtnSignup.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }



        return binding.root
    }

    fun Login(email: String?, password: String?, callback: ((Boolean) -> Unit)) {
        val mDatabase = Firebase.firestore
        var storedEmail = false
        var storedPassword = false
        var isMember = false

        mDatabase.collection("member")
            .get()
            .addOnSuccessListener { it ->
                for (document in it) {
                    if (document.data.getValue("email").equals(email))
                        storedEmail = true
                    if (document.data.getValue("password").equals(password))
                        storedPassword = true
                    if (storedEmail && storedPassword)
                        isMember = true
                }
                callback.invoke(isMember)
            }
            .addOnFailureListener { it ->
                isMember = false
                callback.invoke(isMember)
            }
    }


    // 프래그먼트가 destroy (파괴) 될때..
    override fun onDestroyView() {
        // onDestroyView 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroyView()
    }
}