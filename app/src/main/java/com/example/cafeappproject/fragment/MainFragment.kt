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
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
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

        // Set Drawer Width
        val sysWidth = resources.displayMetrics.widthPixels
        val sysWidthDP = pxToDp(sysWidth)
        val drawer = getView()?.findViewById<ConstraintLayout>(R.id.id_drawer)
        val currentWidthDP = drawer?.layoutParams?.width?.let { pxToDp(it) }
        if (currentWidthDP != null) {
            if (currentWidthDP < 300) {
                drawer?.layoutParams?.width = sysWidth
            }
            else {
                drawer?.layoutParams?.width = dpToPx(300)
            }
        }

        // Open Drawer
        val drawerLayout = getView()?.findViewById<DrawerLayout>(R.id.id_drawerLayout)
        val openDrawer = getView()?.findViewById<ImageButton>(R.id.id_btn_drawer_open)
        openDrawer?.setOnClickListener {
            if (!drawerLayout!!.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.openDrawer(Gravity.RIGHT)
            }
        }

        // Close Drawer
        val closeDrawer = getView()?.findViewById<ImageButton>(R.id.id_btn_drawer_close)
        closeDrawer?.setOnClickListener {
            if (drawerLayout!!.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.closeDrawer(Gravity.RIGHT)
            }
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
