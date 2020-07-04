package com.rps.animalslistdagger2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rps.animalslistdagger2.di.AppModule
import com.rps.animalslistdagger2.di.CONTEXT_APP
import com.rps.animalslistdagger2.di.DaggerViewModelComponent
import com.rps.animalslistdagger2.di.TypeOfContext

import com.rps.animalslistdagger2.model.Animal
import com.rps.animalslistdagger2.model.ApiKey
import com.rps.animalslistdagger2.network.AnimalApiService
import com.rps.animalslistdagger2.util.SharedPreferencesHelper
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class ListViewModel(application: Application) : AndroidViewModel(application) {

    constructor(application: Application, test: Boolean = true) : this(application) {
        injected = true
    }

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var apiService: AnimalApiService

    @Inject
    @field:TypeOfContext(CONTEXT_APP)
    lateinit var prefs: SharedPreferencesHelper

    private var inValidApiKey = false

    private var injected = false

    fun inject() {
        if (!injected) {
            DaggerViewModelComponent.builder()
                .appModule(AppModule(getApplication()))
                .build()
                .inject(this)
        }
    }


    fun refresh() {
        inject()
        loading.value = true
        inValidApiKey = false

        val key = prefs.getApiKey()
        if (key.isNullOrEmpty()) {
            getKey()
        } else {
            getAnimals(key)
        }

    }

    fun hardRefresh() {
        inject()
        loading.value = true
        getKey()
    }

    private fun getKey() {
        disposable.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(key: ApiKey?) {
                        if (key?.key.isNullOrEmpty()) {
                            loadError.value = true
                            loading.value = false
                        } else {
                            prefs.saveApiKey(key?.key.toString())
                            getAnimals(key?.key.toString())
                        }
                    }

                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()
                        loading.value = false
                        loadError.value = true

                    }

                })
        )
    }

    private fun getAnimals(key: String) {
        disposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Animal>>() {
                    override fun onSuccess(animalList: List<Animal>?) {

                        loadError.value = false
                        animals.value = animalList
                        loading.value = false

                    }

                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()
                        if (!inValidApiKey) {
                            inValidApiKey = true
                            getKey()
                        } else {
                            loadError.value = true
                            loading.value = false
                            animals.value = null
                        }
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}
