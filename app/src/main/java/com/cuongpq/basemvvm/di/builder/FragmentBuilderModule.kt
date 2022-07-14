package com.cuongpq.basemvvm.di.builder

import com.cuongpq.basemvvm.ui.main.fragment.search.SearchFragment
import com.cuongpq.basemvvm.ui.main.fragment.home.HomeFragment
import com.cuongpq.basemvvm.ui.main.fragment.notification.NotificationFragment
import com.cuongpq.basemvvm.ui.main.fragment.profile.ProfileFragment
import com.cuongpq.basemvvm.ui.main.fragment.signin.SigninFragment
import com.cuongpq.basemvvm.ui.main.fragment.signup.SignupFragment
import com.cuongpq.basemvvm.ui.main.user.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
//******************************
//******************************
//***** Create by cuongpq  *****
//******************************
//******************************

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): UserFragment
    @ContributesAndroidInjector
    abstract fun contributeSigninFragment() : SigninFragment
    @ContributesAndroidInjector
    abstract fun contributeSignupFagment() : SignupFragment
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment() : HomeFragment
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment() : SearchFragment
    @ContributesAndroidInjector
    abstract fun contributeNotificationFragment() : NotificationFragment
    @ContributesAndroidInjector
    abstract fun contributeProfileFragment() : ProfileFragment
}