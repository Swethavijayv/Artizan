package com.example.etsymockapp

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    //single product
    @GET("products/{productid}")
    fun getProduct(@Query("productid") productid: String): Call<Product>

    //whole product list, set limit with a parameter
    @GET("products")
    fun getProducts(@Query("limit")limit: Int): Call<ProductList>

    @GET("products/categories")
    fun getCategories(): Call<List<String>>

    @GET("products/category/{category}")
    fun getCategorizedProducts(@Path("category") category: String): Call<ProductList>

}