package com.dhrodao.androidshop.ItemService

import com.dhrodao.androidshop.entities.ItemGet
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface ItemService {
    @GET("items")
    suspend fun getItems(): List<ItemGet>
}

private const val BASE_URL = "http://10.0.2.2:5000/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object Api {
    val retrofitService : ItemService by lazy {
        retrofit.create(ItemService::class.java) }
}