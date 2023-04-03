package com.example.etsymockapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.etsymockapp.Extra.Events
import com.example.etsymockapp.Extra.Utils.BASE_URL
import com.example.etsymockapp.Models.Product
import com.example.etsymockapp.Models.ProductList
import com.example.etsymockapp.Services.ApiInterface
import com.example.etsymockapp.ViewModel.ViewModel
import com.example.etsymockapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    val TAG = "HomeFragment"

    val viewModel: ViewModel by viewModels()

    lateinit var recyclerCategoryAdapter: RecyclerCategoryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        val root: View = binding.root

        viewModel.getCategories()

        viewModel.categories.observe(viewLifecycleOwner, Observer {
            when(it){
                is Events.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Events.Success ->{
                    //maybe use shimmer?
                    binding.progressBar.visibility = View.GONE
                    binding.categoryrecyclerview.setHasFixedSize(true)
                    binding.categoryrecyclerview.layoutManager = LinearLayoutManager(requireContext().applicationContext)

                    recyclerCategoryAdapter = it.data?.let{ data -> RecyclerCategoryAdapter(data, viewModel,viewLifecycleOwner)}!!
                    binding.categoryrecyclerview.adapter = recyclerCategoryAdapter
                }

                is Events.Error ->{
                    binding.progressBar.visibility = View.GONE
                    it.let{
                        Log.e(TAG,"${it.msg.toString()}")
                    }
                }
            }
        })


        return root
    }
}