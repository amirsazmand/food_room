package com.example.duni2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.text.FieldPosition

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrUpdateFood(food: Food)

    @Insert
    fun addAllFood(foods: List<Food>)


    @Query("Select * From food Order By id Desc")
    fun getAllFood() : List<Food>


    @Query("Update food set subjectFood = :foodName and cityFood = :foodCity and priceFood = :foodPrice and distance = :foodDistance where id = :id ")
    fun updateFood(foodName : String , foodCity : String , foodPrice : String , foodDistance : String , id : Int)



    @Query("delete from food")
    fun deleteAllFood()


    @Delete
    fun deleteFood (food: Food )


    @Query("Select * From food Where subjectFood Like '%' || :searchText || '%' ")
    fun searchFood (searchText : String) : List<Food>


}