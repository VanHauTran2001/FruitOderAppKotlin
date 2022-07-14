package com.cuongpq.basemvvm.ui.main

import android.content.Intent
import androidx.fragment.app.Fragment
import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.data.sqlite.SQLiteHelper
import com.cuongpq.basemvvm.databinding.ActivityMainBinding
import com.cuongpq.basemvvm.ui.base.activity.BaseMVVMActivity
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.cuongpq.basemvvm.ui.main.activity.cart.CartActivity
import com.cuongpq.basemvvm.ui.main.fragment.search.SearchFragment
import com.cuongpq.basemvvm.ui.main.fragment.home.HomeFragment
import com.cuongpq.basemvvm.ui.main.fragment.notification.NotificationFragment
import com.cuongpq.basemvvm.ui.main.fragment.profile.ProfileFragment

class MainActivity : BaseMVVMActivity<MainCallBack, MainViewModel>(), MainCallBack {
    private var sqliteHelper : SQLiteHelper?=null
    override fun getLayoutMain() = R.layout.activity_main

    override fun setEvents() {
    }

    override fun initComponents() {
        getBindingData().viewModel = mModel
        replaceFragment(HomeFragment())
        getBindingData().txtTitle.text = "Home"
        onClickMenuItem()
        createTableSQLite()
        setNumberCart()
        mModel.uiEventLiveData.observe(this){
            when(it){
                BaseViewModel.FINISH_ACTIVITY -> finish()
                MainViewModel.CLICK_IMG_CART -> onClickToCart()
            }
        }
    }

    private fun onClickToCart() {
        startActivity(Intent(applicationContext,CartActivity::class.java))
    }

    private fun setNumberCart() {
//        getBindingData().txtNumber.text =""+Utils.NUMBER
    }

    private fun createTableSQLite() {
        sqliteHelper = SQLiteHelper(this,"Fruit.DB",null,1)
        sqliteHelper!!.QueryData("CREATE TABLE IF NOT EXISTS Cart(Id INTEGER PRIMARY KEY AUTOINCREMENT,"+
        "IdAccount VARCHAR(20)," +
        "IdSP INTEGER," +
        "NameSP NVARCHAR(100)," +
        "PriceSP INTEGER,"+
        "UnitSP INTEGER,"+
        "ImageSP VARCHAR(100))")
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, fragment, Fragment::class.java.name)
            .commit()
    }
    private fun onClickMenuItem() {
        getBindingData().bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    getBindingData().txtTitle.text = "Home"
                    replaceFragment(HomeFragment())
                    getBindingData().bottomNavigation.menu.findItem(R.id.bottom_home).isChecked = true
                }
                R.id.bottom_search -> {
                    getBindingData().txtTitle.text = "Search"
                    replaceFragment(SearchFragment())
                    getBindingData().bottomNavigation.menu.findItem(R.id.bottom_search).isChecked = true
                }
                R.id.bottom_notifications -> {
                    getBindingData().txtTitle.text = "Notifications"
                    replaceFragment(NotificationFragment())
                    getBindingData().bottomNavigation.menu.findItem(R.id.bottom_notifications).isChecked = true
                }
                R.id.bottom_profile -> {
                    getBindingData().txtTitle.text = "Profile"
                    replaceFragment(ProfileFragment())
                    getBindingData().bottomNavigation.menu.findItem(R.id.bottom_profile).isChecked = true
                }
            }
            false
        }
    }
    override fun getViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }

    override fun getBindingData() = mBinding as ActivityMainBinding

}