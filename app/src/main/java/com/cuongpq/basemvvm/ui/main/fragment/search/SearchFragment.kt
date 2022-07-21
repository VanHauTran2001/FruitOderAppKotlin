package com.cuongpq.basemvvm.ui.main.fragment.search

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.data.model.Recently
import com.cuongpq.basemvvm.databinding.FragmentSearchBinding
import com.cuongpq.basemvvm.ui.base.fragment.BaseMvvmFragment
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.cuongpq.basemvvm.ui.main.activity.details.DetailsActivity
import java.lang.ref.WeakReference

class SearchFragment : BaseMvvmFragment<SearchCallBack, SearchViewModel>(), SearchCallBack,SearchAdapter.ISearch {
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }

    override fun getLayoutMain() = R.layout.fragment_search

    override fun setEvents() {

    }

    override fun initComponents() {
        getBindingData().searchViewModel = mModel
        mModel.uiEventLiveData.observe(this){
            when(it){
                BaseViewModel.FINISH_ACTIVITY ->finishActivity()
            }
        }
        onClickSearch()
        onShowDataSearch()
    }

    private fun onClickSearch() {
        getBindingData().edtSeachItem.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.isEmpty()){
                    mModel.obListSearch.clear()
                    onShowDataSearch()
                }else{
                    mModel.getDataSearch(getBindingData().edtSeachItem.text.toString().trim())
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    @SuppressLint("WrongConstant")
    private fun onShowDataSearch() {
            if (getBindingData().recylerSearch.adapter==null){
                val linearLayoutManager = LinearLayoutManager.VERTICAL
                getBindingData().recylerSearch.layoutManager =  LinearLayoutManager(activity,linearLayoutManager,false)
                    getBindingData().recylerSearch.adapter = SearchAdapter(this@SearchFragment)
            }else{
                getBindingData().recylerSearch.adapter!!.notifyDataSetChanged()
            }

    }


    override fun getBindingData() = mBinding as FragmentSearchBinding

    override fun getViewModel(): Class<SearchViewModel> {
        return SearchViewModel::class.java
    }

    override fun getCountSeachr(): Int {
        if (mModel.obListSearch==null){
            return 0
        }
        return mModel.obListSearch.size
    }

    override fun getDataSearch(position: Int): Recently {
        return mModel.obListSearch!![position]
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun getContextSearch(): Context {
        return context!!
    }


    override fun onClickItemSearch(position: Int) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("details", mModel.obListSearch!![position])
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(intent)
    }

}
