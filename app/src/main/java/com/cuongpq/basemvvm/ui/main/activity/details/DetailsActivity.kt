package com.cuongpq.basemvvm.ui.main.activity.details


import android.annotation.SuppressLint
import android.content.Intent
import com.bumptech.glide.Glide
import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.data.model.Recently
import com.cuongpq.basemvvm.databinding.ActivityDetailsBinding
import com.cuongpq.basemvvm.ui.base.activity.BaseMVVMActivity
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.cuongpq.basemvvm.ui.main.activity.cart.CartActivity
import com.cuongpq.basemvvm.ui.utils.Utils


class DetailsActivity :BaseMVVMActivity<DetailsCallBack,DetailsViewModel>(),DetailsCallBack{
    private var recently : Recently ?=null
    private var check : Boolean ?=null
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
                DetailsViewModel.CLICK_ADD_CART -> onClickAddCart()
                DetailsViewModel.CLICK_IMG_CART -> onClickImgCart()
            }
        }
        initData()
    }

    private fun onClickImgCart() {
        startActivity(Intent(this,CartActivity::class.java))
    }

    private fun onClickBuyItem() {
        val id = recently!!.getID()
        val name = recently!!.getName()
        val price = recently!!.getPrice()
        val unit = recently!!.getUnit()
        val image = recently!!.getImage()
        mModel.idSP = id!!
        mModel.nameSP = name!!
        mModel.unitSP = unit!!
        mModel.imageSP = image!!
        mModel.number = 1
        mModel.priceSP = price!!
        mModel.clickBuy()
        var total =0
        for (i in Utils.listCar!!.indices) {
            total += Utils.listCar!![i].getNumber()!!
        }
        getBindingData().txtNumberDetails.text = total.toString()
        startActivity(Intent(this,CartActivity::class.java))
    }

    @SuppressLint("SetTextI18n")
    private fun onClickAddCart() {
        val id = recently!!.getID()
        val name = recently!!.getName()
        val price = recently!!.getPrice()
        val unit = recently!!.getUnit()
        val image = recently!!.getImage()
        mModel.idSP = id!!
        mModel.nameSP = name!!
        mModel.unitSP = unit!!
        mModel.imageSP = image!!
        mModel.number = 1
        mModel.priceSP = price!!
        mModel.addToCart()
        var total =0
        for (i in Utils.listCar!!.indices) {
            total += Utils.listCar!![i].getNumber()!!
        }
        getBindingData().txtNumberDetails.text = total.toString()
        showMessage("Add cart successfull")
    }


    private fun onClickBack() {
        finish()
    }
    @SuppressLint("SetTextI18n")
    private fun initData() {
        recently = intent.getSerializableExtra("details") as Recently?
        getBindingData().txtNameDetails.text = recently!!.getName()
        getBindingData().txtDescriptionDetails.text = recently!!.getDescription()
        getBindingData().txtPriceDetails.text = "$" +recently!!.getPrice()
        getBindingData().txtUnitDetails.text = ""+recently!!.getUnit()+" KG"
        Glide.with(applicationContext).load(recently!!.getImage()).into(getBindingData().imgDetails)

        if (Utils.listCar !=null){
            var total =0
            for (i in Utils.listCar!!.indices) {
                total += Utils.listCar!![i].getNumber()!!
            }
            getBindingData().txtNumberDetails.text = total.toString()
        }


    }

    override fun getBindingData() = mBinding as ActivityDetailsBinding

    override fun getViewModel(): Class<DetailsViewModel> {
       return DetailsViewModel::class.java
    }

//    override fun onResumeControl() {
//        super.onResumeControl()
//        var total =0
//        for (i in Utils.listCar!!.indices) {
//            total += Utils.listCar!![i].getNumber()!!
//        }
//        getBindingData().txtNumberDetails.text = total.toString()
//    }
}
