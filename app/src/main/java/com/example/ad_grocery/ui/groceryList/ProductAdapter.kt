import android.app.AlertDialog
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ad_grocery.R
import com.example.ad_grocery.objects.Produce

class ProductAdapter(
    private val products: ArrayList<Produce>,
    private val onQuantityChange: (Float) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private lateinit var magicButton: Button

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

        holder.productNameText.text = "ID: ${product.id}"
        holder.productCostText.text = "Price: ${product.cost * product.quantity * (1 - product.discount)}"
        holder.productQuantityText.text = "Quantity: ${product.quantity}"
        val prodImage = holder.productImage.context.resources.getIdentifier(product.imageAddress, "drawable", holder.productImage.context.packageName)
        holder.productImage.setImageResource(prodImage) // Placeholder image

        val imgResource = if (product.isChecked) R.drawable.ic_checked else R.drawable.ic_check
        holder.itemView.findViewById<ImageButton>(R.id.checkButton).setImageResource(imgResource)

        if (product.isChecked) {
            holder.productNameText.paintFlags = holder.productNameText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.productNameText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.greyed_out))
            holder.productCostText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.greyed_out))
            holder.productQuantityText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.greyed_out))
        } else {
            holder.productNameText.paintFlags = holder.productNameText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.productNameText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.default_text))
            holder.productCostText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.default_text))
            holder.productQuantityText.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.default_text))
        }

        holder.increaseButton.setOnClickListener {
            product.quantity++
            notifyItemChanged(position)
            onQuantityChange(product.cost * (1 - product.discount))
        }

        holder.decreaseButton.setOnClickListener {
            if (product.quantity > 0) {
                product.quantity--
                if (product.quantity == 0) {
                    showRemoveDialog(product, position, holder.itemView.context)
                } else {
                    notifyItemChanged(position)
                    onQuantityChange(-product.cost * (1 - product.discount))
                }
            }
        }

        holder.itemView.findViewById<ImageButton>(R.id.checkButton).setOnClickListener {
            product.isChecked = !product.isChecked
            notifyItemChanged(position)
        }
    }

    private fun showRemoveDialog(product: Produce, position: Int, context: Context) {
        val dialog = AlertDialog.Builder(context)
            .setTitle("Remove Product")
            .setMessage("The quantity of ${product.id} has reached zero. Do you want to remove it from the list?")
            .setPositiveButton("Yes") { _, _ ->
                products.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, products.size);
                onQuantityChange(-product.cost)
            }
            .setNegativeButton("No") { dialog, _ ->
                products[position].quantity++
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    override fun getItemCount(): Int = products.size
}
