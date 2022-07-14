package com.cuongpq.basemvvm.ui.main.activity.slash

import com.cuongpq.basemvvm.data.local.AppDatabase
import com.cuongpq.basemvvm.data.remote.InteractCommon
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import java.util.concurrent.Executor
import javax.inject.Inject

class SlashViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<SlashCallBack>(appDatabase,interactCommon,scheduler){
    companion object{
        val START_LOGIN_ACTIVITY = 1002
    }
    fun onStart(){
        uiEventLiveData.value= START_LOGIN_ACTIVITY;
    }
}