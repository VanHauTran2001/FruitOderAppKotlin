package com.cuongpq.basemvvm.ui.main.fragment.home
import androidx.lifecycle.MutableLiveData
import com.cuongpq.basemvvm.data.local.AppDatabase
import com.cuongpq.basemvvm.data.model.Category
import com.cuongpq.basemvvm.data.model.Discount
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

class HomeViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<HomeCallBack>(appDatabase,interactCommon,scheduler){
    var obDiscount = MutableLiveData<MutableList<Discount>>()
    var obCategory = MutableLiveData<MutableList<Category>>()
    var obRecently = MutableLiveData<MutableList<Recently>>()
    private var compositeDisposable = CompositeDisposable()
    private var apiDiscount :APIService?=null


    init {
        apiDiscount = RetrofitClient.instance.create(APIService::class.java)
        getDataDiscounted()
        getDataCategory()
        getDataRecently()
    }

    private fun getDataRecently() {
        compositeDisposable!!.addAll(apiDiscount!!.getRecently
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                obRecently.value = it as MutableList<Recently>})
    }

    private fun getDataCategory() {
        compositeDisposable!!.addAll(apiDiscount!!.getCategory
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                obCategory.value = it as MutableList<Category> })
    }

    private fun getDataDiscounted() {
        compositeDisposable!!.addAll(apiDiscount!!.getDiscount
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                obDiscount.value = it as MutableList<Discount> })
    }


    companion object{

    }
    init {

    }
}


