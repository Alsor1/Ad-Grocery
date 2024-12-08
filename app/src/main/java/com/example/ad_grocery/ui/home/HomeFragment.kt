package com.example.ad_grocery.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ad_grocery.MainActivity
import com.example.ad_grocery.databinding.FragmentHomeBinding
import com.example.ad_grocery.objects.Produce
import com.example.ad_grocery.objects.ProductDB
import com.example.ad_grocery.objects.Recipe
import java.util.Date

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Example Recipe object
    private val exampleRecipe = Recipe(
        ingredients = listOf(
            Produce("2", 101, Date(System.currentTimeMillis() + 86400000L * 70), 3.0f, 1, 1, 0.05f, "apa2"),
            Produce("12", 501, Date(System.currentTimeMillis() + 86400000L * 20), 100.0f, 1, 5, 0.3f, "mic1"),
            Produce("15", 601, Date(System.currentTimeMillis() + 86400000L * 20), 5.0f, 1, 6, 0.0f, "paine1"),
        ),
        name = "Sample Recipe",
        recipeDiscount = 0.8f,
        ogPrice = 50f,
        newPrice = 10f
    )

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.budgetView.text = "Current budget: " + MainActivity.user.currBudget
        binding.nameView.text = "Hello, " + MainActivity.user.name + "!"
        binding.daysUntilView.text =
            "Days until budget reset: " + (MainActivity.user.daysInterval
                    - MainActivity.user.timePassed(
                Date(System.currentTimeMillis())
            ))
        binding.moneySavedView.text = "You saved " + MainActivity.user.moneySaved

        // Link the button to the `bought` function
        binding.buyButton.setOnClickListener {
            exampleRecipe.bought()
            updateUI()
        }

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return root
    }

    private fun updateUI() {
        binding.budgetView.text = "Current budget: ${MainActivity.user.currBudget}"
        binding.moneySavedView.text = "You saved ${MainActivity.user.moneySaved}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
