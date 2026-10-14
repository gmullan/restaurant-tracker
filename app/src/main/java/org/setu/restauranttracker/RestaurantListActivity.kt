package org.setu.restauranttracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.setu.restauranttracker.models.RestaurantModel
import androidx.appcompat.app.AlertDialog
import timber.log.Timber

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
            listener = object : OnRestaurantListener {

                override fun onRestaurantClick(restaurant: RestaurantModel) {
                    Timber.d("Editing restaurant id=${restaurant.id}, name=${restaurant.name}")


                    val intent = Intent(this@RestaurantListActivity, AddEditActivity::class.java)
                    intent.putExtra("id", restaurant.id)
                    startActivity(intent)
                }

                override fun onRestaurantLongClick(restaurant: RestaurantModel): Boolean {
                    AlertDialog.Builder(this@RestaurantListActivity)
                        .setTitle("Delete Restaurant")
                        .setMessage("Are you sure you want to delete ${restaurant.name}?")
                        .setPositiveButton("Delete") { _, _ ->
                            AppData.restaurants.delete(restaurant.id)

                            Timber.d("Deleted restaurant id=${restaurant.id}")

                            recyclerView.adapter?.notifyDataSetChanged()
                        }
                        .setNegativeButton("Cancel", null)
                        .show()

                    return true
                }
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