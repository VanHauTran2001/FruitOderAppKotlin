package com.cuongpq.basemvvm.ui.main.activity.details

import android.content.Context
import com.cuongpq.basemvvm.data.local.AppDatabase
import com.cuongpq.basemvvm.data.remote.InteractCommon
import com.cuongpq.basemvvm.data.sqlite.SQLiteHelper
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.concurrent.Executor
import javax.inject.Inject
import kotlin.properties.Delegates

class DetailsViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<DetailsCallBack>(appDatabase,interactCommon,scheduler){

    private var sqLiteHelper: SQLiteHelper? = null
    private var idAccount: String? = null
    private var firebaseUser: FirebaseUser? = null
    var idSP by Delegates.notNull<Int>()
    lateinit var nameSP: String
    var priceSP by Delegates.notNull<Int>()
    var unitSP by Delegates.notNull<Int>()
    lateinit var imageSP: String
    init {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
    }
    companion object{
        const val CLICK_BACK = 1001
        const val CLICK_BUY = 1002
    }
    fun onClickBack(){
        uiEventLiveData.value = CLICK_BACK
    }
    fun onClickBuy(){
        uiEventLiveData.value = CLICK_BUY
    }
    fun addToCart(context: Context){
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
        sqLiteHelper = SQLiteHelper(context,"Fruit.DB",null,1)
        sqLiteHelper!!.QueryData("INSERT INTO Cart VALUES(null,'$idAccount','$idSP','$nameSP','$priceSP','$unitSP','$imageSP')")
    }
    fun clickBuy(context: Context){
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
        sqLiteHelper = SQLiteHelper(context,"Fruit.DB",null,1)
        sqLiteHelper!!.QueryData("INSERT INTO Cart VALUES(null,'$idAccount','$idSP','$nameSP','$priceSP','$unitSP','$imageSP')")
    }
}
