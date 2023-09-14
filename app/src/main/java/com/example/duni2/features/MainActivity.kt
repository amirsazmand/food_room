package com.example.duni2.features

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.duni2.adapters.MyAdapter
import com.example.duni2.databinding.ActivityMainBinding
import com.example.duni2.databinding.DialogAddLayoutBinding
import com.example.duni2.databinding.DialogRemoveLayoutBinding
import com.example.duni2.model.Food
import com.example.duni2.utils.FoodDao
import com.example.duni2.utils.MyDatabase
import com.example.duni2.utils.OnClickedListener

const val SHARE_PREF = "share_pref"
const val KEY_FIRST_RUN = "firstRun"


class MainActivity : AppCompatActivity(), OnClickedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MyAdapter
    private lateinit var foodDao: FoodDao
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var allListFood: ArrayList<Food>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initial()

    }

    private fun initial() {
        allListFood = arrayListOf()
        foodDao = MyDatabase.getDatabase(this).foodDao
        sharedPreferences = getSharedPreferences(SHARE_PREF, MODE_PRIVATE)
        editor = sharedPreferences.edit()


        firstRun()

        getAllFoodFromDb()

        addFoodToDataBase()

        search()
    }

    private fun addFoodToDataBase() {
        binding.addImg.setOnClickListener {


            val dialog = AlertDialog.Builder(this).create()
            val dialogBinding = DialogAddLayoutBinding.inflate(layoutInflater)
            dialog.setView(dialogBinding.root)
            dialog.setCancelable(true)
            dialog.show()


            dialogBinding.dialogBtnDone.setOnClickListener {
                if ((dialogBinding.dialogEdtNameFood.editText!!.text.isNotEmpty()) &&
                    (dialogBinding.dialogEdtDistance.editText!!.text.isNotEmpty()) &&
                    (dialogBinding.dialogEdtCityFood.editText!!.text.isNotEmpty()) &&
                    (dialogBinding.dialogEdtPriceFood.editText!!.text.isNotEmpty())
                ) {

                    val random = (1..8).random()

                    val newFood = Food(
                        subjectFood = dialogBinding.dialogEdtNameFood.editText!!.text.toString(),
                        cityFood = dialogBinding.dialogEdtCityFood.editText!!.text.toString(),
                        priceFood = "$" + dialogBinding.dialogEdtPriceFood.editText!!.text.toString(),
                        distance = dialogBinding.dialogEdtDistance.editText!!.text.toString(),
                        foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food$random.jpg",
                        rating = (1..5).random().toFloat(),
                        numbersOfRating = (100..900).random()
                    )

                    addOrUpdateFood(newFood)
                    adapter.addFood(newFood)
                    binding.recyclerView.scrollToPosition(0)



                    Toast.makeText(this, "اضافه شد", Toast.LENGTH_LONG).show()
                    dialog.dismiss()



                } else {

                    Toast.makeText(this, "لطفا همه ی آیتم ها را وارد کنید ! ", Toast.LENGTH_SHORT)
                        .show()

                }


            }


        }
    }

    private fun firstRun() {
        val listFood = arrayListOf(
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
                distance = "120 ",
                foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                rating = 2f,
                numbersOfRating = 750
            ),
            Food(
                subjectFood = "Lasania",
                cityFood = "Isfahan , Iran",
                priceFood = " $ 18",
                distance = "120 ",
                foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                rating = 5f,
                numbersOfRating = 196
            ),
            Food(
                subjectFood = "Pizza",
                cityFood = "Isfahan , Iran",
                priceFood = " $ 20",
                distance = "120 ",
                foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                rating = 4f,
                numbersOfRating = 700
            ),
            Food(
                subjectFood = "Sushi",
                cityFood = "Isfahan , Iran",
                priceFood = " $ 30",
                distance = "120 ",
                foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                rating = 2.5f,
                numbersOfRating = 450
            ),
            Food(
                subjectFood = "Roasted Fish",
                cityFood = "Isfahan , Iran",
                priceFood = " $ 21",
                distance = "120 ",
                foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                rating = 3.5f,
                numbersOfRating = 360
            ),
            Food(
                subjectFood = "Fried Chicken",
                cityFood = "Isfahan , Iran",
                priceFood = " $ 19",
                distance = "120 ",
                foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                rating = 2f,
                numbersOfRating = 189
            ),
            Food(
                subjectFood = "Baryooni",
                cityFood = "Shiraz , Iran",
                priceFood = " $ 16",
                distance = "120 ",
                foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                rating = 2.5f,
                numbersOfRating = 945
            ),
            Food(
                subjectFood = "قیمه",
                cityFood = "تهران , ایران",
                priceFood = " $ 16",
                distance = "23 ",
                foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                rating = 2.5f,
                numbersOfRating = 945
            ),
            Food(
                subjectFood = "قرمه سبزی",
                cityFood = "تهران , Iran",
                priceFood = " $ 16",
                distance = "120 ",
                foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                rating = 2.5f,
                numbersOfRating = 945
            ),
            Food(
                subjectFood = "سمبوسه",
                cityFood = "آبادان , Iran",
                priceFood = " $ 16",
                distance = "120 ",
                foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                rating = 2.5f,
                numbersOfRating = 945
            ),
            Food(
                subjectFood = "باقالی قاتوق",
                cityFood = "رشت , Iran",
                priceFood = " $ 16",
                distance = "120 ",
                foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                rating = 2.5f,
                numbersOfRating = 945
            ),
            Food(
                subjectFood = "آش دوغ",
                cityFood = "تبریز , Iran",
                priceFood = " $ 16",
                distance = "120 ",
                foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                rating = 2.5f,
                numbersOfRating = 945
            ),
            Food(
                subjectFood = "میرزا قاسمی",
                cityFood = "رشت , Iran",
                priceFood = " $ 16",
                distance = "120 ",
                foodImgUrl = "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                rating = 2.5f,
                numbersOfRating = 945
            ),
        )



        if (!sharedPreferences.contains(KEY_FIRST_RUN)) {


            foodDao.addAllFood(listFood)
            editor.putBoolean(KEY_FIRST_RUN, false)
            editor.apply()

        }
    }


    private fun getAllFoodFromDb() {


        adapter = MyAdapter(ArrayList(foodDao.getAllFood()), this, this)

        binding.recyclerView.adapter = adapter

        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


    }

    private fun addOrUpdateFood(food: Food) {

        foodDao.addOrUpdateFood(food)


    }



    private fun search() {
        binding.searchEditText.addTextChangedListener { editable ->
            if (editable!!.isNotEmpty()) {

                // search on dataBase
                val searchData = foodDao.searchFood(editable.toString())
                adapter.setListFood(ArrayList(searchData))




            } else {
                val data = foodDao.getAllFood()
                adapter.setListFood(ArrayList(data))
                //  adapter.setListFood(listFood.clone() as ArrayList<Food>)

            }


        }
    }


    // removed food with long click on item recyclerView
    override fun setOnLongClick(position: Int, oldFood: Food) {

        val dialog = AlertDialog.Builder(this).create()

        val alertBinding = DialogRemoveLayoutBinding.inflate(layoutInflater)

        dialog.setView(alertBinding.root)
        dialog.setCancelable(true)
        dialog.show()



        alertBinding.doneBtn.setOnClickListener {



            removeFood(oldFood)
            adapter.removeFood(oldFood, position)



            Toast.makeText(this, " حذف شد ", Toast.LENGTH_SHORT).show()
            dialog.dismiss()

        }
        alertBinding.cancelBtn.setOnClickListener {

            dialog.dismiss()
        }


    }



    private fun removeFood(food: Food) {

        foodDao.deleteFood(food)

    }



    //updated food with  click on item recyclerView
    override fun setOnClick(position: Int, oldFood: Food) {

        val dialog = AlertDialog.Builder(this).create()
        val dialogBinding = DialogAddLayoutBinding.inflate(layoutInflater)
        dialog.setView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogBinding.dialogEdtDistance.editText!!.setText(oldFood.distance)
        dialogBinding.dialogEdtCityFood.editText!!.setText(oldFood.cityFood)
        dialogBinding.dialogEdtNameFood.editText!!.setText(oldFood.subjectFood)

//        val price = dialogBinding.dialogEdtPriceFood.editText!!.text.toString()
//        price.split("$")


        dialogBinding.dialogEdtPriceFood.editText!!.setText(oldFood.priceFood)



        dialogBinding.dialogBtnDone.setOnClickListener {
            if ((dialogBinding.dialogEdtNameFood.editText!!.text.isNotEmpty()) &&
                (dialogBinding.dialogEdtDistance.editText!!.text.isNotEmpty()) &&
                (dialogBinding.dialogEdtCityFood.editText!!.text.isNotEmpty()) &&
                (dialogBinding.dialogEdtPriceFood.editText!!.text.isNotEmpty())
            ) {
                val newFood = Food(
                    id = oldFood.id ,
                    subjectFood = dialogBinding.dialogEdtNameFood.editText!!.text.toString(),
                    cityFood = dialogBinding.dialogEdtCityFood.editText!!.text.toString(),
                    priceFood = dialogBinding.dialogEdtPriceFood.editText!!.text.toString(),
                    distance = dialogBinding.dialogEdtDistance.editText!!.text.toString(),
                    foodImgUrl = oldFood.foodImgUrl,
                    rating = oldFood.rating,
                    numbersOfRating = oldFood.numbersOfRating
                )

                // update in data in database
                addOrUpdateFood(newFood)
                adapter.updateFood(newFood, position)
                Toast.makeText(this, "ویرایش انجام شد", Toast.LENGTH_LONG).show()
                dialog.dismiss()


            } else {

                Toast.makeText(this, "لطفا همه ی آیتم ها را وارد کنید ! ", Toast.LENGTH_SHORT)
                    .show()

            }


        }


    }


}