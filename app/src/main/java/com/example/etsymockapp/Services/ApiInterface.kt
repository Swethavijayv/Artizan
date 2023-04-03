package com.example.etsymockapp.Services

import com.example.etsymockapp.Models.Product
import com.example.etsymockapp.Models.ProductList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("products")
    suspend fun getProducts(@Query("limit")limit: Int): ProductList

    @GET("products/{productid}")
    suspend fun getProduct(@Query("productid") productid: String): Product

    @GET("products/categories")
    suspend fun getCategories(): ArrayList<String>

    @GET("products/category/{category}")
    suspend fun getCategorizedProducts(@Path("category") category: String): ProductList

}