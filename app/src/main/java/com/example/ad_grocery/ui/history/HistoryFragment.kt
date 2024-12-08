package com.example.ad_grocery.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad_grocery.MainActivity
import com.example.ad_grocery.R
import com.example.ad_grocery.objects.Produce
import java.util.Date

class HistoryFragment : Fragment() {

    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter
    private val historyList = MainActivity.user.history

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        historyRecyclerView = view.findViewById(R.id.historyRecyclerView)
        historyAdapter = HistoryAdapter(historyList)
        historyRecyclerView.layoutManager = LinearLayoutManager(context)
        historyRecyclerView.adapter = historyAdapter

        return view
    }

    fun addNewItemToHistory(newProduct: Produce) {
        historyList.add(0, newProduct) // Add the new product to the start of the list
        historyAdapter.notifyItemInserted(0) // Notify the adapter of the new item
        historyRecyclerView.scrollToPosition(0) // Scroll to the new item
    }
}
