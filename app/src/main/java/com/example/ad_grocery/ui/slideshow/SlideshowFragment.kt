package com.example.ad_grocery.ui.slideshow

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ad_grocery.MainActivity
import com.example.ad_grocery.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // UI Elements
        val addBudgetInput: EditText = binding.inputAddBudget
        val subtractBudgetInput: EditText = binding.inputSubtractBudget
        val resetDayInput: EditText = binding.inputResetDay
        val maxEconomySwitch: Switch = binding.switch1

        // Initialize switch state based on user's maxEconomy property
        maxEconomySwitch.isChecked = MainActivity.user.maxEconomy

        // Switch listener
        maxEconomySwitch.setOnCheckedChangeListener { _, isChecked ->
            MainActivity.user.maxEconomy = isChecked
            val state = if (isChecked) "enabled" else "disabled"
            Toast.makeText(context, "Maximum Economy $state", Toast.LENGTH_SHORT).show()
        }

        // Add to current budget button
        binding.btnAddBudget.setOnClickListener {
            val amount = addBudgetInput.text.toString().toFloatOrNull()
            if (amount != null) {
                MainActivity.user.changeCurrBudget(amount)
                Toast.makeText(
                    context,
                    "Budget increased by $amount. Current: ${MainActivity.user.currBudget}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(context, "Enter a valid amount", Toast.LENGTH_SHORT).show()
            }
        }

        // Subtract from current budget button
        binding.btnSubtractBudget.setOnClickListener {
            val amount = subtractBudgetInput.text.toString().toFloatOrNull()
            if (amount != null) {
                MainActivity.user.changeCurrBudget(-amount) // Pass as negative
                Toast.makeText(
                    context,
                    "Budget decreased by $amount. Current: ${MainActivity.user.currBudget}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(context, "Enter a valid amount", Toast.LENGTH_SHORT).show()
            }
        }

        // Change budget reset day button
        binding.btnSubmitResetDay.setOnClickListener {
            val dateString = resetDayInput.text.toString()
            MainActivity.user.changeDaysInterval(dateString)
            Toast.makeText(
                context,
                "Reset day interval updated to ${MainActivity.user.daysInterval} days",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Format reset day input as dd/MM
        binding.inputResetDay.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length == 2 && !s.contains("/")) {
                    binding.inputResetDay.setText("${s}/")
                    binding.inputResetDay.setSelection(3) // Move cursor after the slash
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.length > 5) {
                    binding.inputResetDay.setText(s.substring(0, 5)) // Limit to 5 characters
                    binding.inputResetDay.setSelection(5)
                }
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
