package com.mddstudio.mvvmfavdish.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mddstudio.mvvmfavdish.model.entities.FavDish

@Database(entities = [FavDish::class],version = 1)
abstract class FavDishDatabase :RoomDatabase(){

    abstract fun favdish():FavDishDao

    companion object{

        private var INSTSNCE:FavDishDatabase? =null

        fun getdatabase(context: Context):FavDishDatabase{
            return INSTSNCE ?: synchronized(this) {

                val instance=Room.databaseBuilder(context.applicationContext,
                FavDishDatabase::class.java,"favdishdb")
                    .build()
                INSTSNCE=instance

             return  instance

            }
        }


    }



}