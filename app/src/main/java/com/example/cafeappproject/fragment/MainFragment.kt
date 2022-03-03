package com.example.cafeappproject.fragment

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.cafeappproject.R
import com.example.cafeappproject.databinding.FragmentLoginBinding
import com.example.cafeappproject.databinding.FragmentMainBinding
import com.example.cafeappproject.databinding.FragmentSignUpBinding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class MainFragment : Fragment() {

    private var mBinding: FragmentMainBinding? = null
    private val binding get() = mBinding!!

    private val imgList = mutableListOf<Image>()
    //private lateinit var viewPager2: ViewPager2

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

        /*



            Drawer



         */


        // Drawer: set width dynamically
        val sysWidth = resources.displayMetrics.widthPixels
        val sysWidthDP = pxToDp(sysWidth)
        val drawer = getView()?.findViewById<ConstraintLayout>(R.id.id_drawer)
        if (sysWidthDP != null) {
            if (sysWidthDP * 0.8 < 300) {
                drawer?.layoutParams?.width = dpToPx((sysWidthDP * 0.8).toInt())
            }
            else {
                drawer?.layoutParams?.width = dpToPx(300)
            }
        }
        // Drawer: open drawer
        val drawerLayout = getView()?.findViewById<DrawerLayout>(R.id.id_drawerLayout)
        val openDrawer = getView()?.findViewById<ImageButton>(R.id.id_btn_drawer_open)
        openDrawer?.setOnClickListener {
            if (!drawerLayout!!.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.openDrawer(Gravity.RIGHT)
            }
        }
        // Drawer: close drawer
        val closeDrawer = getView()?.findViewById<ImageButton>(R.id.id_btn_drawer_close)
        closeDrawer?.setOnClickListener {
            if (drawerLayout!!.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.closeDrawer(Gravity.RIGHT)
            }
        }

        /*



            ViewPager2



         */
        // images for image slider
        val arrImg: ArrayList<Int> = ArrayList()
        arrImg.add(R.drawable.slider_coffee)
        arrImg.add(R.drawable.slider_latte)
        arrImg.add(R.drawable.slider_strawberry)

        // connect adapter
        val adapter = MainSliderAdapter(arrImg)
        val viewPager2 = getView()?.findViewById<ViewPager2>(R.id.id_imageslider_main)
        viewPager2?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2?.adapter = adapter

        // connect indicator
        val indicator = getView()?.findViewById<DotsIndicator>(R.id.id_indicator_main)
        if (viewPager2 != null) {
            indicator?.setViewPager2(viewPager2)
        }
    }

    fun dpToPx(dp: Int): Int {
        val displayMetrics = resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }
    fun pxToDp(px: Int): Int {
        val displayMetrics = resources.displayMetrics
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }


}
