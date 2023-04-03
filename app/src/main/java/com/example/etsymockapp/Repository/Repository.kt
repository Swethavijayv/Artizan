package com.example.etsymockapp.Repository

import com.example.etsymockapp.Models.Product
import com.example.etsymockapp.Models.ProductList
import com.example.etsymockapp.Services.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository @Inject constructor(val apiInterface: ApiInterface){

    fun getCategorizedProducts(category: String): Flow<ProductList> = flow{
        emit(apiInterface.getCategorizedProducts(category))
    }.flowOn(Dispatchers.Main)

    fun getCategories(): Flow<ArrayList<String>> = flow{
        emit(apiInterface.getCategories())
    }.flowOn(Dispatchers.Main)

    fun getProduct(id: String): Flow<Product> = flow{
        emit(apiInterface.getProduct(id))
    }.flowOn(Dispatchers.Main)

}