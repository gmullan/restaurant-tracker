package org.setu.restauranttracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RestaurantListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_restaurant_list)

        val recyclerView =
            findViewById<RecyclerView>(R.id.restaurantsRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val restaurants = AppData.restaurants.findAll()

        recyclerView.adapter = RestaurantAdapter(
            restaurants,

            onEdit = { restaurant ->
                val intent = Intent(this, AddEditActivity::class.java)
                intent.putExtra("id", restaurant.id)
                startActivity(intent)
            },

            onDelete = { restaurant ->
                AppData.restaurants.delete(restaurant.id)
                recyclerView.adapter?.notifyDataSetChanged()
            }
        )

        val returnButton = findViewById<Button>(R.id.returnButton)

        returnButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        val recyclerView =
            findViewById<RecyclerView>(R.id.restaurantsRecyclerView)

        recyclerView.adapter?.notifyDataSetChanged()
    }
}