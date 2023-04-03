package com.example.etsymockapp.di

import com.example.etsymockapp.Extra.Utils
import com.example.etsymockapp.Repository.Repository
import com.example.etsymockapp.Services.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppMdule {

    @Singleton
    @Provides
    fun provideRepository(apiInterface: ApiInterface): Repository = Repository(apiInterface)

    @Singleton
    @Provides
    fun provideRetrofitInstance() : ApiInterface{
        return Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}