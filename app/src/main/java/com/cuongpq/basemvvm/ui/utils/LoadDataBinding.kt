package com.cuongpq.basemvvm.ui.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.ui.customview.GlideApp
//******************************
//******************************
//***** Create by cuongpq  *****
//******************************
//******************************

object LoadDataBinding {
    @JvmStatic
    @BindingAdapter("urlImage")
    fun AppCompatImageView.setUrlImage(urlImage: String?){
        if (!StringUtils.isBlank(urlImage)){
            GlideApp.with(this)
                .load(urlImage)
                .placeholder(R.drawable.ic_photo)
                .error(R.drawable.ic_error)
                .into(this)
        }
    }
}