package com.cuongpq.basemvvm.ui.main.fragment.search

import androidx.lifecycle.MutableLiveData
import com.cuongpq.basemvvm.data.local.AppDatabase
import com.cuongpq.basemvvm.data.model.Recently
import com.cuongpq.basemvvm.data.remote.InteractCommon
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.cuongpq.basemvvm.ui.main.retrofit.APIService
import com.cuongpq.basemvvm.ui.main.retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<SearchCallBack>(appDatabase,interactCommon,scheduler){
    var obListSearch = MutableLiveData<MutableList<Recently>>()
    private var compositeDisposable = CompositeDisposable()
    private var apiDiscount : APIService?=null
    init {
       apiDiscount = RetrofitClient.instance.create(APIService::class.java)
    }

    fun getDataSearch(edtSearch:String) {
        compositeDisposable!!.addAll(apiDiscount!!.getSearch(edtSearch)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                obListSearch.value = it as MutableList<Recently>
            })
    }
}
