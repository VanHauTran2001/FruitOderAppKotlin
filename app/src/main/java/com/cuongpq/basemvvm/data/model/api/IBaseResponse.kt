package com.cuongpq.basemvvm.data.model.api

interface IBaseResponse {
    fun getErrorCode(): Int
    fun getMsg(): String?
    fun getStatus():Int
}