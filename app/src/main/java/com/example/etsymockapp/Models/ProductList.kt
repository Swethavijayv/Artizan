package com.example.etsymockapp.Models

import com.google.gson.annotations.SerializedName

data class ProductList(
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("total")
    val total: Int
)