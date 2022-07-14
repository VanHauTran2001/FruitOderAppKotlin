package com.cuongpq.basemvvm.ui.main.activity.details


import android.content.Intent
import android.widget.Toast
import com.bumptech.glide.Glide
import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.data.model.Recently
import com.cuongpq.basemvvm.data.sqlite.SQLiteHelper
import com.cuongpq.basemvvm.databinding.ActivityDetailsBinding
import com.cuongpq.basemvvm.ui.base.activity.BaseMVVMActivity
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.cuongpq.basemvvm.ui.main.activity.cart.CartActivity
import com.google.firebase.auth.FirebaseAuth


class DetailsActivity :BaseMVVMActivity<DetailsCallBack,DetailsViewModel>(),DetailsCallBack{
    private var recently : Recently ?=null
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }

    override fun getLayoutMain() = R.layout.activity_details

    override fun setEvents() {

    }

    override fun initComponents() {
        getBindingData().detailsViewModel = mModel
        mModel.uiEventLiveData.observe(this){
            when(it){
                BaseViewModel.FINISH_ACTIVITY -> finish()
                DetailsViewModel.CLICK_BACK ->onClickBack()
                DetailsViewModel.CLICK_BUY -> onClickBuyItem()
            }
        }
        initData()
        onClickAddCart()
    }

    private fun onClickBuyItem() {
        val id = recently!!.getID()
        val name = recently!!.getName()
        val price = recently!!.getPrice()
        val unit = recently!!.getUnit()
        val image = recently!!.getImage()
        mModel.idSP = id!!
        mModel.nameSP = name!!
        mModel.priceSP = price!!
        mModel.unitSP = unit!!
        mModel.imageSP = image!!
        mModel.clickBuy(applicationContext)
        startActivity(Intent(this,CartActivity::class.java))
    }

    private fun onClickAddCart() {
        val id = recently!!.getID()
        val name = recently!!.getName()
        val price = recently!!.getPrice()
        val unit = recently!!.getUnit()
        val image = recently!!.getImage()
        mModel.idSP = id!!
        mModel.nameSP = name!!
        mModel.priceSP = price!!
        mModel.unitSP = unit!!
        mModel.imageSP = image!!
        getBindingData().btnAddCart.setOnClickListener {
            if (recently != null) {
                mModel.addToCart(applicationContext)
            }
        }

    }


    private fun onClickBack() {
        finish()
    }
    private fun initData() {
        recently = intent.getSerializableExtra("details") as Recently?
        getBindingData().txtNameDetails.text = recently!!.getName()
        getBindingData().txtDescriptionDetails.text = recently!!.getDescription()
        getBindingData().txtPriceDetails.text = "$" +recently!!.getPrice()
        getBindingData().txtUnitDetails.text = ""+recently!!.getUnit()+" KG"
        Glide.with(applicationContext).load(recently!!.getImage()).into(getBindingData().imgDetails)

    }

    override fun getBindingData() = mBinding as ActivityDetailsBinding

    override fun getViewModel(): Class<DetailsViewModel> {
       return DetailsViewModel::class.java
    }

}
