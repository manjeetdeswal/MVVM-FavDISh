package com.mddstudio.mvvmfavdish.model.db

import androidx.room.*
import com.mddstudio.mvvmfavdish.model.entities.FavDish
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDishDao {
    @Insert
    suspend fun insetFavdishDetailis(fabdish: FavDish)

    @Query("select *  from favdishdb order by id")
     fun getallDishes():Flow<List<FavDish>>


     @Update
     suspend fun updateDish(favDish: FavDish)


     @Query("Select * from favdishdb where favourite = 1")
      fun getfavDish():Flow<List<FavDish>>

      @Delete
      suspend fun deleteDish(favDish: FavDish)

    @Query("SElect * from favdishdb where type = :filtertype")
      fun getfilterList(filtertype:String) :Flow<List<FavDish>>


}