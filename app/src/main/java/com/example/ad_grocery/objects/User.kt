package com.example.ad_grocery.objects

import java.util.Date
import java.util.HashMap
import kotlin.collections.ArrayList

class User(
    val id: Int, var name: String, var initBudget: Float,
    var currBudget: Float, var daysInterval: Int, var lastReset: Date,
    var preferences: HashMap<Produce, Boolean>, var history: ArrayList<Produce>,
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
        history.addAll(toBuy)
        toBuy.clear()
    }

    fun setPreference(product: Produce, isPreferred: Boolean) {
        preferences[product] = isPreferred
    }

    fun calculateCurrentBudget(): List<Pair<Produce, Int>> {
        ///LOGICA
        
        // Parcurgere hashmap de produse si stabilirea utility score-ului pentru fiecare produs

        // Sortare produse dupa utility score

        // Calculare si adaugare in lista de produse de cumparat in functie de bugetul curent si utility score, astfel incat sa se maximizeze utility score-ul
        // si sa nu se depaseasca bugetul curent

        // In cazul in care se gaseste o alternativa de produs mai ieftin pentru un produs preferat, se va adauga in lista de produse de cumparat in favoarea reducerii costului si
        // a putea avea cat mai multe produse preferate in lista de produse de cumparat

        // Returnare lista de produse de cumparat
    }
}
