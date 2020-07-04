package com.rps.animalslistdagger2.di

import com.rps.animalslistdagger2.viewmodel.ListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class,PrefsModule::class,AppModule::class])
interface ViewModelComponent {
    fun inject(viewModel: ListViewModel)
}