package com.example.etsymockapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load

class RecyclerProductAdapter(val context: Context?, private val productlist: MutableList<Product>?) : RecyclerView.Adapter<RecyclerProductAdapter.ViewHolder>() {

    class ViewHolder(productView: View): RecyclerView.ViewHolder(productView){
        var productthumbnail: ImageView
        init{
            productthumbnail = productView.findViewById(R.id.productthumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var productView = LayoutInflater.from(context).inflate(R.layout.recycler_product,parent,false)
        return ViewHolder(productView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var imageURL: String = productlist?.get(position)!!.thumbnail
        Log.d("ProductAdapter","URL: "+imageURL)
        holder.productthumbnail.load(imageURL)
        holder.productthumbnail.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val activity=v!!.context as AppCompatActivity
                val productDetail=ProductDetail()
                activity.supportFragmentManager.beginTransaction().replace(R.id.container,productDetail).addToBackStack(null).commit()
            }

        })
    }

    override fun getItemCount(): Int {
        if(productlist == null){
            return 0
        }
        else{
            return productlist!!.size
        }
    }
}