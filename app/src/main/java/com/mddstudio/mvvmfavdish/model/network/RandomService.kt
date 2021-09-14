package com.mddstudio.mvvmfavdish.model.network

import com.mddstudio.mvvmfavdish.model.entities.RandomDishdata
import com.mddstudio.mvvmfavdish.utils.Constant
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.internal.schedulers.RxThreadFactory
import retrofit2.Retrofit

import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RandomService {

    private var api=Retrofit.Builder().baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(RandomDishApi::class.java)

    fun getRandomDish():Single<RandomDishdata.Recipes> {
        return api.getDishes(Constant.API_KEY,Constant.LIMITVALUE,Constant.TAGS,
        Constant.NUMBERVSLUE)
    }

}