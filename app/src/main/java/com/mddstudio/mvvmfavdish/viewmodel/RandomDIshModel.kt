package com.mddstudio.mvvmfavdish.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mddstudio.mvvmfavdish.model.entities.RandomDishdata
import com.mddstudio.mvvmfavdish.model.network.RandomService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class RandomDIshModel :ViewModel(){

    private val randomRecser=RandomService()
    private val compostiteDispobe=CompositeDisposable()
    val loadrandomdish= MutableLiveData<Boolean>()
    val ranadomDishResponse=MutableLiveData<RandomDishdata.Recipes>()
    val randomdishError=MutableLiveData<Boolean>()


    fun getRandomDishApi(){
        loadrandomdish.value=true
        compostiteDispobe.add(
            randomRecser.getRandomDish()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<RandomDishdata.Recipes>(){
                    override fun onSuccess(t: RandomDishdata.Recipes) {
                      loadrandomdish.value=false
                        ranadomDishResponse.value=t
                        randomdishError.value=false
                    }

                    override fun onError(e: Throwable) {
                        loadrandomdish.value=false
                        e.printStackTrace()
                        randomdishError.value=true
                    }

                })

        )
    }
}