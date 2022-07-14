package com.cuongpq.basemvvm.ui.main.user

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.cuongpq.basemvvm.common.Constants
import com.cuongpq.basemvvm.data.local.AppDatabase
import com.cuongpq.basemvvm.data.model.User
import com.cuongpq.basemvvm.data.remote.ApiHelp
import com.cuongpq.basemvvm.data.remote.ApiUser
import com.cuongpq.basemvvm.data.remote.InteractCommon
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference
import java.util.concurrent.Executor
import javax.inject.Inject

class UserViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<UserCallBack>(appDatabase, interactCommon, scheduler) {
    val mIsRefreshing = ObservableBoolean(false)
    private val api: ApiUser
    val obUsers = MutableLiveData<MutableList<User>>()
        get

    init {
        api = ApiHelp.createRetrofit(endpoint = Constants.BASE_URL, formatDate = Constants.LIST_FORMAT_TIME).create(
            ApiUser::class.java)
        getUsers()
    }

    fun getUsers() : Disposable?{
        setIsLoading(true)
        return subscribeHasResultDispose(
            api.getUsers().subscribeOn(Schedulers.from(schedulers))
                .observeOn(Schedulers.computation()),
            {
                setIsLoading(false)
                mIsRefreshing.set(false)
                makeFunOnOtherThread {
                    appDatabase.userDao().insertListUser(it.data!!)
                }
                obUsers.postValue(it.data)
            },{
                mIsRefreshing.set(false)
                setIsLoading(false)
                callBack?.get()?.error(User::class.java.name, it)
            }
        )
    }

    fun getAllUserOffline(){
        obUsers.postValue(appDatabase.userDao().findAll())
    }

    fun onRefresh(){
        mIsRefreshing.set(true)
        getUsers()
    }
}