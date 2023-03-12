import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.meghamlabs.restaurantmanagementadminapp.Order
import com.meghamlabs.restaurantmanagementadminapp.R

class OrderAdapter(private var orders: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>()  {

    fun setData(data: List<Order>) {
        orders = data
        notifyDataSetChanged()
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tableNumberTv: TextView = itemView.findViewById(R.id.table_number_tv)
        val foodItemTv: TextView = itemView.findViewById(R.id.food_item_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        val tableNumber = order.tableNumber

        // Group orders by table number
        val ordersByTableNumber = HashMap<Int, MutableList<Order>>()
        for (o in orders) {
            if (ordersByTableNumber.containsKey(o.tableNumber)) {
                ordersByTableNumber[o.tableNumber]?.add(o)
            } else {
                ordersByTableNumber[o.tableNumber] = mutableListOf(o)
            }
        }

        // Display orders for current table number
        val orderList = ordersByTableNumber[tableNumber]
        holder.tableNumberTv.text = "Table No:$tableNumber"
        holder.foodItemTv.text = orderList?.joinToString(separator = "\n") {
            "${it.foodName}   ${it.quantity}"
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }

}
