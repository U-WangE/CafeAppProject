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
import com.example.cafeappproject.databinding.FragmentSettingBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SettingFragment : Fragment() {
    private var mBinding: FragmentSettingBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentSettingBinding.inflate(inflater, container, false)

        // setting fragment 실행시 사용자의 설정 불러오기
        // autologin 사용 여부
        binding.idSwitchSettingAutologin.isChecked =
            activity?.let { it -> MySharedPreferences.getAutoLogin(it) }!!

        // 뒤로가기 버튼
        binding.idBtnSettingBackspace.setOnClickListener { it ->
            it.findNavController().navigate(R.id.action_settingFragment_to_mainFragment)
        }

        // 로그아웃
        binding.idBtnSettingLogout.setOnClickListener { it ->
            it.findNavController().navigate(R.id.action_settingFragment_to_loginFragment)
        }

        // autologin change
        binding.idSwitchSettingAutologin.setOnCheckedChangeListener { buttonView, isChecked ->
            activity?.let { context ->
                MySharedPreferences.setAutoLogin(context, isChecked)
                ChangeAutoLogin(isChecked) { it ->
                    if (it) {
                        Toast.makeText(
                            context,
                            R.string.autologin_setting_success_change,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Toast.makeText(
                            context,
                            R.string.autologin_setting_failure_change,
                            Toast.LENGTH_SHORT
                        )
                    }
                }
            }
        }

        return binding.root
    }

    // Database내의 해당 member의 autologin 변경
    fun ChangeAutoLogin(isCheck: Boolean, callback: (Boolean) -> Unit) {
        val mDatabase = Firebase.firestore

        mDatabase.collection("member")
            .get()
            .addOnSuccessListener { it ->
                for (document in it) {
                    if (document.data.getValue("email")
                            .equals(activity?.let { context ->
                                MySharedPreferences.getUserEmail(
                                    context
                                )
                            })
                    ) {  // autologin update
                        mDatabase.collection("member").document(document.id).update("autologin", isCheck)
                        callback.invoke(true)
                    }
                }
                callback.invoke(false)
            }
            .addOnFailureListener { it ->
                Log.e("AutoLogin Switch => ", "Error getting Change AutoLogin Setting", it)
            }
    }

}