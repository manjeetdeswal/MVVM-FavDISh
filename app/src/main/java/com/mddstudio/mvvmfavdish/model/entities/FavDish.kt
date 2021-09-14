package com.mddstudio.mvvmfavdish.model.entities

import android.icu.text.CaseMap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@kotlinx.parcelize.Parcelize

@Entity(tableName = "favdishdb")
data class FavDish (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val image:String,
    val imageSource:String,
    val title: String,
    val type: String,
    val category: String,
    val ingredient: String,
    val cookingtime:String,
    val directionCooking:String,
    var favourite:Boolean=true
):Parcelable