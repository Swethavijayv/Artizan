package com.example.etsymockapp

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.etsymockapp.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var util : Util = Util()
    val BASE_URL : String = util.fetchdata

    lateinit var fragmentHomeBinding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

        var productMap = hashMapOf<String,MutableList<Product>>()

        var root: View = fragmentHomeBinding.root

        var progressBar = fragmentHomeBinding.progressBar

        progressBar.visibility = View.VISIBLE

        lateinit var recyclerCategoryAdapter: RecyclerCategoryAdapter
        var categoryrecyclerview = fragmentHomeBinding.categoryrecyclerview
        categoryrecyclerview.setHasFixedSize(true)
        categoryrecyclerview.layoutManager = LinearLayoutManager(activity)

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(ApiInterface::class.java)

        service.getProducts(100).enqueue(object :Callback<ProductList>{
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                var result = response.body()?.products!!

                for(product : Product in result){
                    //Log.d("MainActivity","Category: "+product.category+"\nProduct: "+product+"\n")
                    if(!productMap.containsKey(product.category.toString())) {
                        var categorizedproductlist: MutableList<Product>  = mutableListOf()
                        categorizedproductlist.add(product)
                        productMap.put(product.category.toString(),categorizedproductlist)
                    }else{
                        var categorizedproductlist: MutableList<Product>? = productMap.get(product.category.toString())
                        categorizedproductlist?.add(product)
                    }

                }
                recyclerCategoryAdapter = RecyclerCategoryAdapter(context, productMap)
                recyclerCategoryAdapter.notifyDataSetChanged()
                categoryrecyclerview.adapter = recyclerCategoryAdapter
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                progressBar.visibility = View.GONE
                d("Home","onFailure Error"+t.message.toString())
            }

        })

/*        service.getCategories().enqueue(object : Callback<List<String>>{
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                val result = response.body()!!
                recyclerCategoryAdapter = RecyclerCategoryAdapter(context, result,progressbar)
                recyclerCategoryAdapter.notifyDataSetChanged()
                categoryrecyclerview.adapter = recyclerCategoryAdapter

            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                progressbar.visibility=View.GONE
                d("Home","onFailure Error"+t.message.toString())
            }

        })*/

/*        //get categories
        service.getCategories().enqueue(object : Callback<List<String>>{
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                Log.d("MainActivity","onResponse Success "+response.body().toString())

            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.d("MainActivity","onFailure Error"+t.message.toString())
            }

        })

        //get product at productid
        service.getProduct("1").enqueue(object : Callback<Product>{
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                Log.d("MainActivity","onResponse Success "+response.body().toString())
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.d("MainActivity","onFailure Error"+t.message.toString())
            }

        })*/

        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}