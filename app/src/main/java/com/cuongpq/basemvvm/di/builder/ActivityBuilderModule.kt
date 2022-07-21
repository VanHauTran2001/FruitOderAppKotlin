package com.cuongpq.basemvvm.di.builder

import com.cuongpq.basemvvm.ui.main.MainActivity
import com.cuongpq.basemvvm.ui.main.activity.card.stripe.CardActivity
import com.cuongpq.basemvvm.ui.main.activity.cart.CartActivity
import com.cuongpq.basemvvm.ui.main.activity.details.DetailsActivity
import com.cuongpq.basemvvm.ui.main.activity.login.LoginActivity
import com.cuongpq.basemvvm.ui.main.activity.slash.SlashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
//******************************
//******************************
//***** Create by cuongpq  *****
//******************************
//******************************

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
    @ContributesAndroidInjector
    abstract fun contributeSlashActivity() : SlashActivity
    @ContributesAndroidInjector
    abstract fun contributeLoginActivity() : LoginActivity
    @ContributesAndroidInjector
    abstract fun contributeDetailsActivity() : DetailsActivity
    @ContributesAndroidInjector
    abstract fun contributeCartActivity() : CartActivity
    @ContributesAndroidInjector
    abstract fun contribueteCardActivity() : CardActivity
}