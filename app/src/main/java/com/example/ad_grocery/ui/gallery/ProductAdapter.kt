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
    private val products: ArrayList<Produce>,
    private val onQuantityChange: (Float) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.productImage)
        val productNameText: TextView = view.findViewById(R.id.productNameText)
        val productCostText: TextView = view.findViewById(R.id.productCostText)
        val productQuantityText: TextView = view.findViewById(R.id.productQuantityText)
        val increaseButton: Button = view.findViewById(R.id.increaseButton)
        val decreaseButton: Button = view.findViewById(R.id.decreaseButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        // Set product details
        holder.productNameText.text = "ID: ${product.id}"
        holder.productCostText.text = "Price: $${product.cost * product.quantity}"
        holder.productQuantityText.text = "Quantity: ${product.quantity}"
        // Load image (use an image library like Glide for real apps)
        holder.productImage.setImageResource(R.drawable.ic_menu_gallery) // Placeholder image

        // Increase quantity
        holder.increaseButton.setOnClickListener {
            product.quantity++
            notifyItemChanged(position)
            onQuantityChange(product.cost)
        }

        // Decrease quantity
        holder.decreaseButton.setOnClickListener {
            if (product.quantity > 0) {
                product.quantity--
                notifyItemChanged(position)
                onQuantityChange(-product.cost)
            }
        }
    }

    override fun getItemCount(): Int = products.size
}
