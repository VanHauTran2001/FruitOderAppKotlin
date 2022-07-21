package com.cuongpq.basemvvm.ui.main.fragment.notification

import android.content.Context
import com.cuongpq.basemvvm.data.local.AppDatabase
import com.cuongpq.basemvvm.data.model.Notifi
import com.cuongpq.basemvvm.data.remote.InteractCommon
import com.cuongpq.basemvvm.data.sqlite.SQLiteHelper
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.concurrent.Executor
import javax.inject.Inject

class NotificationViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<NotificationCallBack>(appDatabase,interactCommon,scheduler){
    var obListTB : ArrayList<Notifi>?=null
    var mNotifi : Notifi?=null
    private var firebaseUser: FirebaseUser? = null
    private var sqLiteHelper: SQLiteHelper? = null
    private var idAccount: String? = null

    init {
        obListTB = ArrayList()
        mNotifi = Notifi()
    }
    fun showDataTB(context: Context){
        sqLiteHelper = SQLiteHelper(context,"Fruit.DB",null,1)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        idAccount = firebaseUser!!.uid
        val data = sqLiteHelper!!.getData("SELECT * FROM Notification WHERE IdAccount = '$idAccount' ORDER BY IdTB DESC")
        while (data.moveToNext()){v
            val idTB = data.getInt(0)
            val textTB = data.getString(2)
            val hour = data.getString(3)
            val day = data.getString(4)
            obListTB!!.add(Notifi(idTB,textTB,hour,day))
        }
    }
    fun deleteTB(idTB : Int,context: Context){
     sqLiteHelper = SQLiteHelper(context,"Fruit.DB",null,1)
     sqLiteHelper!!.QueryData("DELETE FROM Notification WHERE IdTB = '$idTB'")
    }
//    fun getListNotification() : ArrayList<Notifi>{
//        return obListTB!!.reversed() as ArrayList<Notifi>
//    }
}
