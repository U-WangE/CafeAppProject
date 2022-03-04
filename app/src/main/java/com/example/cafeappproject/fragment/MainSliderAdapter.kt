package com.example.cafeappproject.fragment

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeappproject.R

class MainSliderAdapter(imgData: ArrayList<Int>) :
        RecyclerView.Adapter<MainSliderAdapter.MyViewHolder>() {
    val arrData = imgData

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item: ImageView = itemView.findViewById(R.id.id_img_slide)
    }

    // Create ViewHolder and return it
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val context = parent.context
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.fragment_main_slide, parent, false)
        val viewHolder = MyViewHolder(view)
        return viewHolder
    }

    // Set data to be shown
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.item.setImageResource(arrData[position])
    }

    override fun getItemCount(): Int {
        return arrData.size
    }
}