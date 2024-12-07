package com.example.ad_grocery.objects

import java.util.Date
import java.util.HashMap
import kotlin.collections.ArrayList

class User(
    val id: Int, var name: String, var initBudget: Float,
    var currBudget: Float, var daysInterval: Int, var lastReset: Date,
    var preferences: HashMap<String, Int>, var history: ArrayList<Produce>,
    var toBuy: ArrayList<Produce>
) {
    fun resetBudget(currTime: Date): Boolean {
        val timePassed = (currTime.time - lastReset.time) / 86400000
        if (timePassed >= daysInterval) {
            currBudget = initBudget
            return true
        }
        return false
    }

    fun boughtList() {
        for (prod in toBuy) {
            history.add(prod)
        }
        toBuy = ArrayList()
    }

    fun setPreference(id: String, level: Int) {
        preferences[id] = level
    }
}