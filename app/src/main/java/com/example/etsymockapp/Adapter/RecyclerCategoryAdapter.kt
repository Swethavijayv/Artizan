package com.example.etsymockapp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar

import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.etsymockapp.Extra.Events
import com.example.etsymockapp.Models.Product
import com.example.etsymockapp.ViewModel.ViewModel
import com.example.etsymockapp.databinding.RecyclerCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class RecyclerCategoryAdapter @Inject constructor(val categories: ArrayList<String>, val viewModel:ViewModel, private val lifecycleOwner: LifecycleOwner): RecyclerView.Adapter<RecyclerCategoryAdapter.ViewHolder>() {

    lateinit var recyclerProductAdapter: RecyclerProductAdapter

    val TAG = "RecyclerCategoryAdapter"

    inner class ViewHolder(val binding: RecyclerCategoryBinding) : RecyclerView.ViewHolder(binding.root){
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerCategoryBinding.inflate(
                LayoutInflater.from(parent.context),parent,false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var category = categories[position]
        holder.binding.apply {
            categorytitle.text = category
        }

        viewModel.getProducts(category).observe(lifecycleOwner, Observer {
            when(it){
                is Events.Loading ->{
                }
                is Events.Success ->{
                    holder.binding.apply {
                        productrecyclerview.setHasFixedSize(true)
                        productrecyclerview.layoutManager = LinearLayoutManager(holder.binding.root.context, LinearLayoutManager.HORIZONTAL,false)
                    }
                    recyclerProductAdapter = it.data?.let{
                            data-> RecyclerProductAdapter(holder.binding.root.context,data)
                    }!!

                    holder.binding.productrecyclerview.adapter = recyclerProductAdapter
                }
                is Events.Error ->{
                    it.let{
                        Log.e(TAG, "${it.msg.toString()}")
                    }
                }
            }
        })

        //Log.v(TAG, "${category}: "+"${viewModel}")


    }

    override fun getItemCount(): Int {
        return categories.size
    }
}