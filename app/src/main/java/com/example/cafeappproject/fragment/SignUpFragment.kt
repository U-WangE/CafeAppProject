package com.example.cafeappproject.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cafeappproject.R
import com.example.cafeappproject.databinding.FragmentSignUpBinding
import com.google.firebase.database.FirebaseDatabase

class SignUpFragment : Fragment() {

    private var mBinding : FragmentSignUpBinding? = null
    private val binding get() = mBinding!!

    private val mDatabase = FirebaseDatabase.getInstance().getReference()
    data class User(var nickname: String, var email: String, var password: String)



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentSignUpBinding.inflate(inflater, container, false)

        // NicknameCheck
        binding.idBtnSignupNicknamecheck.setOnClickListener {
            when(nicknameRules(binding.idTxtSignupNickname.text.toString())) {
                1 -> Toast.makeText(activity, R.string.correct_input, Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(activity, R.string.overlap_input, Toast.LENGTH_SHORT).show()
                3 -> Toast.makeText(activity, R.string.invalid_input, Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(activity, R.string.non_input, Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    // 올바른 nickname인지 검사
    // 1: 올바른 입력 o, 중복x
    // 2: 올바른 입력 o, 중복o
    // 3: 올바른 입력 x
    // 4: null
    private fun nicknameRules(nickname: String?) : Int {
        if(nickname.isNullOrEmpty()) return 4
        val exp = Regex("^[가-힣ㄱ-ㅎa-zA-Z0-9._ -]{2,}\$")
        if(!exp.matches(nickname.trim())) return 3
        else if(overlapCheck(nickname.trim())) return 1 else return 2
    }

    // firebase 검색 어떻게 씀?ㅋㅋ
    private fun overlapCheck(nickname: String): Boolean {
        mDatabase.child("member").child("User").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return true
    }


    // 프래그먼트가 destroy (파괴) 될때..
    override fun onDestroyView() {
        // onDestroyView 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroyView()
    }

}