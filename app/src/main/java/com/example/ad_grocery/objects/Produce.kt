    package com.example.ad_grocery.objects

    import java.util.Date

    class IdGenerator() {
        private var lastId = 0

        fun getId(): Int {
            return ++lastId
        }
    }

    object ProductDB {
        private val productDatabase: HashMap<String, MutableList<Produce>> = HashMap()

        fun addProductType(productType: String, prices: List<Produce>) {
            if (productType in productDatabase) {
                productDatabase[productType]?.addAll(prices)
            } else {
                productDatabase[productType] = prices.toMutableList()
            }
        }

        fun addPriceToProduct(productType: String, product: Produce) {
            productDatabase.computeIfAbsent(productType) { mutableListOf() }.add(product)
        }

        fun getProducts(productType: String): List<Produce>? {
            return productDatabase[productType]
        }

        fun removeProductType(productType: String) {
            productDatabase.remove(productType)
        }

        fun removePriceFromProduct(productType: String, produce: Produce) {
            productDatabase[productType]?.remove(produce)
        }

        fun hasProductType(productType: String): Boolean {
            return productType in productDatabase
        }

        fun getAllProductTypes(): Set<String> {
            return productDatabase.keys
        }

        fun getDatabase(): HashMap<String, MutableList<Produce>> {
            return productDatabase
        }

        fun calculateCostScore(id: String, brand: Int): Int {
            val prod: List<Produce>? = this.getProducts(id)

            if (prod.isNullOrEmpty()) {
                return 0
            }

            var isBrandPresent = false
            for (p in prod) {
                if (p.brand == brand) {
                    isBrandPresent = true
                    break
                }
            }

            if (!isBrandPresent) {
                return 0
            }

            val top10Products = prod.sortedBy { it.cost }.take(10)

            for ((index, p) in top10Products.withIndex()) {
                if (p.brand == brand) {
                    return 10 - index
                }
            }

            return 0
        }

        fun sortDB() {
            productDatabase.forEach { (_, prods) -> prods.sortedBy { it.cost * it.discount }}
        }
    }

    class Produce(
        val id: String,
        val brand: Int,
        val expiry: Date,
        val cost: Float,
        var quantity: Int,
        var category: Int,
        var discount: Float,
        val imageAddress: String
    ) {
        var utility: Float = 0f

        fun calculateUtility(user: User): Float {
            val prefWeight = 0.5f
            val costWeight = 0.3f
            val expiryWeight = 0.2f
            val preferenceScore = if (user.preferences[this] == true) 1 else 0
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

            if (daysDifference < 1) {
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
