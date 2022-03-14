package com.example.cafeappproject.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import com.example.cafeappproject.R
import com.example.cafeappproject.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private var mBinding: ActivityProfileBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        mBinding = ActivityProfileBinding.inflate(layoutInflater)

        var spinner = binding.idSpinnerProfileSpinner
        spinner.adapter = ArrayAdapter.createFromResource(this, R.array.my_Profile_spinner, android.R.layout.simple_spinner_item)
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

                    }
                    1 -> {

                    }
                    2 -> {

                    }
                    else -> {
                        Log.e("ProfileSpinner => ", "Error getting ProfileSpinner")
                    }
                }
            }


        }


        setContentView(binding.root)
    }
}