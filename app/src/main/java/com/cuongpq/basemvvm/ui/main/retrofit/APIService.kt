package com.cuongpq.basemvvm.ui.main.retrofit
import com.cuongpq.basemvvm.data.model.Category
import com.cuongpq.basemvvm.data.model.Discount
import com.cuongpq.basemvvm.data.model.Recently
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @get:GET("getspdiscounted.php")
    val getDiscount :Observable<List<Discount>>
    @get:GET("getcategory.php")
    val getCategory :Observable<List<Category>>
    @get:GET("recently.php")
    val getRecently:Observable<List<Recently>>
    @POST("search.php")
    fun getSearch(@Field("tukhoa") tukhoa: String?): Observable<List<Recently>>
}