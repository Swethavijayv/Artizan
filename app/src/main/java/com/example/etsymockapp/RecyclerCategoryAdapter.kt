package com.example.etsymockapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerCategoryAdapter(val context: Context?, val productMap: HashMap<String,MutableList<Product>>): RecyclerView.Adapter<RecyclerCategoryAdapter.ViewHolder>() {


    class ViewHolder(categoryView: View): RecyclerView.ViewHolder(categoryView){
        var categorytitle: TextView
        var productrecyclerview: RecyclerView


        init{
            categorytitle = categoryView.findViewById(R.id.categorytitle)
            productrecyclerview = categoryView.findViewById(R.id.productrecyclerview)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var categoryView = LayoutInflater.from(context).inflate(R.layout.recycler_category,parent,false)
        return ViewHolder(categoryView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var categoryList: List<String> = productMap.keys.toList()
        var category:String = categoryList[position].toString().replaceFirstChar { it.uppercase() }
        holder.categorytitle.text = category


        Log.d("CategoryAdapter","Category: "+category)
        Log.d("CategoryAdapter","Products: "+productMap.get(categoryList[position]))

        lateinit var recyclerProductAdapter: RecyclerProductAdapter
        holder.productrecyclerview.setHasFixedSize(true)
        holder.productrecyclerview.layoutManager = LinearLayoutManager(holder.itemView.context,LinearLayoutManager.HORIZONTAL,false)

        recyclerProductAdapter = RecyclerProductAdapter(context, productMap.get(categoryList[position]))
        recyclerProductAdapter.notifyDataSetChanged()
        holder.productrecyclerview.adapter = recyclerProductAdapter

    }

    override fun getItemCount(): Int {
        return productMap.keys.size
    }
}