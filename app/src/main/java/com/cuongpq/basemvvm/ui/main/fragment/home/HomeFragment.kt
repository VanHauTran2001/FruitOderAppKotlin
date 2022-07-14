package com.cuongpq.basemvvm.ui.main.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.data.model.Category
import com.cuongpq.basemvvm.data.model.Discount
import com.cuongpq.basemvvm.data.model.Recently
import com.cuongpq.basemvvm.databinding.FragmentHomeBinding
import com.cuongpq.basemvvm.ui.base.fragment.BaseMvvmFragment
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.cuongpq.basemvvm.ui.main.activity.details.DetailsActivity
import com.cuongpq.basemvvm.ui.main.fragment.home.category.CategoryAdapter
import com.cuongpq.basemvvm.ui.main.fragment.home.discounted.DiscountedAdapter
import com.cuongpq.basemvvm.ui.main.fragment.home.recently.RecentlyAdapter
import java.lang.ref.WeakReference


class HomeFragment : BaseMvvmFragment<HomeCallBack, HomeViewModel>(), HomeCallBack,
    DiscountedAdapter.IDiscouted,CategoryAdapter.ICategory,RecentlyAdapter.IRecently {
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }

    override fun getLayoutMain() = R.layout.fragment_home

    override fun setEvents() {
    }

    override fun initComponents() {
        onStartViewFlipper()
        getBindingData().homeViewModel = mModel
        onShowDataDiscount()
        onShowDataCategory()
        onShowDataRecently()
        mModel.uiEventLiveData.observe(this){
            when(it){
                BaseViewModel.FINISH_ACTIVITY -> finishActivity()
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun onShowDataRecently() {
        if (isConnectedInternet(requireContext())){
           mModel.callBack = WeakReference(this)
            mModel.obRecently.observe(this){
                if (getBindingData().recylerRecentlyItem.adapter==null){
                    val linearLayoutManager = LinearLayoutManager.HORIZONTAL
                    getBindingData().recylerRecentlyItem.layoutManager = LinearLayoutManager(activity,linearLayoutManager,false)
                    getBindingData().recylerRecentlyItem.adapter = RecentlyAdapter(this@HomeFragment)
                }else{
                    getBindingData().recylerRecentlyItem.adapter!!.notifyDataSetChanged()
                }
            }
        }else{
            Toast.makeText(activity,"No internet",Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("WrongConstant")
    private fun onShowDataCategory() {
        if (isConnectedInternet(requireContext())){
            mModel.callBack= WeakReference(this)
            mModel.obCategory.observe(this){
                if (getBindingData().recylerCategories.adapter==null){
                    val linearLayoutManager = LinearLayoutManager.HORIZONTAL
                    getBindingData().recylerCategories.layoutManager = LinearLayoutManager(activity,linearLayoutManager,false)
                    getBindingData().recylerCategories.adapter = CategoryAdapter(this@HomeFragment)
                }else{
                    getBindingData().recylerCategories.adapter!!.notifyDataSetChanged()
                }
            }
        }else{
            Toast.makeText(activity,"No internet",Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("WrongConstant")
    private fun onShowDataDiscount() {
        if (isConnectedInternet(requireContext())){
            mModel.callBack = WeakReference(this)
            mModel.obDiscount.observe(this){
                if (getBindingData().recylerDiscounted.adapter==null){
                    val linearLayoutManager = LinearLayoutManager.HORIZONTAL
                    getBindingData().recylerDiscounted.layoutManager = LinearLayoutManager(activity,linearLayoutManager,false)
                    getBindingData().recylerDiscounted.adapter = DiscountedAdapter(this@HomeFragment)
                }else{
                    getBindingData().recylerDiscounted.adapter!!.notifyDataSetChanged()
                }
            }
        }else{
            Toast.makeText(activity,"No internet",Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun onStartViewFlipper() {
        val listBanner :ArrayList<String> = ArrayList()
        listBanner.add("https://image.shutterstock.com/z/stock-vector-strawberry-yogurt-ads-with-fresh-fruit-on-glittering-grass-background-in-d-illustration-1501937549.jpg")
        listBanner.add("https://as2.ftcdn.net/v2/jpg/02/66/09/07/1000_F_266090751_TmGUFKFXLkKetizOxTT4YkTGUl9hje73.jpg")
        listBanner.add("https://as1.ftcdn.net/v2/jpg/02/66/09/08/1000_F_266090838_V1WoxM8uKD77HEXMuKrDgj0vyGQegpe2.jpg")
        listBanner.add("http://bizweb.dktcdn.net/thumb/grande/100/421/036/articles/7-cong-thuc-tra-trai-cay-min.jpg?v=1635391157750")
        listBanner.add("https://dichoisaigon.com/wp-content/uploads/2019/10/anh-dai-dien-20.jpg")
        listBanner.add("https://img.freepik.com/free-vector/fruits-berries-banner-vector-illustration-compositions-pitaya-pomegranate-raspberries-strawberries-grapes-currants-blueberries-lemon-peach-apple-orange-watermelon-avocado_202271-2173.jpg?w=2000")
        listBanner.add("https://thumbs.dreamstime.com/z/mango-juice-ads-liquid-hand-banner-grabbing-fruit-effect-blue-sky-background-d-illustration-152914246.jpg")
        for (i in listBanner.indices) {
            val imageView = ImageView(activity)
            Glide.with(activity!!).load(listBanner[i]).into(imageView)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            getBindingData().viewFlipper.addView(imageView)
        }
        getBindingData().viewFlipper.flipInterval=3000
        getBindingData().viewFlipper.isAutoStart = true
        val anim =AnimationUtils.loadAnimation(activity,R.anim.annim_banner)
        getBindingData().viewFlipper.animation = anim
    }


    override fun getBindingData() = mBinding as FragmentHomeBinding

    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun count(): Int {
        if (mModel.obDiscount.value==null){
            return 0
        }
        return mModel.obDiscount.value!!.size
    }

    override fun getData(position: Int): Discount {
        return mModel.obDiscount.value!![position]
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun context(): Context {
        return context!!
    }

    //Kiem tra ket noi internet
    private fun isConnectedInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val networkMobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return networkWifi != null && networkWifi.isConnected || networkMobile != null && networkMobile.isConnected
    }

    override fun countCategory(): Int {
       if (mModel.obCategory.value==null){
           return 0
       }
        return mModel.obCategory.value!!.size
    }

    override fun getDataCategory(position: Int): Category {
        return mModel.obCategory.value!![position]
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun contextCategory(): Context {
        return context!!
    }

    override fun countRecently(): Int {
       if (mModel.obRecently.value==null){
           return 0
       }
        return mModel.obRecently.value!!.size
    }

    override fun getDataRecently(position: Int): Recently {
        return mModel.obRecently.value!![position]
    }
    @SuppressLint("UseRequireInsteadOfGet")
    override fun contextRecently(): Context {
        return context!!
    }

    override fun onClickItemRecently(position: Int) {
        val intent =Intent(context,DetailsActivity::class.java)
        intent.putExtra("details", mModel.obRecently.value!![position])
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(intent)
    }
}