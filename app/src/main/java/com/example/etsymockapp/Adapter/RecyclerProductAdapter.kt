package com.example.etsymockapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.etsymockapp.Models.ProductList
import com.example.etsymockapp.databinding.RecyclerProductBinding

class RecyclerProductAdapter(val context: Context?, private val productlist: ProductList) : RecyclerView.Adapter<RecyclerProductAdapter.ViewHolder>() {

    val TAG = "RecyclerProductAdapter"

    class ViewHolder(val binding: RecyclerProductBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerProductBinding.inflate(
                LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var products = productlist.products

        var imageURL: String = products.get(position)!!.thumbnail

        holder.binding.apply {
/*            Log.v(TAG, "${category}: "+"${products.get(position).title}")*/
            productthumbnail.load(imageURL)

            productthumbnail.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val activity=v!!.context as AppCompatActivity
                    val productDetail=ProductDetail()
                    activity.supportFragmentManager.beginTransaction().replace(R.id.container,productDetail).addToBackStack(null).commit()
                }
            })
        }
    }

    override fun getItemCount(): Int {
        if(productlist.products == null){
            return 0
        }
        else{
            return productlist.products!!.size
        }
    }
}
