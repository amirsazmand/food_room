package com.example.duni2.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.duni2.model.Food


@Database(entities = [Food::class], version = 1, exportSchema = false)

abstract class MyDatabase() : RoomDatabase() {

    abstract val foodDao: FoodDao

    //  design pattern : singleton

    companion object {
        // باعث میشود همه Thread ها از تغییر این database با  خبر شوند
        @Volatile
        private var instance: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {

            // اگر قرار ندهیم در هر thread میره یک شی از این database میسازه
            synchronized(this) {
                if (instance == null) {

                    instance =
                        Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, "MyDb")
                            .allowMainThreadQueries()
                            .build()

                }
                return instance!!

            }



        }


        private val callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                insertFood()


            }
        }


        fun insertFood() {
            val listFood = listOf(
                Food(
                    subjectFood = "Hamburger",
                    cityFood = "Isfahan , Iran",
                    priceFood = " $ 12",
                    distance = "120 ",
                    foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                    rating = 4f,
                    numbersOfRating = 800
                ),
                Food(
                    subjectFood = "Grilled Fish",
                    cityFood = "Isfahan , Iran",
                    priceFood = " $ 15",
                    distance = "120 miles from you",
                    foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                    rating = 2f,
                    numbersOfRating = 750
                ),
                Food(
                    subjectFood = "Lasania",
                    cityFood = "Isfahan , Iran",
                    priceFood = " $ 18",
                    distance = "120 miles from you",
                    foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                    rating = 5f,
                    numbersOfRating = 196
                ),
                Food(
                    subjectFood = "Pizza",
                    cityFood = "Isfahan , Iran",
                    priceFood = " $ 20",
                    distance = "120 miles from you",
                    foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                    rating = 4f,
                    numbersOfRating = 700
                ),
                Food(
                    subjectFood = "Sushi",
                    cityFood = "Isfahan , Iran",
                    priceFood = " $ 30",
                    distance = "120 miles from you",
                    foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                    rating = 2.5f,
                    numbersOfRating = 450
                ),
                Food(
                    subjectFood = "Roasted Fish",
                    cityFood = "Isfahan , Iran",
                    priceFood = " $ 21",
                    distance = "120 miles from you",
                    foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                    rating = 3.5f,
                    numbersOfRating = 360
                ),
                Food(
                    subjectFood = "Fried Chicken",
                    cityFood = "Isfahan , Iran",
                    priceFood = " $ 19",
                    distance = "120 miles from you",
                    foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                    rating = 2f,
                    numbersOfRating = 189
                ),
                Food(
                    subjectFood = "Baryooni",
                    cityFood = "Shiraz , Iran",
                    priceFood = " $ 16",
                    distance = "120 miles from you",
                    foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                    rating = 2.5f,
                    numbersOfRating = 945
                )
            )
            val foodDao = instance!!.foodDao
            foodDao.addAllFood(listFood)

        }


    }


}