package org.setu.restauranttracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 320, 32, 32)
        }

        val title = TextView(this).apply {
            text = "RestaurantTracker"
            textSize = 28f
        }

        val addButton = Button(this).apply {
            text = "Add Restaurant"

            setOnClickListener {
                val intent = Intent(
                    this@MainActivity,
                    AddEditActivity::class.java
                )
                startActivity(intent)
            }
        }

        listLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        root.addView(title)
        root.addView(addButton)
        root.addView(listLayout)

        setContentView(root)
        displayRestaurants()
    }

    private fun displayRestaurants() {

        listLayout.removeAllViews()

        val restaurants = AppData.restaurants.findAll()

        if (restaurants.isEmpty()) {
            val emptyText = TextView(this).apply {
                text = "No restaurants added yet."
                textSize = 18f
                setPadding(0, 40, 0, 40)
            }

            listLayout.addView(emptyText)
            return
        }

        for (restaurant in restaurants) {

            val restaurantText = TextView(this).apply {
                text = "${restaurant.name} - ${restaurant.location} - Rating: ${restaurant.rating}/5"
                textSize = 18f
                setPadding(0, 20, 0, 20)
            }

            val editButton = Button(this).apply {
                text = "Edit"

                setOnClickListener {
                    val intent = Intent(
                        this@MainActivity,
                        AddEditActivity::class.java
                    )

                    intent.putExtra("id", restaurant.id)
                    startActivity(intent)
                }
            }

            val deleteButton = Button(this).apply {
                text = "Delete"

                setOnClickListener {
                    AppData.restaurants.delete(restaurant.id)
                    displayRestaurants()
                }
            }

            listLayout.addView(restaurantText)
            listLayout.addView(editButton)
            listLayout.addView(deleteButton)
        }
    }

    override fun onResume() {
        super.onResume()

        if (::listLayout.isInitialized) {
            displayRestaurants()
        }
    }
}