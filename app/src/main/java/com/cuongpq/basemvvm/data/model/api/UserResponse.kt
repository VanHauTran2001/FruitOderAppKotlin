package com.cuongpq.basemvvm.data.model.api

class UserResponse<T> {
    val page: Int = 0
    val per_page: Int = 0
    val total: Int = 0
    val total_pages: Int = 0
    val data: T? = null
}