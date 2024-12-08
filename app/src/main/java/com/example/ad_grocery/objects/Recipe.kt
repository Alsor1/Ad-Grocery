package com.example.ad_grocery.objects

import com.example.ad_grocery.MainActivity

class Recipe(
    var ingredients: List<Produce?>,
    val name: String, var recipeDiscount: Float, var ogPrice: Float = 0f,
    var newPrice: Float = 0f, var quantity: Int = 0
){
    fun calcPrices() {
        ogPrice = ingredients.filterNotNull().sumOf { it.cost.toDouble() }.toFloat()
        newPrice = ogPrice * (1f - recipeDiscount)
    }
    fun bought() {
        for (i in ingredients){
            if (i != null) {
                MainActivity.user.history.add(i)
            }
        }
        MainActivity.user.moneySaved += ogPrice - newPrice
        MainActivity.user.currBudget -= newPrice
        quantity++
    }
}