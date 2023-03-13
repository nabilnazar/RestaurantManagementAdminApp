
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.meghamlabs.restaurantmanagementadminapp.Order
import com.meghamlabs.restaurantmanagementadminapp.R

class OrderAdapter(private var orders: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>()  {

    companion object {
        private const val TAG = "OrderAdapter"
        private const val TAG1 = "Println"
    }

    fun setData(data: List<Order>) {
        orders = data
        notifyDataSetChanged()
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tableNumberTv: TextView = itemView.findViewById(R.id.table_number_tv)
        val foodItemTv: TextView = itemView.findViewById(R.id.food_item_tv)
        val deleteBtn: Button = itemView.findViewById(R.id.delete_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        val tableNumber = order.tableNumber

        // Display orders for current table number
        holder.tableNumberTv.text = "Table No:$tableNumber"

        // Display food items for the order
        var foodItemsString = ""
        for (food in order.food!!) {
            val itemName = food.name
            val itemQuantity = food.quantity
            foodItemsString += "$itemName  x $itemQuantity\n"
        }
        holder.foodItemTv.text = foodItemsString.trim()

        holder.deleteBtn.setOnClickListener {
            // Get a reference to the Firebase Realtime Database node for the order
            val database = FirebaseDatabase.getInstance()
            val orderRef = database.getReference("orders").child(order.id)
            // Remove the order from the database
            orderRef.removeValue()
                .addOnSuccessListener {
                    // Remove the order from the local list of orders
                    orders = orders.filterNot { it.id == order.id }
                    notifyDataSetChanged()
                    Log.d(TAG, "Order deleted successfully from Firebase")
                }
                .addOnFailureListener { error ->
                    // Handle any errors that occur during the delete operation
                    Log.e(TAG, "Failed to delete order: ${error.message}")
                }
        }
    }




    override fun getItemCount(): Int {
        return orders.size
    }

}
