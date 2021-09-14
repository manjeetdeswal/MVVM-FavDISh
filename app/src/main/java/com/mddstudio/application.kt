package com.mddstudio

import android.app.Application
import com.mddstudio.mvvmfavdish.model.db.FavDishDatabase
import com.mddstudio.mvvmfavdish.model.db.FavDishRapo

class application:Application() {

    private val database by
            lazy {
                FavDishDatabase.getdatabase(context=applicationContext)

            }
    val rapostary by lazy {
        FavDishRapo(database.favdish())
    }
}