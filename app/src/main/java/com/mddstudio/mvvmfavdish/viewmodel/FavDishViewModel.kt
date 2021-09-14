package com.mddstudio.mvvmfavdish.viewmodel

import androidx.lifecycle.*
import com.mddstudio.mvvmfavdish.model.entities.FavDish
import com.mddstudio.mvvmfavdish.model.db.FavDishRapo
import kotlinx.coroutines.launch

class FavDishViewModel (private val rapo: FavDishRapo):ViewModel(){

    fun insertData(dish: FavDish)= viewModelScope.launch {

        rapo.insertFavdish(dish)
    }
    fun updateData(dish: FavDish)= viewModelScope.launch {

        rapo.UpdateFavdish(dish)
    }

    fun deleteData(dish: FavDish)= viewModelScope.launch {

        rapo.deleteFavdish(dish)
    }


    fun filterdData(value: String):LiveData<List<FavDish>> =rapo.filterFavdish(value).asLiveData()
    val alldisheslist:LiveData<List<FavDish>> =rapo.getallDishes.asLiveData()

    val favdisheslist:LiveData<List<FavDish>> =rapo.getfavDishes.asLiveData()

}

class FavDishFactory(private val rapo: FavDishRapo):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(FavDishViewModel::class.java)){
            return FavDishViewModel(rapo) as T
        }
        throw IllegalArgumentException("Unkonwn Viewmodel Class")

    }

}