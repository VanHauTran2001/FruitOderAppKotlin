package com.cuongpq.basemvvm.ui.main.activity.card.stripe

import android.content.Context
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cuongpq.basemvvm.data.local.AppDatabase
import com.cuongpq.basemvvm.data.remote.InteractCommon
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.Executor
import javax.inject.Inject
import kotlin.properties.Delegates

class CardViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) :BaseViewModel<CardCallBack>(appDatabase,interactCommon,scheduler){
    private var SECRET_KEY = "sk_test_51LM6SzDXwZsZacmxCiXsNWfrCoBPMtWY5xJMPp16MAywHtk7XqXEuJmCmweg2XHnAtdrBoqXN7xMqhKGULfmxYSN003B7h56y8"
    var customerID: String? = null
    var ephericalKey: String? = null
    var clientSecret: String? = null
    var price by Delegates.notNull<Int>()
    companion object{
        const val CONTINUE_SHOPPING = 2001
        const val PAYMENT_FLOW = 2002
    }
    fun onContinueShopping(){
        uiEventLiveData.value = CONTINUE_SHOPPING
    }

    fun onPayment(context: Context) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, "https://api.stripe.com/v1/customers",
            Response.Listener { response ->
                try {
                    val `object` = JSONObject(response)
                    customerID = `object`.getString("id")
                    getPhericalKey(context,customerID!!)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val header: MutableMap<String, String> = HashMap()
                header["Authorization"] = "Bearer $SECRET_KEY"
                return header
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }
    private fun getPhericalKey(context: Context,customerID: String) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
            Response.Listener { response ->
                try {
                    val `object` = JSONObject(response)
                    ephericalKey = `object`.getString("id")
                    getClientSecret(context,customerID)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val header: MutableMap<String, String> = java.util.HashMap()
                header["Authorization"] = "Bearer $SECRET_KEY"
                header["Stripe-Version"] = "2020-08-27"
                return header
            }

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["customer"] = customerID
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }
    private fun getClientSecret(context: Context,customerID: String) {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, "https://api.stripe.com/v1/payment_intents",
            Response.Listener { response ->
                try {
                    val `object` = JSONObject(response)
                    clientSecret = `object`.getString("client_secret")
                    //payment flow
                    uiEventLiveData.value = PAYMENT_FLOW
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val header: MutableMap<String, String> = java.util.HashMap()
                header["Authorization"] = "Bearer $SECRET_KEY"
                return header
            }

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["customer"] = customerID
                params["amount"] = "$price"+ "00" //money paypal
                params["currency"] = "usd"
                params["automatic_payment_methods[enabled]"] = "true"
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }
}