package com.rps.animalslistdagger2.di

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.rps.animalslistdagger2.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
open class PrefsModule {

    @Provides
    @Singleton //Single Instance Of A Class (Shared Prefernce access one resource in android i.e storage)
    @TypeOfContext(CONTEXT_APP)
    open fun provideSharedPreferences(app:Application):SharedPreferencesHelper{
        return SharedPreferencesHelper(app)
    }
    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVTIY)
    open fun provideActivitySharedPrefernces(activity:AppCompatActivity):SharedPreferencesHelper{
        return SharedPreferencesHelper(activity)
    }

}

const val CONTEXT_APP = "Application context"
const val CONTEXT_ACTIVTIY = "Activity context"

@Qualifier//Qualifeies which return type u need which one is needed in which context
annotation class TypeOfContext(val types:String)