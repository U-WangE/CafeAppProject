package com.example.cafeappproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cafeappproject.R
import com.example.cafeappproject.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var mBinding: FragmentMenuBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentMenuBinding.inflate(inflater, container, false)

        val tb = binding.idToolbarMenuToolbar

        return binding.root
    }

}