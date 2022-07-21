package com.cuongpq.basemvvm.ui.main.activity.cart

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cuongpq.basemvvm.data.model.Cart
import com.cuongpq.basemvvm.databinding.ItemCartBinding

class CartAdapter(private val interCar : ICart):
RecyclerView.Adapter<CartAdapter.Companion.CartViewHolder>(){
    companion object{
        class CartViewHolder(val binding : ItemCartBinding):RecyclerView.ViewHolder(binding.root)
    }
    interface ICart{
        fun getCount():Int
        fun getDataCart(position:Int):Cart
        fun getContext() : Context
        fun getDeleteItem(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val carts = interCar.getDataCart(position)
        holder.binding.txtNameCart.text = carts.getNameSP()
        holder.binding.txtNumberItem.text = "X"+carts.getNumber()
        holder.binding.txtPriceCart.text = "$ " + (carts.getPriceSP()!! * carts.getNumber()!!)
        holder.binding.txtUnitCart.text = ""+carts.getUnitSP()+ "KG"
        Glide.with(interCar.getContext()).load(carts.getImageSP()).into(holder.binding.imgSpCart)
        holder.binding.btnDelete.setOnClickListener {
            interCar.getDeleteItem(position)
        }
    }

    override fun getItemCount(): Int {
       return interCar.getCount()
    }
}