package com.example.cafeappproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.cafeappproject.R
import com.example.cafeappproject.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var mBinding: FragmentProfileBinding? = null
    private val binding get() = mBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        // 뒤로가기 버튼
        binding.idBtnProfileBackspace.setOnClickListener { it ->
            it.findNavController().navigate(R.id.action_settingFragment_to_mainFragment)
        }


        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

}