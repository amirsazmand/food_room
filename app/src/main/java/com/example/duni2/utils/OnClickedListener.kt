package com.example.duni2.utils

import com.example.duni2.model.Food

interface OnClickedListener {

    fun setOnLongClick(position : Int, oldFood: Food)

    fun setOnClick(position: Int, oldFood: Food)


}