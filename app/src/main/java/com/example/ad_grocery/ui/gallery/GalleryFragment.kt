package com.example.ad_grocery.ui.gallery

import ProductAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ad_grocery.databinding.FragmentGalleryBinding
import com.example.ad_grocery.objects.Produce
import java.util.Date

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProductAdapter
    private var totalCost: Float = 0.0f
    private val products: ArrayList<Produce> = arrayListOf(
        Produce("1", 1, Date(), 5.0f, 2, 0, 0.0f, "image1.png"),
        Produce("2", 1, Date(), 7.5f, 1, 0, 0.0f, "image2.png"),
        Produce("3", 2, Date(), 3.0f, 5, 1, 0.0f, "image3.png"),
        Produce("4", 2, Date(), 4.0f, 3, 1, 0.0f, "image4.png"),
        Produce("5", 3, Date(), 10.0f, 1, 2, 0.0f, "image5.png"),
        Produce("6", 3, Date(), 6.5f, 4, 2, 0.0f, "image6.png"),
        Produce("7", 4, Date(), 2.0f, 8, 3, 0.0f, "image7.png"),
        Produce("8", 4, Date(), 12.0f, 1, 3, 0.0f, "image8.png"),
        Produce("9", 5, Date(), 15.0f, 2, 4, 0.0f, "image9.png"),
        Produce("10", 5, Date(), 7.0f, 6, 4, 0.0f, "image10.png"),
        Produce("11", 6, Date(), 9.5f, 1, 5, 0.0f, "image11.png"),
        Produce("12", 6, Date(), 3.5f, 3, 5, 0.0f, "image12.png"),
        Produce("13", 7, Date(), 5.5f, 2, 0, 0.0f, "image13.png"),
        Produce("14", 8, Date(), 8.0f, 3, 0, 0.0f, "image14.png"),
        Produce("15", 9, Date(), 6.5f, 5, 1, 0.0f, "image15.png"),
        Produce("16", 10, Date(), 3.5f, 4, 1, 0.0f, "image16.png"),
        Produce("17", 11, Date(), 9.0f, 2, 2, 0.0f, "image17.png"),
        Produce("18", 12, Date(), 4.5f, 3, 2, 0.0f, "image18.png")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        // Set up RecyclerView
        adapter = ProductAdapter(products) { costChange ->
            totalCost += costChange
            updateTotalPrice()
        }
        binding.productRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.productRecyclerView.adapter = adapter

        for (prod in products) {
            totalCost += prod.cost * prod.quantity
        }
        updateTotalPrice()

        return binding.root
    }

    private fun updateTotalPrice() {
        binding.totalPriceText.text = "Total Price: %.2f".format(totalCost)
    }

    fun addProduct(product: Produce) {
        products.add(product)

        adapter.notifyItemInserted(products.size - 1)

        totalCost += product.cost * product.quantity
        updateTotalPrice()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
