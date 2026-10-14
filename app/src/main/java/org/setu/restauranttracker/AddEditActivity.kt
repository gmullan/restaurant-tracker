package org.setu.restauranttracker

import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import org.setu.restauranttracker.models.RestaurantModel
import android.widget.TextView
import timber.log.Timber
import android.app.DatePickerDialog
import java.util.Calendar

class AddEditActivity : AppCompatActivity() {

    private var editingId: Long = -1L

    private lateinit var nameInput: EditText
    private lateinit var locationInput: EditText
    private lateinit var ratingInput: EditText

    private lateinit var dateVisitedButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_edit)

        nameInput = findViewById(R.id.nameInput)
        locationInput = findViewById(R.id.locationInput)
        ratingInput = findViewById(R.id.ratingInput)

        val saveButton = findViewById<Button>(R.id.saveButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        dateVisitedButton = findViewById(R.id.dateVisitedButton)

        dateVisitedButton.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePicker = DatePickerDialog(
                this,
                { _, year, month, day ->
                    val selectedDate = "%02d/%02d/%04d".format(day, month + 1, year)
                    dateVisitedButton.text = selectedDate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }

        saveButton.setOnClickListener {
            saveRestaurant()
        }

        cancelButton.setOnClickListener {
            finish()
        }



        editingId = intent.getLongExtra("id", -1L)

        if (editingId != -1L) {
            findViewById<TextView>(R.id.formTitle).text = "Edit Restaurant"
            loadExistingRestaurant(editingId)
        }
    }

    private fun saveRestaurant() {

        val name = nameInput.text.toString().trim()
        val location = locationInput.text.toString().trim()
        val dateVisited = dateVisitedButton.text.toString()
        val rating = ratingInput.text.toString().toIntOrNull()

        if (name.isEmpty()) {
            nameInput.error = "Restaurant name is required"
            return
        }

        if (location.isEmpty()) {
            locationInput.error = "Location is required"
            return
        }

        if (dateVisited == "Select Date Visited") {
            Toast.makeText(this, "Please select a date visited", Toast.LENGTH_SHORT).show()
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
                dateVisited = dateVisited,
                rating = rating
            )
            AppData.restaurants.create(restaurant)

            Timber.d("Created restaurant id=${restaurant.id}")

            Toast.makeText(this, "Restaurant created", Toast.LENGTH_SHORT).show()
        } else {
            val restaurant = RestaurantModel(
                id = editingId,
                name = name,
                location = location,
                dateVisited = dateVisited,
                rating = rating
            )
            AppData.restaurants.update(restaurant)

            Timber.d("Updated restaurant id=${restaurant.id}")

            Toast.makeText(this, "Restaurant updated", Toast.LENGTH_SHORT).show()
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
        dateVisitedButton.text = restaurant.dateVisited
        ratingInput.setText(restaurant.rating.toString())
    }
}