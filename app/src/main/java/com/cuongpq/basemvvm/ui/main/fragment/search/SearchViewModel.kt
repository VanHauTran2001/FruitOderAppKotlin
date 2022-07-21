package com.cuongpq.basemvvm.ui.main.fragment.search


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
    var obListSearch :ArrayList<Recently> = ArrayList()
    private var compositeDisposable = CompositeDisposable()
    private var apiDiscount : APIService?=null

    init {
       apiDiscount = RetrofitClient.instance.create(APIService::class.java)
    }

    fun getDataSearch(keySearch : String) {
        obListSearch.clear()
        compositeDisposable!!.add(apiDiscount!!.getSearch(keySearch)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                obListSearch = it as ArrayList<Recently>
            })
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
