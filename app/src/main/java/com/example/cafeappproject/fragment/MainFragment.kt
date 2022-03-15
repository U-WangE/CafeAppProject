package com.example.cafeappproject.fragment

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
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.cafeappproject.R
import com.example.cafeappproject.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var mBinding: FragmentMainBinding? = null
    private val binding get() = mBinding!!
    private val bindingDrawer get() = binding.idConstraintMainDrawer!!

    private val arrImg: ArrayList<Int> = ArrayList()    // Image Data for ImageSlider
    private val imgSliderHander = ImageSliderHandler()  // autoscroll handler for ImageSlider
    private var imgCurrentPosition = 0
    private val intervalTime = 3000.toLong()    // for autoscroll, 3000ms = 3s

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMainBinding.inflate(inflater, container, false)
        binding.idBtnMembershipMain.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_membershipFragment)
        }
        binding.idBtnMenuMain.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_menuFragment)
        }
        bindingDrawer.idBtnNotificationsDrawer.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_noticeFragment)
        }
        bindingDrawer.idBtnSettingsDrawer.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_settingFragment)
        }

        /*
            Drawer
                occupies along the display
                    at least: 0.8 of the system display width
                    at most: 300dp
                opened/closed by buttons

            ViewPager2
                automatically slides over (every 3s)
                infinite loop
                with indicator
        */

        // Drawer: set width dynamically
        val sysWidth = resources.displayMetrics.widthPixels // get current display width
        val sysWidthDP = pxToDp(sysWidth)                   // get current display width as DP
        val drawer = binding.idConstraintMainDrawer.idConstraintDrawerDrawer
        if (sysWidthDP != null) {
            if (sysWidthDP * 0.8 < 300) {
                // make the drawer's width as 0.8 of total width
                drawer?.layoutParams?.width = dpToPx((sysWidthDP * 0.8).toInt())
            }
            else {
                // fix the drawer's width to be 300dp if exceeds
                drawer?.layoutParams?.width = dpToPx(300)
            }
        }
        // Drawer: menu btn clicked -> open drawer
        val drawerLayout = binding.idDrawerMainDrawer
        val openDrawer = binding.idBtnMainOpendrawer
        openDrawer?.setOnClickListener {
            if (!drawerLayout!!.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.openDrawer(Gravity.RIGHT)  // open drawer if not opened
            }
        }
        // Drawer: cancel btn clicked -> close drawer
        val closeDrawer = bindingDrawer.idBtnCloseDrawer
        closeDrawer?.setOnClickListener {
            if (drawerLayout!!.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.closeDrawer(Gravity.RIGHT) // close drawer if not closed
            }
        }

        // ImageSlider: store images
        val arrImgData = arrayListOf<Int>(
            R.drawable.slider_coffee,
            R.drawable.slider_latte,
            R.drawable.slider_strawberry
        )
        arrImg.add(arrImgData[arrImgData.size - 1]) // trick for infinite loop
        arrImg.addAll(arrImgData)
        arrImg.add(arrImgData[0])

        // ImageSlider: connect adapter
        val adapter = MainSliderAdapter(arrImg)
        val viewPager2 = binding.idVp2MainImageslider
        viewPager2?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2?.adapter = adapter

        // ImageSlider: set indicator
        val indicator = binding.idLibIndicatorMain
        indicator?.createIndicators(arrImgData.size, 1)
        indicator?.animatePageSelected(0)

        // ImageSlider: auto scroll
        // ImageSlider: infinite loop
        viewPager2?.setCurrentItem(imgCurrentPosition, false)
        viewPager2?.apply {
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    // infinite loop
                    if (state == ViewPager2.SCROLL_STATE_IDLE
                        || state == ViewPager2.SCROLL_STATE_DRAGGING) {
                        if (currentItem == 0) {
                            // Reached Front, Change Index to Last
                            setCurrentItem(arrImg.size - 2, false)
                        }
                        else if (currentItem == arrImg.size - 1) {
                            // Reached Rear, Change Index to First
                            setCurrentItem(1, false)
                        }
                    }
                    // auto scroll
                    when (state) {
                        ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart(intervalTime)
                        ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()
                    }
                    indicator?.animatePageSelected(currentItem - 1)
                }
            })
            setCurrentItem(1, false)
        }

        return binding.root
    }

    // calculator for drawer
    private fun dpToPx(dp: Int): Int {
        val displayMetrics = resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }
    private fun pxToDp(px: Int): Int {
        val displayMetrics = resources.displayMetrics
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    // autoscroll for ImageSlider
    private fun autoScrollStart(intervalTime: Long) {
        imgSliderHander.removeMessages(0)   // prevent handler duplication
        imgSliderHander.sendEmptyMessageDelayed(0, intervalTime)    // resend message in $intervalTime
    }
    private fun autoScrollStop() {
        imgSliderHander.removeMessages(0)   // stop handler
    }

    // Message Handler for ImageSlider
    private inner class ImageSliderHandler: Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {
                val viewPager2 = view?.findViewById<ViewPager2>(R.id.id_vp2_main_imageslider)
                imgCurrentPosition = viewPager2?.currentItem!!
                imgCurrentPosition++    // move to next slide
                imgCurrentPosition %= arrImg.size   // not needed in infinite slide, but for defensive
                viewPager2?.setCurrentItem(imgCurrentPosition, true)
                autoScrollStart(intervalTime) // start autoscroll again
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

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}
