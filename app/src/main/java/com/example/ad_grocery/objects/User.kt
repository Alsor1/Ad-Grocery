package com.example.ad_grocery.objects

import com.example.ad_grocery.MainActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.collections.ArrayList

class User(
    val id: Int, var name: String, var initBudget: Float,
    var currBudget: Float, var daysInterval: Int, var lastReset: Date,
    var preferences: HashMap<Produce, Boolean>, var history: ArrayList<Produce>,
    var toBuy: ArrayList<Produce>, var moneySaved: Float, var maxEconomy: Boolean = false,
    var groceryTotal : Float= 0f
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
        history.addAll(toBuy)
        toBuy.clear()
    }

    fun setPreference(product: Produce, isPreferred: Boolean) {
        preferences[product] = isPreferred
    }

    fun timePassed(currTime: Date): Long {
        return (currTime.time - lastReset.time) / 86400000
    }

    fun calculateCurrentBudget() {
        ///LOGICA

        // Parcurgere hashmap de produse si stabilirea utility score-ului pentru fiecare produs

        // Sortare produse dupa utility score

        // Calculare si adaugare in lista de produse de cumparat in functie de bugetul curent si utility score, astfel incat sa se maximizeze utility score-ul
        // si sa nu se depaseasca bugetul curent

        // In cazul in care se gaseste o alternativa de produs mai ieftin pentru un produs preferat, se va adauga in lista de produse de cumparat in favoarea reducerii costului si
        // a putea avea cat mai multe produse preferate in lista de produse de cumparat

        // Returnare lista de produse de cumparat
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
    fun optimisedGroceryList(productDB: HashMap<String, MutableList<Produce>>) {
        if(maxEconomy == false && groceryTotal < currBudget){
            return
        }
        for ((productType, productList) in productDB) {
            for (product in toBuy){
                if(productList[0].category == product.category){
                    groceryTotal -= product.cost - productList[0].cost
                    val tempQuantity = product.quantity // Preserve quantity
                    product.id = productList[0].id
                    product.brand = productList[0].brand
                    product.expiry = productList[0].expiry
                    product.cost = productList[0].cost
                    product.category = productList[0].category
                    product.imageAddress = productList[0].imageAddress
                    product.discount = productList[0].discount

                    // Restore preserved values
                    product.quantity = tempQuantity
                    if(maxEconomy == false && groceryTotal >= currBudget){
                        mergeSameProducts()
                        return
                    }
                }
            }
        }
        mergeSameProducts()
    }

    fun mergeSameProducts() {
        val mergedMap = mutableMapOf<String, Produce>()
        for (product in toBuy) {
            if (mergedMap.containsKey(product.id)) {
                val existingProduct = mergedMap[product.id]!!
                existingProduct.quantity += product.quantity
            } else {
                mergedMap[product.id] = product
            }
        }

        toBuy = ArrayList(mergedMap.values)
    }
}