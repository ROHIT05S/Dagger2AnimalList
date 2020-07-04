package com.rps.animalslistdagger2.network

import com.rps.animalslistdagger2.di.DaggerApiComponent
import com.rps.animalslistdagger2.model.Animal
import com.rps.animalslistdagger2.model.ApiKey
import io.reactivex.Single


import javax.inject.Inject

class AnimalApiService {

    @Inject
    lateinit var api: AnimalApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getApiKey(): Single<ApiKey> {
        return api.getApiKey();
    }

    fun getAnimals(key: String): Single<List<Animal>> {
        return api.getAnimals(key)
    }
}