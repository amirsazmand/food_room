package com.example.duni2

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.duni2.databinding.ItemBinding
import com.example.duni2.databinding.ItemLayoutBinding
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


class MyAdapter(
    private var foodList: ArrayList<Food>,
    private val context: Context,

    private val onClickedListener: OnClickedListener
) : RecyclerView.Adapter<MyAdapter.Holder>() {


    inner class Holder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun onBind(position: Int) {

            binding.mapItemTxt.text = foodList[position].cityFood
            binding.howManyTxtItem.text = "miles for you " + foodList[position].distance
            binding.nameTxtItem.text = foodList[position].subjectFood
            binding.priceTxtItem.text = foodList[position].priceFood + " vip "
            binding.numberOfRating.text = "( ${foodList[position].numbersOfRating} Ratings )"
            binding.ratingBar.rating = foodList[position].rating
            Glide.with(context)
                .load(foodList[position].foodImgUrl)
                .transform(RoundedCornersTransformation(16, 4))
                .into(binding.foodImgItem)


        }

        fun click(position: Int) {


            itemView.setOnClickListener {

                onClickedListener.setOnClick(adapterPosition, foodList[adapterPosition])


            }
            itemView.setOnLongClickListener {
                onClickedListener.setOnLongClick(adapterPosition, foodList[adapterPosition])


                true
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_layout , parent , false)
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)

    }

    override fun getItemCount(): Int {
        return foodList.size
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {


        holder.onBind(position)

        holder.click(position)


    }

    fun addFood(newFood: Food) {

        foodList.add(0, newFood)
        // item 0 changed
        notifyItemInserted(0)

    }

    fun removeFood(oldFood: Food, position: Int) {

        foodList.remove(oldFood)
        notifyItemRemoved(position)


    }

    fun updateFood(newFood: Food, position: Int) {
//        foodList.remove(oldFood)
//        foodList.add(position , newFood)
//       notifyItemInserted(position)


        foodList[position] = newFood
        notifyItemInserted(position)


    }


    @SuppressLint("NotifyDataSetChanged")
    fun setListFood(newList: ArrayList<Food>) {
        foodList.clear()
        foodList.addAll(newList)
        notifyDataSetChanged()


    }


}