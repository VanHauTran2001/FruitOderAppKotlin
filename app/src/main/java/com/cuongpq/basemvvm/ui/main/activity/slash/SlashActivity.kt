package com.cuongpq.basemvvm.ui.main.activity.slash

import android.content.Intent
import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.databinding.ActivitySlashBinding
import com.cuongpq.basemvvm.ui.base.activity.BaseMVVMActivity
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.cuongpq.basemvvm.ui.main.MainActivity
import com.cuongpq.basemvvm.ui.main.activity.login.LoginActivity
import com.cuongpq.basemvvm.ui.utils.OpenFragmentUtils

class SlashActivity : BaseMVVMActivity<SlashCallBack,SlashViewModel>(),SlashCallBack {
    override fun error(id: String, error: Throwable) {
       showMessage(error.message!!)
    }


    override fun getLayoutMain() = R.layout.activity_slash

    override fun setEvents() {
    }

    override fun initComponents() {
        getBindingData().slashViewModel = mModel
        mModel.uiEventLiveData.observe(this){
            when(it){
                BaseViewModel.FINISH_ACTIVITY-> finish()
                SlashViewModel.START_LOGIN_ACTIVITY -> startLoginActivity()
            }
        }
    }

    override fun getBindingData() = mBinding as ActivitySlashBinding

    override fun getViewModel(): Class<SlashViewModel> {
        return SlashViewModel::class.java
    }

    fun startLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}