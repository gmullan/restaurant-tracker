package org.setu.restauranttracker

import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import org.setu.restauranttracker.models.RestaurantModel

class AddEditActivity : AppCompatActivity() {

    private var editingId: Long = -1L

    private lateinit var nameInput: EditText
    private lateinit var locationInput: EditText
    private lateinit var ratingInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 320, 32, 32)
        }

        nameInput = EditText(this).apply {
            hint = "Restaurant name"
        }

        locationInput = EditText(this).apply {
            hint = "Location"
        }

        ratingInput = EditText(this).apply {
            hint = "Rating (1-5)"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
        }

        val saveButton = Button(this).apply {
            text = "Save"

            setOnClickListener {
                saveRestaurant()
            }
        }

        val cancelButton = Button(this).apply {
            text = "Cancel"

            setOnClickListener {
                finish()
            }
        }

        root.addView(nameInput)
        root.addView(locationInput)
        root.addView(ratingInput)
        root.addView(saveButton)
        root.addView(cancelButton)

        setContentView(root)

        editingId = intent.getLongExtra("id", -1L)

        if (editingId != -1L) {
            loadExistingRestaurant(editingId)
        }
    }

    private fun saveRestaurant() {

        val name = nameInput.text.toString().trim()
        val location = locationInput.text.toString().trim()
        val rating = ratingInput.text.toString().toIntOrNull()

        if (name.isEmpty()) {
            nameInput.error = "Restaurant name is required"
            return
        }

        if (location.isEmpty()) {
            locationInput.error = "Location is required"
            return
        }

        if (rating == null || rating !in 1..5) {
            ratingInput.error = "Enter a rating from 1 to 5"
            return
        }

        if (editingId == -1L) {

            val restaurant = RestaurantModel(
                name = name,
                location = location,
                rating = rating
            )

            AppData.restaurants.create(restaurant)

            Toast.makeText(
                this,
                "Restaurant created",
                Toast.LENGTH_SHORT
            ).show()

        } else {

            val restaurant = RestaurantModel(
                id = editingId,
                name = name,
                location = location,
                rating = rating
            )

            AppData.restaurants.update(restaurant)

            Toast.makeText(
                this,
                "Restaurant updated",
                Toast.LENGTH_SHORT
            ).show()
        }

        finish()
    }

    private fun loadExistingRestaurant(id: Long) {

        val restaurant = AppData.restaurants.findOne(id)

        if (restaurant == null) {
            Toast.makeText(
                this,
                "Restaurant not found",
                Toast.LENGTH_SHORT
            ).show()

            finish()
            return
        }

        nameInput.setText(restaurant.name)
        locationInput.setText(restaurant.location)
        ratingInput.setText(restaurant.rating.toString())
    }
}