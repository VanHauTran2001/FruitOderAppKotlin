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
import kotlin.properties.Delegates

class CartViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<CartCallBack>(appDatabase,interactCommon,scheduler){

    private var firebaseUser: FirebaseUser? = null
    private var sqLiteHelper: SQLiteHelper? = null
    private var idAccount: String? = null
    lateinit var text_TB : String
    lateinit var date_TB : String
    lateinit var hours : String
    companion object{
        const val BACK_STACK = 1002
        const val CONTINUE = 1003
        const val PAY = 1004
    }
    init {
//        obCart = ArrayList()
    }
    fun onBackStack(){
        uiEventLiveData.value = BACK_STACK
    }
    fun onContunue(){
        uiEventLiveData.value = CONTINUE
    }
    fun onPay(){
        uiEventLiveData.value = PAY
    }
    fun getNotification(context: Context){
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
        sqLiteHelper = SQLiteHelper(context,"Fruit.DB",null,1)
        sqLiteHelper!!.QueryData("INSERT INTO Notification VALUES(null,'$idAccount','$text_TB','$hours','$date_TB')")
    }
//     fun getDataCart(context: Context) {
//         sqLiteHelper = SQLiteHelper(context,"Fruit.DB",null,1)
//         firebaseUser = FirebaseAuth.getInstance().currentUser
//         idAccount = firebaseUser!!.uid
//         val data = sqLiteHelper!!.getData("SELECT * FROM Cart1 WHERE IdAccount = '$idAccount'")
//         while (data.moveToNext()){
//             val idSp = data.getInt(2)
//             val nameSP = data.getString(3)
//             val priceSP = data.getInt(4)
//             val unitSP = data.getInt(5)
//             val imageSP = data.getString(6)
//             val number = data.getInt(7)
//             obCart!!.add(Cart(idSp,nameSP,priceSP,unitSP,imageSP,number))
//         }
//         Utils.NUMBER = obCart!!.size
//    }
//    fun getDelete(idSp: Int?){
//        sqLiteHelper!!.QueryData("DELETE FROM Cart1 WHERE IdSp = '$idSp' ")
//    }
}