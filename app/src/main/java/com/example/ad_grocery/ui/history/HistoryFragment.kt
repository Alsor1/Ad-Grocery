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
import com.example.ad_grocery.databinding.FragmentHistoryBinding
import java.util.Date

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.budgetView.text = "Current budget: " + MainActivity.user.currBudget
        binding.nameView.text = "Hello, " + MainActivity.user.name + "!"
        binding.daysUntilView.text =
            "Days until budget reset: " + (MainActivity.user.daysInterval
                    - MainActivity.user.timePassed(
                Date(System.currentTimeMillis())
            ))
        binding.moneySavedView.text = "You saved " + MainActivity.user.moneySaved

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}