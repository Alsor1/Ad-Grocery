package com.example.ad_grocery.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ad_grocery.R
import com.example.ad_grocery.objects.Produce

class HistoryAdapter(private val historyList: List<Produce>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.historyProductImage)
        val productName: TextView = view.findViewById(R.id.historyProductName)
        val productDetails: TextView = view.findViewById(R.id.historyProductDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_product, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val product = historyList[position]
        holder.productName.text = product.id
        holder.productDetails.text = "Price: $${product.cost} x ${product.quantity}"
        val imgResource = holder.productImage.context.resources.getIdentifier(product.imageAddress, "drawable", holder.productImage.context.packageName)
        holder.productImage.setImageResource(imgResource) // Placeholder image
    }

    override fun getItemCount(): Int = historyList.size
}
