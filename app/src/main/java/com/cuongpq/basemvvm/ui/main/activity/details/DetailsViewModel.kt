package com.cuongpq.basemvvm.ui.main.activity.details

import android.content.Context
import com.cuongpq.basemvvm.data.local.AppDatabase
import com.cuongpq.basemvvm.data.model.Cart
import com.cuongpq.basemvvm.data.remote.InteractCommon
import com.cuongpq.basemvvm.data.sqlite.SQLiteHelper
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.cuongpq.basemvvm.ui.utils.Utils
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
    private var flag : Boolean?=null
    private var sqLiteHelper: SQLiteHelper? = null
    private var idAccount: String? = null
    private var firebaseUser: FirebaseUser? = null
    var idSP by Delegates.notNull<Int>()
    lateinit var nameSP: String
    var priceSP by Delegates.notNull<Int>()
    var unitSP by Delegates.notNull<Int>()
    lateinit var imageSP: String
    var number by Delegates.notNull<Int>()
    init {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
    }
    companion object{
        const val CLICK_BACK = 1001
        const val CLICK_BUY = 1002
        const val CLICK_ADD_CART = 1003
        const val CLICK_IMG_CART = 1004
    }
    fun onClickBack(){
        uiEventLiveData.value = CLICK_BACK
    }
    fun onClickBuy(){
        uiEventLiveData.value = CLICK_BUY
    }
    fun onClickAdd(){
        uiEventLiveData.value = CLICK_ADD_CART
    }
    fun onClickImg(){
        uiEventLiveData.value = CLICK_IMG_CART
    }
    fun addToCart(){
        var cart = Cart()
//        firebaseUser = FirebaseAuth.getInstance().currentUser
//        idAccount = firebaseUser!!.uid
//        sqLiteHelper = SQLiteHelper(context,"Fruit.DB",null,1)
//        sqLiteHelper!!.QueryData("INSERT INTO Cart1 VALUES(null,'$idAccount','$idSP','$nameSP','$priceSP','$unitSP','$imageSP','$number')")
        if (Utils.listCar!!.size>0){
             flag = false
            for (i in Utils.listCar!!.indices){
                if (Utils.listCar!![i].getIdSP()==idSP){
                    Utils.listCar!![i].setNumber(number+ Utils.listCar!![i].getNumber()!!)
                    val giaSp = priceSP* Utils.listCar!![i].getNumber()!!
                    Utils.listCar!![i].setPriceSP(giaSp/2)
                    flag = true
                }
            }
            if (flag==false){
                cart.setIdSP(idSP)
                cart.setNameSP(nameSP)
                cart.setPriceSP(priceSP)
                cart.setNumber(number)
                cart.setUnitSP(unitSP)
                cart.setImageSP(imageSP)
                Utils.listCar!!.add(cart)
            }
        }else{
            cart.setIdSP(idSP)
            cart.setNameSP(nameSP)
            cart.setPriceSP(priceSP)
            cart.setNumber(number)
            cart.setUnitSP(unitSP)
            cart.setImageSP(imageSP)
            Utils.listCar!!.add(cart)
        }
    }
    fun clickBuy(){
//        firebaseUser = FirebaseAuth.getInstance().currentUser
//        idAccount = firebaseUser!!.uid
//        sqLiteHelper = SQLiteHelper(context,"Fruit.DB",null,1)
//        sqLiteHelper!!.QueryData("INSERT INTO Cart1 VALUES(null,'$idAccount','$idSP','$nameSP','$priceSP','$unitSP','$imageSP','$number')")
        var cart = Cart()
        var sl = number
        if (Utils.listCar!!.size>0){
            flag = false
            for (i in Utils.listCar!!.indices){
                if (Utils.listCar!![i].getIdSP()==idSP){
                    Utils.listCar!![i].setNumber(sl+ Utils.listCar!![i].getNumber()!!)
                    val giaSp = priceSP* Utils.listCar!![i].getNumber()!!
                    Utils.listCar!![i].setPriceSP(giaSp/2)
                    flag = true
                }
            }
            if (flag==false){
                sl = number
                var priceNew = sl*priceSP
                cart.setIdSP(idSP)
                cart.setNameSP(nameSP)
                cart.setPriceSP(priceNew)
                cart.setNumber(number)
                cart.setUnitSP(unitSP)
                cart.setImageSP(imageSP)
                Utils.listCar!!.add(cart)
            }
        }else{
            sl = number
            var priceNew = sl*priceSP
            cart.setIdSP(idSP)
            cart.setNameSP(nameSP)
            cart.setPriceSP(priceNew)
            cart.setNumber(number)
            cart.setUnitSP(unitSP)
            cart.setImageSP(imageSP)
            Utils.listCar!!.add(cart)
        }
    }
}
