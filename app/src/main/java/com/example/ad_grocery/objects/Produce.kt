package com.example.ad_grocery.objects

import java.util.Date

class IdGenerator() {
    private var lastId = 0

    fun getId(): Int {
        return ++lastId
    }
}

object ProductDB {
    private val productDatabase: HashMap<String, MutableList<Float>> = HashMap()

    fun addProductType(productType: String, prices: List<Float>) {
        if (productType in productDatabase) {
            productDatabase[productType]?.addAll(prices)
        } else {
            productDatabase[productType] = prices.toMutableList()
        }
    }

    fun addPriceToProduct(productType: String, price: Float) {
        productDatabase.computeIfAbsent(productType) { mutableListOf() }.add(price)
    }

    fun getPrices(productType: String): List<Float>? {
        return productDatabase[productType]
    }

    fun removeProductType(productType: String) {
        productDatabase.remove(productType)
    }

    fun removePriceFromProduct(productType: String, price: Float) {
        productDatabase[productType]?.remove(price)
    }

    fun hasProductType(productType: String): Boolean {
        return productType in productDatabase
    }

    fun getAllProductTypes(): Set<String> {
        return productDatabase.keys
    }

    fun printDatabase() {
        for ((productType, prices) in productDatabase) {
            println("$productType: $prices")
        }
    }

    fun calculateCostScore(id: String, brand: Int): Int {
        val prices: List<Float>? = this.getPrices(id)
    
        if (prices.isNullOrEmpty()) {
            return 0
        }
    
        if (brand !in prices.indices) {
            return 0
        }
    
        val smallestPrices = prices.sorted().take(10)
    
        val currentPrice = prices[brand]
    
        val position = smallestPrices.indexOf(currentPrice)
    
        return if (position != -1) 10 - position else 0
    }
    
}


class Produce(
    val id: String,
    val brand: Int,
    val expiry: Date,
    val cost: Float,
    var quantity: Int,
    var category: Int,
    var discount: Float
    val imageAddress: String
) {
    constructor(brand: Int expiry: Date, cost: Float, quantity: Int, category: Int, discount: Float, image: String) : this(IdGenerator().getId().toString(), brand, expiry, cost, quantity, category, discount, image)

    fun calculateUtility(user: User): Float {
        val prefWeight = 0.5f
        val costWeight = 0.3f
        val expiryWeight = 0.2f
        var preferenceScore = user.preferences.get(this.id)
        if (preferenceScore == null) {
            preferenceScore = 0
        }
        val expiryScore = calculateExpiryScore()
        val costScore = ProductDB.calculateCostScore(this.id, this.brand)

        return prefWeight * preferenceScore + costWeight * costScore + expiryWeight * expiryScore + discount
    }

    fun calculateDiscountedCost(): Float {
        return cost * (1 - discount)
    }

    fun calculateExpiryScore(): Int {
        val expiryDate = expiry.time
        val currentDate = Date().time
        val timeDifference = expiryDate - currentDate
        val daysDifference = (timeDifference / (1000 * 60 * 60 * 24)).toInt()
        
        if (daysDifference < 0) {
            return 0
        } else {
            for (i in 1..10) {
                if (daysDifference < 3 * i) {
                    return i
                }
            }
        }
        return 10
    }

}