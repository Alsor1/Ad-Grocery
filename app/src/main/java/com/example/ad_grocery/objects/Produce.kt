package com.example.ad_grocery.objects

import java.util.Date

class IdGenerator() {
    private var lastId = 0

    fun getId(): Int {
        return ++lastId
    }
}

class Produce(
    val id: String, val expiry: Date, val cost: Float,
    var quantity: Int, var category: Category, var discount: Float
) {

}