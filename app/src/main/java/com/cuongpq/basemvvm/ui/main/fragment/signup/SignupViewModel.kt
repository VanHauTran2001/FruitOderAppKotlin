package com.cuongpq.basemvvm.ui.main.fragment.signup

import androidx.lifecycle.MutableLiveData
import com.cuongpq.basemvvm.data.local.AppDatabase
import com.cuongpq.basemvvm.data.remote.InteractCommon
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.cuongpq.basemvvm.ui.main.fragment.signin.SigninViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.concurrent.Executor
import javax.inject.Inject


class SignupViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor,
) : BaseViewModel<SignupCallBack>(appDatabase,interactCommon,scheduler){
    lateinit var emailAddress: String
    lateinit var username: String
    lateinit var password: String
    lateinit var confirmPassword: String
    lateinit var phoneNumber: String
    private var auth: FirebaseAuth?=null
    private var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser>? = null

    companion object {

        const val NAV_GET_DATA_FROM_UI_AND_REGISTER = 1005
        const val NAV_REGISTER_SUCCESS = 1006
        const val NAV_REGISTER_ERROR = 1007
        const val DIALOG_REGISTER_SHOW = 1008
        const val DIALOG_REGISTER_DIS = 1009
    }
    init {
        firebaseUserMutableLiveData = MutableLiveData()
       auth = FirebaseAuth.getInstance()
        if (auth!!.currentUser !=null){
            firebaseUserMutableLiveData!!.postValue(auth!!.currentUser)
        }
    }

    fun onSignup(){
        uiEventLiveData.value = DIALOG_REGISTER_SHOW
        auth!!.createUserWithEmailAndPassword(emailAddress,password).addOnCompleteListener{task->
            uiEventLiveData.value = DIALOG_REGISTER_DIS
            if (task.isSuccessful){
                uiEventLiveData.value = NAV_REGISTER_SUCCESS
            }else{
                uiEventLiveData.value = NAV_REGISTER_ERROR
            }
        }
    }
    fun performSignUp(){
        uiEventLiveData.value = NAV_GET_DATA_FROM_UI_AND_REGISTER
    }
}