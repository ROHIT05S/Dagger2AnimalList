package com.rps.animalslistdagger2.di

import com.rps.animalslistdagger2.network.AnimalApiService
import dagger.Component

@Component(modules =  [ApiModule::class])
interface ApiComponent {
    fun inject(service:AnimalApiService)
}