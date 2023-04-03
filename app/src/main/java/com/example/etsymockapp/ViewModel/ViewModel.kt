package com.example.etsymockapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.etsymockapp.Extra.Events
import com.example.etsymockapp.Models.Product
import com.example.etsymockapp.Models.ProductList
import com.example.etsymockapp.Repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(val repository: Repository): ViewModel(){

    val categories = MutableLiveData<Events<ArrayList<String>>>()

    fun getCategories(){
        viewModelScope.launch {
            repository.getCategories().catch {
                categories.postValue(Events.Error(msg=it.localizedMessage))
            }.collect{ it ->
                categories.postValue(Events.Success(it))
            }
        }
    }

    fun getProducts(category:String): MutableLiveData<Events<ProductList>>{
        // livedata needs to be initialized per api call otherwise, its being observed at a particular state everytime the call is made
        val products = MutableLiveData<Events<ProductList>>()

        viewModelScope.launch {
            repository.getCategorizedProducts(category).catch {
                products.postValue(Events.Error(msg=it.localizedMessage))
            }.collect{ it->
                products.postValue(Events.Success(it))
            }
        }

        return products

    }

    fun getProduct(id: String): MutableLiveData<Events<Product>>{

        val product = MutableLiveData<Events<Product>>()

        viewModelScope.launch {
            repository.getProduct(id).catch {
                product.postValue(Events.Error(msg=it.localizedMessage))
            }.collect{
                product.postValue(Events.Success(it))
            }
        }
        return product
    }


}


