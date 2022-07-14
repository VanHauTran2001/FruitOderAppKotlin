package com.cuongpq.basemvvm.ui.main.fragment.home.category

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cuongpq.basemvvm.data.model.Category
import com.cuongpq.basemvvm.databinding.ItemCategoriesBinding

class CategoryAdapter(private val inters : ICategory):
    RecyclerView.Adapter<CategoryAdapter.Companion.CategoryViewHolder>() {

    companion object{
        class CategoryViewHolder(val binding : ItemCategoriesBinding) : RecyclerView.ViewHolder(binding.root)

    }
    interface ICategory{
        fun countCategory() : Int
        fun getDataCategory(position : Int):Category
        fun contextCategory() : Context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
       val binding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = inters.getDataCategory(position)
        holder.binding.txtNameCategory.text = category.getNameCategory()
        Glide.with(inters.contextCategory()).load(category.getImageCategory()).into(holder.binding.imgCategory)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return inters.countCategory()
    }
}