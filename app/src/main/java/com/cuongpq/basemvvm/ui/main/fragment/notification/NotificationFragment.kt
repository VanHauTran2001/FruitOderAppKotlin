package com.cuongpq.basemvvm.ui.main.fragment.notification

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.data.model.Notifi
import com.cuongpq.basemvvm.databinding.FragmentNotificationsBinding
import com.cuongpq.basemvvm.ui.base.fragment.BaseMvvmFragment
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.cuongpq.basemvvm.ui.utils.Utils

class NotificationFragment : BaseMvvmFragment<NotificationCallBack, NotificationViewModel>(), NotificationCallBack,NotificationAdapter.INotification {
    private var mNotifi : Notifi?=null
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }

    override fun getLayoutMain() = R.layout.fragment_notifications

    override fun setEvents() {

    }

    override fun initComponents() {
        getBindingData().notificationViewModel = mModel
        mNotifi = mModel.mNotifi
        mModel.uiEventLiveData.observe(this){
            when(it){
                BaseViewModel.FINISH_ACTIVITY -> finishActivity()
            }
        }
        onShowDataNotification()
        intRecyler()
    }

    @SuppressLint("WrongConstant")
    private fun intRecyler() {
        val linearLayoutManager = LinearLayoutManager.VERTICAL
        getBindingData().recylerNotification.layoutManager = LinearLayoutManager(activity, linearLayoutManager, false)
        getBindingData().recylerNotification.adapter = NotificationAdapter(this@NotificationFragment)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun onShowDataNotification() {
        mModel.showDataTB(context!!)
    }


    override fun getBindingData() = mBinding as FragmentNotificationsBinding

    override fun getViewModel(): Class<NotificationViewModel> {
        return NotificationViewModel::class.java
    }

    override fun getCountTB(): Int {
        if (mModel.obListTB == null){
            return 0
        }
        return mModel.obListTB!!.size
    }

    override fun getDataTB(position: Int): Notifi {
        return mModel.obListTB!![position]
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun getDeleteTB(position: Int) {
        val dialog = AlertDialog.Builder(context!!)
            .setTitle("Confirm Dialog")
            .setMessage("Are you sure you want to delete it ?")
            .setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                val tb = getListTB()[position]
                mModel.deleteTB(tb.getIdTB()!!, context!!)
                getListTB().removeAt(position)
                getBindingData().recylerNotification.adapter!!.notifyDataSetChanged()
                showMessage("Delete success ")
            }
            .setNegativeButton("No") { _: DialogInterface?, _: Int -> }
            .create()
        dialog.show()
    }

    override fun getListTB(): ArrayList<Notifi> {
        return mModel.obListTB!!
    }
}
