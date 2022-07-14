package com.cuongpq.basemvvm.ui.main.activity.login
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.databinding.ActivityLoginBinding
import com.cuongpq.basemvvm.ui.base.activity.BaseMVVMActivity
import com.cuongpq.basemvvm.ui.main.fragment.signin.SigninFragment
import com.cuongpq.basemvvm.ui.main.fragment.signup.SignupFragment

class LoginActivity :BaseMVVMActivity<LoginCallBack,LoginViewModel>(),LoginCallBack{
    private var viewPagerAdapter: ViewPagerAdapter? = null
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }

    override fun getLayoutMain() = R.layout.activity_login

    override fun setEvents() {

    }

    override fun initComponents() {
        getBindingData().loginViewModel = mModel
        getBindingData().tabLayout.setupWithViewPager(getBindingData().viewPager)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter!!.addFragment(SigninFragment(),"Signin")
        viewPagerAdapter!!.addFragment(SignupFragment(),"Signup")
        getBindingData().viewPager.adapter = viewPagerAdapter
    }

    override fun getBindingData() = mBinding as ActivityLoginBinding

    override fun getViewModel(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }
    class ViewPagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val fragments: ArrayList<Fragment> = ArrayList()
        private val titles: ArrayList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }
}