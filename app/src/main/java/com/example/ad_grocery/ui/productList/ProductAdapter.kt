package com.example.ad_grocery.ui.productList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ad_grocery.R
import com.example.ad_grocery.objects.Produce

class ProductAdapter(
    private val products: List<Produce>,
    private val onAddToBuyList: (Produce) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.productImage)
        val productNameText: TextView = view.findViewById(R.id.productNameText)
        val productCostText: TextView = view.findViewById(R.id.productCostText)
        val addToBuyButton: Button = view.findViewById(R.id.addToBuyButton)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.addable_item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        holder.productNameText.text = product.id
        holder.productCostText.text = "Price: ${product.cost * (1 - product.discount)}"

        // Set the product image
        val imgResource = holder.productImage.context.resources.getIdentifier(
            product.imageAddress,
            "drawable",
            holder.productImage.context.packageName
        )
        holder.productImage.setImageResource(imgResource)

        holder.addToBuyButton.setOnClickListener {
            onAddToBuyList(product)
        }
    }


    override fun getItemCount(): Int = products.size
}
