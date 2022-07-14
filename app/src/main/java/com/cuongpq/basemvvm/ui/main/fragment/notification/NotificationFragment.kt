package com.cuongpq.basemvvm.ui.main.fragment.notification

import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.databinding.FragmentNotificationsBinding
import com.cuongpq.basemvvm.ui.base.fragment.BaseMvvmFragment

class NotificationFragment : BaseMvvmFragment<NotificationCallBack, NotificationViewModel>(), NotificationCallBack {
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }

    override fun getLayoutMain() = R.layout.fragment_notifications

    override fun setEvents() {

    }

    override fun initComponents() {
        getBindingData().notificationViewModel = mModel
    }


    override fun getBindingData() = mBinding as FragmentNotificationsBinding

    override fun getViewModel(): Class<NotificationViewModel> {
        return NotificationViewModel::class.java
    }
}
