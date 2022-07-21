package com.cuongpq.basemvvm.ui.main.activity.card.stripe
import android.content.Intent
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.databinding.ActivityCardBinding
import com.cuongpq.basemvvm.ui.base.activity.BaseMVVMActivity
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.cuongpq.basemvvm.ui.main.MainActivity
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import org.json.JSONException
import org.json.JSONObject

class CardActivity : BaseMVVMActivity<CardCallBack,CardViewModel>(),CardCallBack {

    private var PUBLISH_KEY = "pk_test_51LM6SzDXwZsZacmx0S5cDkqhtA6hoGdDb1KIg9BbB8CbU4CIwT4xRNJUK51rcRf5NBUAj826utliu9I9syTuFkRc00mqQSM1Iv"
    private var paymentSheet : PaymentSheet?=null

    private var pricePayment : Int = 0
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }

    override fun getLayoutMain() = R.layout.activity_card

    override fun setEvents() {

    }

    override fun initComponents() {
       getBindingData().cardViewModel = mModel
        mModel.uiEventLiveData.observe(this){
            when(it){
                BaseViewModel.FINISH_ACTIVITY -> finish()
                CardViewModel.CONTINUE_SHOPPING -> onContinueShopping()
                CardViewModel.PAYMENT_FLOW -> PaymentFlow()
            }
        }
        PaymentConfiguration.init(this, PUBLISH_KEY)
        paymentSheet = PaymentSheet(this) { paymentSheetResult: PaymentSheetResult? ->
            onPaymentResult(paymentSheetResult!!)
        }
        val intent = intent
        pricePayment = intent.getIntExtra("price",0)
        mModel.price = pricePayment
        mModel.onPayment(applicationContext)
    }

    private fun onContinueShopping() {
        startActivity(Intent(this,MainActivity::class.java))
    }



    private fun onPaymentResult(paymentSheetResult: PaymentSheetResult) {
        when(paymentSheetResult){
            is PaymentSheetResult.Canceled -> {
                Toast.makeText(this, "Payment Cancel", Toast.LENGTH_SHORT).show()
            }
            is PaymentSheetResult.Failed -> {
                Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()
            }
            is PaymentSheetResult.Completed -> {
                Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun PaymentFlow() {
            paymentSheet!!.presentWithPaymentIntent(
                mModel.clientSecret!!, PaymentSheet.Configuration("ABC Company",
                    PaymentSheet.CustomerConfiguration(mModel.customerID!!, mModel.ephericalKey!!)
                )
            )
    }
    override fun getBindingData() = mBinding as ActivityCardBinding

    override fun getViewModel(): Class<CardViewModel> {
        return CardViewModel::class.java
    }
}