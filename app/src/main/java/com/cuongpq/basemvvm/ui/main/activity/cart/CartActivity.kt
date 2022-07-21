package com.cuongpq.basemvvm.ui.main.activity.cart

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.data.model.Cart
import com.cuongpq.basemvvm.databinding.ActivityCartBinding
import com.cuongpq.basemvvm.ui.base.activity.BaseMVVMActivity
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.cuongpq.basemvvm.ui.main.MainActivity
import com.cuongpq.basemvvm.ui.main.activity.card.stripe.CardActivity
import com.cuongpq.basemvvm.ui.utils.Utils
import com.stripe.android.PaymentConfiguration
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CartActivity : BaseMVVMActivity<CartCallBack,CartViewModel>(),CartCallBack,CartAdapter.ICart{

    private var pricePay : Int?=null
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
                CartViewModel.PAY -> onPayMent()
            }
        }
        showDataCart()
        SumPriceItem()
    }

    private fun onPayMent() {
        if (Utils.listCar!!.size >0){
            var textTB = "You have successfully placed an order with a value of $pricePay $. Thank you for your trust."
            val calendar = Calendar.getInstance()
            val currentDate = SimpleDateFormat("dd/MM/yyyy")
            val date = currentDate.format(calendar.time)
            val currentHour = SimpleDateFormat("HH:mm:ss")
            val hour = currentHour.format(calendar.time)
            mModel.text_TB = textTB
            mModel.hours = hour
            mModel.date_TB = date
            mModel.getNotification(applicationContext)

            //stripe
            val intent = Intent(this,CardActivity::class.java)
            intent.putExtra("price",pricePay)
            startActivity(intent)

            //delete list data in cart
            Utils.listCar!!.clear()
            getBindingData().recylerCart.adapter!!.notifyDataSetChanged()
            SumPriceItem()


        }else{
            showMessage("No items yet!! Please purchase")
        }
    }

    private fun SumPriceItem() {
        pricePay = 0
        for (i in Utils.listCar!!.indices){
            pricePay = pricePay!! +  (Utils.listCar!![i].getPriceSP()!! * Utils.listCar!![i].getNumber()!!)
        }
        getBindingData().txtSumPriceCart.text = (pricePay!!).toString()+"$"
    }

    private fun onContinue() {
        startActivity(Intent(this,MainActivity::class.java))
    }

    private fun onBackStack() {
        finish()
    }

    @SuppressLint("WrongConstant")
    private fun showDataCart() {
        val linearLayoutManager = LinearLayoutManager.VERTICAL
        getBindingData().recylerCart.layoutManager = LinearLayoutManager(applicationContext,linearLayoutManager,false)
        getBindingData().recylerCart.adapter = CartAdapter(this@CartActivity)
    }

    override fun getBindingData() = mBinding as ActivityCartBinding

    override fun getViewModel(): Class<CartViewModel> {
        return CartViewModel::class.java
    }

    override fun getCount(): Int {
        if (Utils.listCar==null){
            return 0
        }
        return Utils.listCar!!.size
    }

    override fun getDataCart(position: Int): Cart {
        return Utils.listCar!![position]
    }

    override fun getContext(): Context {
        return applicationContext
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun getDeleteItem(position: Int) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Confirm Dialog")
            .setMessage("Are you sure you want to delete it ?")
            .setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                Utils.listCar!!.removeAt(position)
                getBindingData().recylerCart.adapter!!.notifyDataSetChanged()
                SumPriceItem()
            }
            .setNegativeButton("No") { _: DialogInterface?, _: Int -> }
            .create()
        dialog.show()

    }



}