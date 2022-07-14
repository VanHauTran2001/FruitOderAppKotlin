package com.cuongpq.basemvvm.ui.main.fragment.signup

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.databinding.FragmentSignupBinding
import com.cuongpq.basemvvm.ui.base.fragment.BaseMvvmFragment
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.cuongpq.basemvvm.ui.main.MainActivity
import com.cuongpq.basemvvm.ui.main.activity.login.LoginActivity

class SignupFragment : BaseMvvmFragment<SignupCallBack,SignupViewModel>(),SignupCallBack{
    private var progressDialog : ProgressDialog?=null
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }


    override fun getLayoutMain() = R.layout.fragment_signup

    override fun setEvents() {

    }

    override fun initComponents() {
        getBindingData().signupViewModel = mModel
        progressDialog = ProgressDialog(context)
        mModel.uiEventLiveData.observe(this){
            when(it){
                BaseViewModel.FINISH_ACTIVITY -> finishActivity()
                SignupViewModel.NAV_GET_DATA_FROM_UI_AND_REGISTER ->startSignup()
                SignupViewModel.NAV_REGISTER_SUCCESS -> onSignupSuccess()
                SignupViewModel.NAV_REGISTER_ERROR ->onSignupError()
                SignupViewModel.DIALOG_REGISTER_SHOW -> onShowDialog()
                SignupViewModel.DIALOG_REGISTER_DIS -> onDisDialog()
            }
        }

    }
    fun onDisDialog(){
        progressDialog!!.dismiss()
    }
    fun onShowDialog(){
        progressDialog!!.setTitle("Signin")
        progressDialog!!.setMessage("Please wait.......")
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.show()
    }
    @SuppressLint("ResourceAsColor")
    private fun startSignup() {
            val email = getBindingData().edtEmail.text.toString().trim()
            val passwrod = getBindingData().edtPassword.text.toString().trim()
            val user = getBindingData().edtUser.text.toString().trim()
            val confirmPassword = getBindingData().edtConfirmPassword.text.toString().trim()
            val phone = getBindingData().edtPhone.text.toString().trim()
            if (email.isEmpty()||passwrod.isEmpty() || user.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()){
                getBindingData().tvError.text = "Field can not null"
                getBindingData().tvError.setTextColor(android.R.color.holo_red_dark)
                return
            }else if (passwrod != confirmPassword){
                getBindingData().tvError.text = "Password is not true !"
            }
            getBindingData().tvError.text = ""
            mModel.emailAddress = email
            mModel.username= user
            mModel.password = passwrod
            mModel.confirmPassword = confirmPassword
            mModel.phoneNumber = phone
            mModel.onSignup()
    }

    override fun getBindingData() = mBinding as FragmentSignupBinding

    override fun getViewModel(): Class<SignupViewModel> {
        return SignupViewModel::class.java
    }
    private fun onSignupSuccess(){
        val intent = Intent(context, LoginActivity::class.java)
        context?.startActivity(intent)
        showMessage("Signup Success")
    }

    private fun onSignupError(){
        showMessage("Signup Error")
    }
}