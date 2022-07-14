package com.cuongpq.basemvvm.ui.main.activity.cart

import android.content.Context
import androidx.lifecycle.MutableLiveData
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

class CartViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<CartCallBack>(appDatabase,interactCommon,scheduler){
    var obCart : ArrayList<Cart>?=null
    private var firebaseUser: FirebaseUser? = null
    private var sqLiteHelper: SQLiteHelper? = null
    private var idAccount: String? = null
    companion object{
        const val BACK_STACK = 1002
        const val CONTINUE = 1003
    }
    init {
        obCart = ArrayList()
    }
    fun onBackStack(){
        uiEventLiveData.value = BACK_STACK
    }
    fun onContunue(){
        uiEventLiveData.value = CONTINUE
    }
     fun getDataCart(context: Context) {
         sqLiteHelper = SQLiteHelper(context,"Fruit.DB",null,1)
         firebaseUser = FirebaseAuth.getInstance().currentUser
         idAccount = firebaseUser!!.uid
         val data = sqLiteHelper!!.getData("SELECT * FROM Cart WHERE IdAccount = '$idAccount'")
         while (data.moveToNext()){
             val idSp = data.getInt(2)
             val nameSP = data.getString(3)
             val priceSP = data.getInt(4)
             val unitSP = data.getInt(5)
             val imageSP = data.getString(6)
             obCart!!.add(Cart(idSp,nameSP,priceSP,unitSP,imageSP))
         }
//         Utils.NUMBER = obCart.value!!.size
    }
}