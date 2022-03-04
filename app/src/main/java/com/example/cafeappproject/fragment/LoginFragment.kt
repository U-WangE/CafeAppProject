package com.example.cafeappproject.fragment

import  android.os.Bundle
import android.util.Log
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

        // 로그인 버튼 클릭
        binding.idBtnLogin.setOnClickListener { view ->
            Login(
                binding.idTxtLoginEmail.text.toString(),
                binding.idTxtLoginPassword.text.toString(),
                binding.idCheckboxAutologin.isChecked
            ) {
                if (it) {

                    // MySharedPreferences 기존 정보 초기화
                    activity?.let { it -> MySharedPreferences.clearUser(it) }

                    // MySharedPreferences에 email 저장
                    activity?.let { it ->
                        MySharedPreferences.setUserEmail(
                            it,
                            binding.idTxtLoginEmail.text.toString()
                        )
                    }

                    // MySharedPreferences에 password 저장
                    activity?.let { it ->
                        MySharedPreferences.setUserPassword(
                            it,
                            binding.idTxtLoginPassword.text.toString()
                        )
                    }

                    // MySharedPreferences에 autoLogin 저장
                    activity?.let { it ->
                        MySharedPreferences.setAutoLogin(
                            it,
                            binding.idCheckboxAutologin.isChecked
                        )
                    }
                    Toast.makeText(activity, R.string.Login, Toast.LENGTH_SHORT).show()
                    view.findNavController()
                        .navigate(R.id.action_loginFragment_to_mainFragment)  // MainFragment로 이동
                } else {
                    // 회원 정보 없을 시 Toast Message
                    binding.idTxtLoginEmail.setText(null)
                    binding.idTxtLoginPassword.setText(null)
                    Toast.makeText(activity, R.string.non_registered, Toast.LENGTH_LONG).show()
                }
            }
        }

        // SignUp button click -> SingUpFragment로 이동
        binding.idBtnSignup.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        return binding.root
    }

    // Login
    fun Login(
        email: String?,
        password: String?,
        autologin: Boolean?,
        callback: ((Boolean) -> Unit)
    ) {
        val mDatabase = Firebase.firestore
        var storedEmail = false
        var storedPassword = false
        var isMember = false

        // Database의 회원 정보 일치 확인
        mDatabase.collection("member")
            .get()
            .addOnSuccessListener { it ->
                for (document in it) {
                    if (document.data.getValue("email").equals(email)) storedEmail =
                        true else storedEmail = false

                    if (document.data.getValue("password").equals(password)) storedPassword =
                        true else storedPassword = false

                    if (storedEmail && storedPassword) {
                        isMember = true

                        // autoCheck 여부 저장
                        mDatabase.collection("member").document(document.id)
                            .update("autologin", autologin)
                    }
                }
                callback.invoke(isMember)
            }
            .addOnFailureListener { it ->
                isMember = false
                callback.invoke(isMember)
            }
    }


    override fun onDestroyView() {
        // onDestroyView 에서 binding class 인스턴스 참조를 정리.
        mBinding = null
        super.onDestroyView()
    }
}