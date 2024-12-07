package com.example.ad_grocery.objects

import java.util.ArrayList
import java.util.Date
import java.util.HashMap

class User(
    val id: String, var name: String, var initBudget: Float,
    var currBudget: Float, var daysInterval: Int, var lastReset: Date,
    var preferences: HashMap<Produce, Int>, var history: ArrayList<Produce>,
    var toBuy: ArrayList<Produce>
) {

}