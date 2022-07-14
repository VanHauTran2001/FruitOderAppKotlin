package com.cuongpq.basemvvm.ui.utils

import androidx.fragment.app.FragmentManager
import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.ui.base.AnimationScreen
import com.cuongpq.basemvvm.ui.base.fragment.BaseFragment
import com.cuongpq.basemvvm.ui.main.user.UserFragment
//******************************
//******************************
//***** Create by cuongpq  *****
//******************************
//******************************

object OpenFragmentUtils {
    @JvmStatic
    fun getAnimationScreenFullOpen(): AnimationScreen {
        return AnimationScreen(
            R.anim.enter_to_left,
            R.anim.exit_to_left,
            R.anim.enter_to_right,
            R.anim.exit_to_right
        )
    }

    @JvmStatic
    fun openUserFragment(manager: FragmentManager) {
        val transaction = manager.beginTransaction()
        BaseFragment.openFragment(
            manager,
            transaction,
            UserFragment::class.java,
            null,
            false,
            true,
            null,
            R.id.content
        )
    }
}