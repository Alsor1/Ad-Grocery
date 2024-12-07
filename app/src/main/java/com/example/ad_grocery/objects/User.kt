package com.example.ad_grocery.objects

import java.util.Date
import java.util.HashMap
import kotlin.collections.ArrayList

class User(
    val id: Int, var name: String, var initBudget: Float,
    var currBudget: Float, var daysInterval: Int, var lastReset: Date,
    var preferences: HashMap<String, Int>, var history: ArrayList<Produce>,
    var toBuy: ArrayList<Produce>, var moneySaved: Float
) {
    /**
     * Returns true, if the specified interval has passed and the budget has been reset
     */
    fun resetBudget(currTime: Date): Boolean {
        if (timePassed(currTime) >= daysInterval) {
            currBudget = initBudget
            return true
        }
        return false
    }

    fun timePassed(currTime: Date): Long {
        return (currTime.time - lastReset.time) / 86400000
    }

    /**
     * If the user bought the current list, add all the produces to history
     */
    fun boughtList() {
        for (prod in toBuy) {
            history.add(prod)
        }
        toBuy = ArrayList()
    }

}