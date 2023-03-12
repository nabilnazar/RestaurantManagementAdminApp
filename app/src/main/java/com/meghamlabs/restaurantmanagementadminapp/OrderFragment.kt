package com.meghamlabs.restaurantmanagementadminapp

import OrderAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class OrderFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var adapter: OrderAdapter
    private lateinit var recyclerView: RecyclerView
    private var orderList = mutableListOf<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.food_order_rv)

        adapter = OrderAdapter(orderList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter


        database = FirebaseDatabase.getInstance().getReference("orders")

        // Fetch data from firebase
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orderList.clear()
                for (orderSnapshot in snapshot.children) {
                    val order = orderSnapshot.getValue(Order::class.java)
                    order?.let {
                        orderList.add(it)
                    }
                }
                adapter.setData(orderList)

            }



            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}
