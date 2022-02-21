package com.example.cafeappproject.fragment

import android.content.Context
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.cafeappproject.R
import com.example.cafeappproject.databinding.FragmentLoginBinding
import com.example.cafeappproject.databinding.FragmentMainBinding
import com.example.cafeappproject.databinding.FragmentSignUpBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class MainFragment : Fragment() {

    private var mBinding: FragmentSignUpBinding? = null
    private val binding get() = mBinding!!

    internal lateinit var viewpager : ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //mBinding = FragmentMainBinding.inflate(inflater, container, false)





        return inflater.inflate(R.layout.fragment_main, container, false)
    }


}


class ViewPagerAdapter(private val context: Context) : PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null

    var images = arrayOf(
        R.drawable.slider_coffee
    )

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.fragment_main_sliderimage, null)
        val image = v.findViewById<View>(R.id.slider_imageView) as ImageView

        image.setImageResource(images[position])
        val vp = container as ViewPager
        vp.addView(v, 0)

        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }
}