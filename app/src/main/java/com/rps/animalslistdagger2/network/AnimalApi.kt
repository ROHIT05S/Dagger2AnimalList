package com.rps.animalslistdagger2.network

import com.rps.animalslistdagger2.model.Animal
import com.rps.animalslistdagger2.model.ApiKey
import io.reactivex.Single

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AnimalApi {
    @GET("getKey")
    fun getApiKey(): Single<ApiKey>

    @FormUrlEncoded
    @POST("getAnimals")
    fun getAnimals(@Field("key") key:String):Single<List<Animal>>

}