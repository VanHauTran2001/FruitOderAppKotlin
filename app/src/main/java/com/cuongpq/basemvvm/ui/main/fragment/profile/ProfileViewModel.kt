package com.cuongpq.basemvvm.ui.main.fragment.profile

import com.cuongpq.basemvvm.data.local.AppDatabase
import com.cuongpq.basemvvm.data.remote.InteractCommon
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import java.util.concurrent.Executor
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<ProfileCallBack>(appDatabase,interactCommon,scheduler){
    companion object{
        const val CLICK_LOGOUT = 1007
        const val CLICK_IMAGE = 1008
        const val CLICK_UPDATE = 1009
    }
    fun onUpdate(){
        uiEventLiveData.value = CLICK_UPDATE
    }
    fun onLogout(){
        uiEventLiveData.value= CLICK_LOGOUT
    }
    fun onClickAvata(){
        uiEventLiveData.value = CLICK_IMAGE
    }
}

