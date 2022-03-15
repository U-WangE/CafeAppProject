package com.example.cafeappproject.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
        mBinding = FragmentProfileBinding.inflate(layoutInflater)

        var spinner = binding.idSpinnerProfileSpinner
        spinner.adapter = activity?.let { it ->
            ArrayAdapter.createFromResource(
                it,
                R.array.my_Profile_spinner,
                android.R.layout.simple_spinner_item
            )
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position) {
                    0 -> {
                        activity?.let { it ->
                            it.supportFragmentManager.beginTransaction()
                                .replace(R.id.id_profile_fragment, MyCrownFragment()).commit()
                        }
                    }
                    1 -> {
                        activity?.let { it ->
                            it.supportFragmentManager.beginTransaction()
                                .replace(R.id.id_profile_fragment, CrownUsageHistoryFragment())
                                .commit()
                        }
                    }
                    2 -> {
                        activity?.let { it ->
                            it.supportFragmentManager.beginTransaction().replace(
                                R.id.id_profile_fragment,
                                MembershipCardManagementFragment()
                            ).commit()
                        }
                    }
                    else -> {
                        Log.e("ProfileSpinner => ", "Error getting ProfileSpinner")
                    }
                }
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