package com.example.ad_grocery.ui.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad_grocery.R
import com.example.ad_grocery.objects.Produce
import com.example.ad_grocery.objects.ProductDB
import com.example.ad_grocery.MainActivity

class ProductListFragment : Fragment() {

    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)

        productRecyclerView = view.findViewById(R.id.productRecyclerView)
        productRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Fetch all products as a flat list
        val products = flattenDatabase()

        // Set up the adapter
        productAdapter = ProductAdapter(products) { product ->
            addToBuyList(product)
        }

        productRecyclerView.adapter = productAdapter

        return view
    }

    private fun flattenDatabase(): List<Produce> {
        return ProductDB.getDatabase().values.flatten()
    }

    private fun addToBuyList(product: Produce) {
        val prod = product.copy(quantity = 1)
        MainActivity.user.toBuy.add(prod)
        Toast.makeText(requireContext(), "${product.id} added to your to-buy list", Toast.LENGTH_SHORT).show()
    }
}
