package com.cuongpq.basemvvm.ui.main.fragment.signin

import androidx.lifecycle.MutableLiveData
import com.cuongpq.basemvvm.data.local.AppDatabase
import com.cuongpq.basemvvm.data.remote.InteractCommon
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.concurrent.Executor
import javax.inject.Inject

class SigninViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<SignInCallBack>(appDatabase,interactCommon,scheduler){
    lateinit var email:String
    lateinit var password:String
    private var auth: FirebaseAuth?=null
    private var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser>? = null
    companion object{
        const val VISIBLE_PASSWORD = 1005
        const val NAV_SIGNIN_SUCCESS = 1006
        const val NAV_SIGNIN_ERROR = 1007
        const val NAV_GET_DATA_FROM_UI_AND_SIGNIN = 1008
        const val DIALOG_SHOW = 1009
        const val DIALOG_DIS = 1010
    }
    init {
        firebaseUserMutableLiveData = MutableLiveData()
       auth = FirebaseAuth.getInstance()
       if (auth!!.currentUser !=null){
            firebaseUserMutableLiveData!!.postValue(auth!!.currentUser)
        }
    }
    fun onSignin(){
        uiEventLiveData.value = DIALOG_SHOW
        auth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            uiEventLiveData.value = DIALOG_DIS
            if (task.isSuccessful) {
                uiEventLiveData.value = NAV_SIGNIN_SUCCESS

            } else {
                uiEventLiveData.value = NAV_SIGNIN_ERROR
            }
        }
    }
    fun performSignIn(){
        uiEventLiveData.value = NAV_GET_DATA_FROM_UI_AND_SIGNIN
    }
    fun onClickVisible(){
        uiEventLiveData.value = VISIBLE_PASSWORD
    }

}