package com.rps.animalslistdagger2

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.rps.animalslistdagger2.di.PrefsModule
import com.rps.animalslistdagger2.util.SharedPreferencesHelper

class PrefsModuleTest(val mockPrefs:SharedPreferencesHelper):PrefsModule() {
    override fun provideSharedPreferences(app: Application): SharedPreferencesHelper {
        return mockPrefs
    }
}