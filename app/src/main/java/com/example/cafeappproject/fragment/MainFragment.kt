package com.example.cafeappproject.fragment

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.cafeappproject.R
import com.example.cafeappproject.databinding.FragmentLoginBinding
import com.example.cafeappproject.databinding.FragmentMainBinding
import com.example.cafeappproject.databinding.FragmentSignUpBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class MainFragment : Fragment() {

    private var mBinding: FragmentMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //mBinding = FragmentMainBinding.inflate(inflater, container, false)





        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drawer = getView()?.findViewById<DrawerLayout>(R.id.id_drawer)
        val button_toolbar = getView()?.findViewById<ImageButton>(R.id.id_btn_toolbar_menu)
        button_toolbar?.setOnClickListener {
            if (drawer!!.isDrawerOpen(Gravity.LEFT)) {
                drawer.closeDrawer(Gravity.LEFT)
            }
            else {
                drawer.openDrawer(Gravity.LEFT)
            }
        }
    }
}
