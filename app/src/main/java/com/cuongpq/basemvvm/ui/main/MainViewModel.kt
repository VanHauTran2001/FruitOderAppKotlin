package com.cuongpq.basemvvm.ui.main

import com.cuongpq.basemvvm.data.local.AppDatabase
import com.cuongpq.basemvvm.data.remote.InteractCommon
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import java.util.concurrent.Executor
import javax.inject.Inject

class MainViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<MainCallBack>(appDatabase, interactCommon, scheduler) {

   init {

   }
    companion object{
        const val CLICK_IMG_CART = 1001
    }
    fun onClickToCart(){
        uiEventLiveData.value = CLICK_IMG_CART
    }
}