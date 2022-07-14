package com.cuongpq.basemvvm.ui.main.activity.cart

import android.content.Context
import android.content.Intent
import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.data.model.Cart
import com.cuongpq.basemvvm.databinding.ActivityCartBinding
import com.cuongpq.basemvvm.ui.base.activity.BaseMVVMActivity
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.cuongpq.basemvvm.ui.main.MainActivity

class CartActivity : BaseMVVMActivity<CartCallBack,CartViewModel>(),CartCallBack,CartAdapter.ICart{
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }

    override fun getLayoutMain() = R.layout.activity_cart

    override fun setEvents() {
    }

    override fun initComponents() {
        getBindingData().cartViewModel = mModel
        mModel.uiEventLiveData.observe(this){
            when(it){
                BaseViewModel.FINISH_ACTIVITY -> finish()
                CartViewModel.BACK_STACK -> onBackStack()
                CartViewModel.CONTINUE -> onContinue()
            }
        }
        showDataCart()
    }

    private fun onContinue() {
        startActivity(Intent(this,MainActivity::class.java))
    }

    private fun onBackStack() {
        finish()
    }

    private fun showDataCart() {
        mModel.getDataCart(applicationContext)
    }

    override fun getBindingData() = mBinding as ActivityCartBinding

    override fun getViewModel(): Class<CartViewModel> {
        return CartViewModel::class.java
    }

    override fun getCount(): Int {
        if (mModel.obCart==null){
            return 0
        }
        return mModel.obCart!!.size
    }

    override fun getDataCart(position: Int): Cart {
        return mModel.obCart!![position]
    }

    override fun getContext(): Context {
        return applicationContext
    }

}