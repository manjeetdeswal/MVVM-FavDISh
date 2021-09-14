package com.mddstudio.mvvmfavdish.model.network

import com.mddstudio.mvvmfavdish.model.entities.RandomDishdata
import com.mddstudio.mvvmfavdish.utils.Constant
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomDishApi {

    @GET(Constant.API_ENDL)
    fun getDishes(
        @Query("apiKey") apiKey: String,
        @Query("limitLicense") limitLicense: Boolean,
        @Query("tags") tags: String,
        @Query("number") number: Int
    ): Single<RandomDishdata.Recipes>
}