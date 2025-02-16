package com.example.actividad3.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {

    lateinit var retrofit:Retrofit

    private fun getService():Retrofit{
        if(!::retrofit.isInitialized){
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    fun getAPI():API{
        return getService().create(API::class.java)
    }
}