package com.example.duni2

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "food")
data class Food(

    @PrimaryKey(autoGenerate = true)
    val id : Int? = null ,
    val subjectFood : String,
    val cityFood : String,
    val priceFood : String,
    val distance : String,
    val foodImgUrl : String,
    val rating : Float,
    val numbersOfRating : Int


)
