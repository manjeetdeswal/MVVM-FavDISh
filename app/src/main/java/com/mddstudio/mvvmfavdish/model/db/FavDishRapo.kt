package com.mddstudio.mvvmfavdish.model.db

import androidx.annotation.WorkerThread
import com.mddstudio.mvvmfavdish.model.entities.FavDish

class FavDishRapo (private val favDishDao: FavDishDao){

    @WorkerThread
    suspend fun insertFavdish(favDish: FavDish){
        favDishDao.insetFavdishDetailis(favDish)
    }

    @WorkerThread
    suspend fun UpdateFavdish(favDish: FavDish){
        favDishDao.updateDish(favDish)
    }

    @WorkerThread
    suspend fun deleteFavdish(favDish: FavDish){
        favDishDao.deleteDish(favDish)
    }


     fun filterFavdish(fvalue: String) : kotlinx.coroutines.flow.Flow<List<FavDish>> = favDishDao.getfilterList(fvalue)



    val getallDishes:kotlinx.coroutines.flow.Flow<List<FavDish>> =favDishDao.getallDishes()
    val getfavDishes:kotlinx.coroutines.flow.Flow<List<FavDish>> =favDishDao.getfavDish()
}