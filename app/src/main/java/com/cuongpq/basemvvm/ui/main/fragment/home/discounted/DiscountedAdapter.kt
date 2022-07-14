package com.cuongpq.basemvvm.ui.main.fragment.home.discounted

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cuongpq.basemvvm.data.model.Discount
import com.cuongpq.basemvvm.databinding.ItemDiscountedBinding

class DiscountedAdapter(private val inter : IDiscouted) : RecyclerView.Adapter<DiscountedAdapter.Companion.DiscountedViewHodler>(){

    companion object{
        class DiscountedViewHodler(val binding : ItemDiscountedBinding) : RecyclerView.ViewHolder(binding.root)
    }
    interface IDiscouted{
        fun count() : Int
        fun getData(position : Int) : Discount
        fun context() : Context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountedViewHodler {
        val binding = ItemDiscountedBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DiscountedViewHodler(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: DiscountedViewHodler, position: Int) {
        val discount = inter.getData(position)
        holder.binding.txtNameItem.text = discount.getNameItem()
        holder.binding.txtSale.text = discount.getSale()
        Glide.with(inter.context()).load(discount.getImageItem()).into(holder.binding.imgItem)
        holder.binding.executePendingBindings()
//        when (position) {
//            0 -> {
//                holder.binding.cardView.setCardBackgroundColor(android.R.color.holo_red_dark)
//            }
//            1 -> {
//                holder.binding.cardView.setCardBackgroundColor(android.R.color.holo_orange_light)
//            }
//            2 -> {
//                holder.binding.cardView.setCardBackgroundColor(android.R.color.holo_green_dark)
//            }
//            3 -> {
//                holder.binding.cardView.setCardBackgroundColor(android.R.color.holo_orange_dark)
//            }
//        }
    }

    override fun getItemCount(): Int {
        return inter.count()
    }
}