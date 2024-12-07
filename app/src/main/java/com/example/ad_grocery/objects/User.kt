package com.example.ad_grocery.objects

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.HashMap
import java.util.Locale
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

    fun changeCurrBudget(amount: Float){
        currBudget += amount
        if(currBudget < 0)
            currBudget = 0f
    }

    fun changeInitBudget(amount: Float){
        initBudget += amount
    }

    fun changeDaysInterval(dateString: String){
        var parsedTime = parseFutureDateInMillis(dateString)
        if (parsedTime != null) {
            val temp = ((parsedTime - Date().time) / 86400000)
            daysInterval = temp.toInt()
        }
    }

    fun parseFutureDateInMillis(dateString: String): Long? {
        return try {
            // Define the date format
            val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
            dateFormat.isLenient = false

            // Parse the string into a Date object without a year
            val parsedDate = dateFormat.parse(dateString) ?: return null

            // Get the current date and year
            val currentCalendar = Calendar.getInstance()
            val currentYear = currentCalendar.get(Calendar.YEAR)

            // Create a calendar instance for the parsed date
            val parsedCalendar = Calendar.getInstance()
            parsedCalendar.time = parsedDate
            parsedCalendar.set(Calendar.YEAR, currentYear) // Set the current year

            // If the parsed date is in the past, increment the year
            if (parsedCalendar.before(currentCalendar)) {
                parsedCalendar.add(Calendar.YEAR, 1)
            }

            // Return the time in milliseconds
            parsedCalendar.timeInMillis
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}