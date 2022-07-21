package com.cuongpq.basemvvm.ui.main.fragment.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cuongpq.basemvvm.data.model.Notifi
import com.cuongpq.basemvvm.databinding.ItemNotificationBinding
class NotificationAdapter (private val interTB : INotification)
    :RecyclerView.Adapter<NotificationAdapter.Companion.NotificationViewHolder>(){
    companion object{
        class NotificationViewHolder(val binding : ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root)
    }
    interface INotification{
        fun getCountTB() : Int
        fun getDataTB(position:Int):Notifi
        fun getDeleteTB(position: Int)
        fun getListTB() : ArrayList<Notifi>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val dataTB = interTB.getDataTB(position)
        holder.binding.txtTB.text = dataTB.getTextTB()
        holder.binding.txtHour.text = dataTB.getHour()
        holder.binding.txtDay.text = dataTB.getDay()
        holder.binding.btnDeleteTB.setOnClickListener {
            interTB.getDeleteTB(position)
//            removeItem(position)
        }
    }
    fun removeItem(position: Int){
        interTB.getListTB().removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,interTB.getListTB().size)
    }
    override fun getItemCount(): Int {
        return interTB.getCountTB()
    }
}