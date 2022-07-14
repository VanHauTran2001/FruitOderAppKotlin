package com.cuongpq.basemvvm.ui.main.fragment.signin

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.databinding.FragmentSigninBinding
import com.cuongpq.basemvvm.ui.base.fragment.BaseMvvmFragment
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.cuongpq.basemvvm.ui.main.MainActivity

class SigninFragment : BaseMvvmFragment<SignInCallBack,SigninViewModel>(),SignInCallBack {
    private var sharedPreferences : SharedPreferences?=null
    private var passwordNotVisible = 1
    private var progressDialog : ProgressDialog?=null
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }
    override fun getLayoutMain() = R.layout.fragment_signin

    override fun setEvents() {

    }

    override fun initComponents() {
        getBindingData().signinViewModel = mModel
        progressDialog = ProgressDialog(context)
        sharedPreferences = activity?.getSharedPreferences("login",Context.MODE_PRIVATE)
        getBindingData().edtEmail.setText(sharedPreferences!!.getString("email",""))
        getBindingData().edtPassword.setText(sharedPreferences!!.getString("password",""))
        getBindingData().checkPass.isChecked = sharedPreferences!!.getBoolean("checked",false)
        mModel.uiEventLiveData.observe(this){
            when(it){
                BaseViewModel.FINISH_ACTIVITY -> finishActivity()
                SigninViewModel.NAV_GET_DATA_FROM_UI_AND_SIGNIN->startSignIn()
                SigninViewModel.NAV_SIGNIN_SUCCESS -> onSigninSuccess()
                SigninViewModel.NAV_SIGNIN_ERROR -> onSigninError()
                SigninViewModel.VISIBLE_PASSWORD -> onClickVisible()
                SigninViewModel.DIALOG_SHOW -> onShowDialog()
                SigninViewModel.DIALOG_DIS-> onDisDialog()
            }
        }
    }

    private fun onDisDialog() {
        progressDialog!!.dismiss()
    }

    private fun onShowDialog() {
        progressDialog!!.setTitle("Signin")
        progressDialog!!.setMessage("Please wait.......")
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.show()
    }

    private fun onClickVisible() {
        if(passwordNotVisible==1){
            getBindingData().imgCheckPass.setImageResource(R.drawable.ic_baseline_visibility_off_24)
            getBindingData().edtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            passwordNotVisible = 0
        }else{
            getBindingData().imgCheckPass.setImageResource(R.drawable.ic_baseline_visibility_24 )
            getBindingData().edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            passwordNotVisible = 1
        }
        getBindingData().edtPassword.setSelection(getBindingData().edtPassword.length())
    }

    @SuppressLint("ResourceAsColor")
    private fun startSignIn() {
        onSaveUser()
            val email = getBindingData().edtEmail.text.toString().trim()
            val passwrod = getBindingData().edtPassword.text.toString().trim()
            if (email.isEmpty()||passwrod.isEmpty()){
                getBindingData().tvErrorSignin.text = "Field can not null"
                getBindingData().tvErrorSignin.setTextColor(android.R.color.holo_red_dark)
                return
            }
            getBindingData().tvErrorSignin.text = ""
            mModel.email = email
            mModel.password = passwrod
            mModel.onSignin()

    }
    private fun onSigninSuccess(){
        showMessage("Signin Success")
        val intent = Intent(context, MainActivity::class.java)
        context?.startActivity(intent)

    }

    private fun onSigninError(){
        showMessage("Signin Error")
    }
    private fun onSaveUser() {
        if(getBindingData().checkPass.isChecked){
            val editor : SharedPreferences.Editor = sharedPreferences!!.edit()
            editor.putString("email",getBindingData().edtEmail.text.toString())
            editor.putString("password",getBindingData().edtPassword.text.toString())
            editor.putBoolean("checked",true)
            editor.commit()
        }else{
            val editor : SharedPreferences.Editor = sharedPreferences!!.edit()
            editor.remove("email")
            editor.remove("password")
            editor.remove("checked")
            editor.commit()
        }
    }

    override fun getBindingData() = mBinding as FragmentSigninBinding

    override fun getViewModel(): Class<SigninViewModel> {
        return SigninViewModel::class.java
    }

}