package com.example.cafeappproject.fragment

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.os.Message
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

    private val arrImg: ArrayList<Int> = ArrayList()
    private val imgSliderHander = ImageSliderHandler()
    private var imgCurrentPosition = 0          // slider position
    private val intervalTime = 1500.toLong()    // for autoscroll, 1500ms = 1.5s

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

        // auto scroll image slider
        viewPager2?.setCurrentItem(imgCurrentPosition, false)
        viewPager2?.apply {
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart(intervalTime)
                        ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()
                    }
                }
            })
        }
    }

    private fun dpToPx(dp: Int): Int {
        val displayMetrics = resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }
    private fun pxToDp(px: Int): Int {
        val displayMetrics = resources.displayMetrics
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    private fun autoScrollStart(intervalTime: Long) {
        imgSliderHander.removeMessages(0)   // prevent handler duplication
        imgSliderHander.sendEmptyMessageDelayed(0, intervalTime)    // restart in $intervalTime
    }
    private fun autoScrollStop() {
        imgSliderHander.removeMessages(0)   // stop handler
    }
    private inner class ImageSliderHandler: Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {
                val viewPager2 = view?.findViewById<ViewPager2>(R.id.id_imageslider_main)
                imgCurrentPosition = viewPager2?.currentItem!!
                imgCurrentPosition++
                imgCurrentPosition %= arrImg.size
                viewPager2?.setCurrentItem(imgCurrentPosition, true)
                autoScrollStart(intervalTime)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        autoScrollStart(intervalTime)   // auto scroll imageSlider
    }
    override fun onPause() {
        super.onPause()
        autoScrollStop()    // stop auto scroll when away from MainFragment
    }
}
