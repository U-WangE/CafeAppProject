package com.example.cafeappproject.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.cafeappproject.R
import com.example.cafeappproject.databinding.FragmentConnectScreenBinding

class ConnectScreenFragment : Fragment() {
    private var mBinding: FragmentConnectScreenBinding? = null

    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentConnectScreenBinding.inflate(inflater, container, false)

        // 화면 클릭 Event
        binding.idTxtClick.setOnClickListener { view ->
            if (activity?.let { it -> MySharedPreferences.getAutoLogin(it) }!!) {  // autoLogin 가능 여부 확인
                if (!activity?.let { it -> MySharedPreferences.getUserEmail(it) }  // MySharedPreference 내에 회원 정보 유무
                        .isNullOrBlank() && !activity?.let { it ->
                        MySharedPreferences.getUserPassword(
                            it
                        )
                    }.isNullOrBlank()) {
                    Toast.makeText(activity, R.string.Login, Toast.LENGTH_SHORT).show()

                    view.findNavController()  // autoLogin success | ConnectScreenFragment -> MainFragment 화면 이동
                        .navigate(R.id.action_connectScreenFragment_to_mainFragment)

                } else {  // autoLogin fail | ConnectScreenFragment -> LoginFragment 화면 이동
                    view.findNavController()
                        .navigate(R.id.action_connectScreenFragment_to_loginFragment)
                }
            } else {  // autoLogin false | ConnectScreenFragment -> LoginFragment 화면 이동
                view.findNavController()
                    .navigate(R.id.action_connectScreenFragment_to_loginFragment)
            }
        }

        return binding.root
    }


    override fun onDestroyView() {
        // onDestroyView 에서 binding class 인스턴스 참조를 정리.
        mBinding = null
        super.onDestroyView()
    }
}