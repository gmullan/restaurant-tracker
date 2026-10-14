
package org.setu.restauranttracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())
        Timber.d("RestaurantTracker started")

        val viewRestaurantsButton =
            findViewById<Button>(R.id.viewRestaurantsButton)

        val addRestaurantButton =
            findViewById<Button>(R.id.addRestaurantButton)

        viewRestaurantsButton.setOnClickListener {
            startActivity(
                Intent(this, RestaurantListActivity::class.java)
            )
        }

        addRestaurantButton.setOnClickListener {
            startActivity(
                Intent(this, AddEditActivity::class.java)
            )
        }
    }
}
