package com.example.ad_grocery.ui.groceryList

import ProductAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ad_grocery.MainActivity
import com.example.ad_grocery.R
import com.example.ad_grocery.databinding.FragmentGalleryBinding
import com.example.ad_grocery.objects.Produce
import com.example.ad_grocery.objects.ProductDB

class GroceryListFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProductAdapter
    private var products: ArrayList<Produce> = MainActivity.user.toBuy

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MainActivity.user.mergeSameProducts()
        products = MainActivity.user.toBuy
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        // Set up RecyclerView
        adapter = ProductAdapter(products) { costChange ->
            MainActivity.user.groceryTotal += costChange
            updateTotalPrice()
        }
        binding.productRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.productRecyclerView.adapter = adapter
        MainActivity.user.groceryTotal  = 0f;
        for (prod in products) {
            MainActivity.user.groceryTotal  += prod.cost * prod.quantity * (1f - prod.discount)
        }
        updateTotalPrice()
        binding.magicButton.setOnClickListener {
            handleMagicButtonClick()
        }

        return binding.root
    }

    private fun updateTotalPrice() {
        binding.totalPriceText.text = "Total Price: %.2f".format(MainActivity.user.groceryTotal )
        if (MainActivity.user.groceryTotal  > MainActivity.user.currBudget) {
            binding.totalPriceText.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
        } else {
            binding.totalPriceText.setTextColor(ContextCompat.getColor(requireContext(), R.color.default_text))
        }
    }


    private fun handleMagicButtonClick() {
        val prodDB = ProductDB.getDatabase()
        // prodDB.forEach { (_, prods) -> prods.sortedBy { it.cost * it.discount }}
        for ((key, prodList) in prodDB) {
            val newList = prodList.sortedBy { it.cost * (1f - it.discount) }.toMutableList()
            prodDB[key] = newList
        }
        MainActivity.user.optimisedGroceryList(prodDB)
        products = MainActivity.user.toBuy
        adapter = ProductAdapter(products) { costChange ->
            MainActivity.user.groceryTotal  += costChange
            updateTotalPrice()
        }
        binding.productRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.productRecyclerView.adapter = adapter
        MainActivity.user.groceryTotal  = 0f
        for (prod in products) {
            MainActivity.user.groceryTotal  += prod.cost * prod.quantity * (1f - prod.discount)
        }
        updateTotalPrice()
    }

    fun addProduct(product: Produce) {
        products.add(product)

        adapter.notifyItemInserted(products.size - 1)

        MainActivity.user.groceryTotal  += product.cost * product.quantity
        updateTotalPrice()
    }

    fun removeProduct(productId: String) {
        val productToRemove = products.find { it.id == productId }

        productToRemove?.let {
            val position = products.indexOf(it)
            products.remove(it)

            adapter.notifyItemRemoved(position)
            adapter.notifyItemRangeChanged(position, products.size);

            MainActivity.user.groceryTotal  -= it.cost * it.quantity
            updateTotalPrice()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
