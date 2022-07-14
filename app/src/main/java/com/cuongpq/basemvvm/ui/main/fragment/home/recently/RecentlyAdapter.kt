package com.cuongpq.basemvvm.ui.main.fragment.home.recently

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cuongpq.basemvvm.data.model.Recently
import com.cuongpq.basemvvm.databinding.ItemRecentlyBinding


class RecentlyAdapter(private val interfaces : IRecently):
RecyclerView.Adapter<RecentlyAdapter.Companion.RecentlyViewHolder>(){

    companion object{
        class RecentlyViewHolder(val binding: ItemRecentlyBinding) : RecyclerView.ViewHolder(binding.root)
    }
    interface IRecently{
        fun countRecently():Int
        fun getDataRecently(position:Int):Recently
        fun contextRecently():Context
        fun onClickItemRecently(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyViewHolder {
        val binding = ItemRecentlyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecentlyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentlyViewHolder, position: Int) {
        val recently = interfaces.getDataRecently(position)
        holder.binding.txtName.text = recently.getName()
        holder.binding.txtDescription.text = recently.getDescription()
        holder.binding.txtPrice.text = "$ " + recently.getPrice()
        holder.binding.txtUnit.text =""+recently.getUnit()+" KG"
        Glide.with(interfaces.contextRecently()).load(recently.getImage()).into(holder.binding.imgRecently)
        holder.itemView.setOnClickListener {
            interfaces.onClickItemRecently(position)
        }
        holder.binding.executePendingBindings()

    }

    override fun getItemCount(): Int {
        return interfaces.countRecently()
    }
}