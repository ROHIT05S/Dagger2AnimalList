package com.rps.animalslistdagger2

import com.rps.animalslistdagger2.di.ApiModule
import com.rps.animalslistdagger2.network.AnimalApiService

class ApiModuleTest(val mockService:AnimalApiService):ApiModule() {
    override fun provideAnimalApiService(): AnimalApiService {
        return mockService
    }
}