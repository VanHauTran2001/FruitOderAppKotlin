package com.cuongpq.basemvvm.data.remote

import com.cuongpq.basemvvm.data.model.User
import com.cuongpq.basemvvm.data.model.api.UserResponse
import io.reactivex.Observable
import retrofit2.http.GET
//******************************
//******************************
//***** Create by cuongpq  *****
//******************************
//******************************

interface ApiUser {
    @GET("/api/users")
    fun getUsers(
    ): Observable<UserResponse<MutableList<User>>>
}