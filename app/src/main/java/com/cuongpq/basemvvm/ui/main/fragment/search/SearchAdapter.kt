package com.cuongpq.basemvvm.ui.main.fragment.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cuongpq.basemvvm.data.model.Recently
import com.cuongpq.basemvvm.databinding.ItemSearchBinding

class SearchAdapter(private val interSearch : ISearch)
    :RecyclerView.Adapter<SearchAdapter.Companion.SearchViewHolder>(){
    companion object{
        class SearchViewHolder(val binding : ItemSearchBinding):RecyclerView.ViewHolder(binding.root)
    }
    init {

    }
    interface ISearch{
        fun getCountSeachr():Int
        fun getDataSearch(position:Int):Recently
        fun getContextSearch():Context
        fun onClickItemSearch(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val dataSearch = interSearch.getDataSearch(position)
        holder.binding.txtNameSearch.text = dataSearch.getName()
        holder.binding.txtDescriptionSearch.text = dataSearch.getDescription()
        holder.binding.txtPriceSearch.text = "$" + dataSearch.getPrice()
        holder.binding.txtUnitSearch.text =""+dataSearch.getUnit()+" KG"
        Glide.with(interSearch.getContextSearch()).load(dataSearch.getImage()).into(holder.binding.imgSpSearch)
        holder.itemView.setOnClickListener {
            interSearch.onClickItemSearch(position)
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return interSearch.getCountSeachr()
    }
}