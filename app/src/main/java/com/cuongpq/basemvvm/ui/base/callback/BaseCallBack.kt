package com.cuongpq.basemvvm.ui.base.callback

import com.cuongpq.basemvvm.ui.base.BaseViewUI
//******************************
//******************************
//***** Create by cuongpq  *****
//******************************
//******************************

interface BaseCallBack : BaseViewUI {
    fun error(id: String, error: Throwable)
}