
package org.setu.restauranttracker.main

import org.setu.restauranttracker.models.RestaurantMemStore
import org.setu.restauranttracker.models.RestaurantModel

val store = RestaurantMemStore()

fun main() {
    println("=== RestaurantTracker Console App (Lab 1) ===")

    var input: Int

    do {
        input = menu()

        when (input) {
            1 -> addRestaurant()
            2 -> listRestaurants()
            3 -> updateRestaurant()
            4 -> deleteRestaurant()
            5 -> searchRestaurant()
            0 -> println("\nExiting RestaurantTracker. Goodbye!")
            else -> println("\nInvalid option. Please try again.")
        }

    } while (input != 0)
}

fun menu(): Int {
    println("\n----------------------------------")
    println(" MAIN MENU")
    println("----------------------------------")
    println(" 1. Add Restaurant")
    println(" 2. List All Restaurants")
    println(" 3. Update a Restaurant")
    println(" 4. Delete a Restaurant")
    println(" 5. Search Restaurant by ID")
    println(" 0. Exit")

    print("\nEnter option: ")
    return readlnOrNull()?.toIntOrNull() ?: -1
}

fun addRestaurant() {
    println("\n--- Add Restaurant ---")

    print("Enter Name: ")
    val name = readlnOrNull()?.trim().orEmpty()

    print("Enter Location: ")
    val location = readlnOrNull()?.trim().orEmpty()

    print("Enter Rating (1-5): ")
    val rating = readlnOrNull()?.toIntOrNull() ?: 0

    if (name.isNotEmpty() && location.isNotEmpty() && rating in 1..5) {
        val restaurant = RestaurantModel(
            name = name,
            location = location,
            rating = rating
        )

        store.create(restaurant)

        println("Restaurant added successfully with ID: ${restaurant.id}")
    } else {
        println("Invalid restaurant details. Creation cancelled.")
    }
}

fun listRestaurants() {
    println("\n--- List All Restaurants ---")

    val restaurants = store.findAll()

    if (restaurants.isEmpty()) {
        println("No restaurants found.")
    } else {
        restaurants.forEach { restaurant ->
            println(
                "ID: ${restaurant.id}, Name: ${restaurant.name}, " +
                        "Location: ${restaurant.location}, Rating: ${restaurant.rating}/5"
            )
        }
    }
}

fun updateRestaurant() {
    println("\n--- Update Restaurant ---")

    listRestaurants()

    if (store.findAll().isEmpty()) return

    print("\nEnter ID of Restaurant to update: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null && store.findOne(id) != null) {
        print("Enter New Name: ")
        val name = readlnOrNull()?.trim().orEmpty()

        print("Enter New Location: ")
        val location = readlnOrNull()?.trim().orEmpty()

        print("Enter New Rating (1-5): ")
        val rating = readlnOrNull()?.toIntOrNull() ?: 0

        if (name.isNotEmpty() && location.isNotEmpty() && rating in 1..5) {
            val updated = store.update(
                RestaurantModel(
                    id = id,
                    name = name,
                    location = location,
                    rating = rating
                )
            )

            if (updated) println("Restaurant updated successfully.")
        } else {
            println("Invalid restaurant details. Update cancelled.")
        }
    } else {
        println("Restaurant with ID $id not found.")
    }
}

fun deleteRestaurant() {
    println("\n--- Delete Restaurant ---")

    listRestaurants()

    if (store.findAll().isEmpty()) return

    print("\nEnter ID of Restaurant to delete: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null) {
        val deleted = store.delete(id)

        if (deleted) {
            println("Restaurant with ID $id deleted successfully.")
        } else {
            println("Restaurant with ID $id not found.")
        }
    } else {
        println("Invalid ID entered.")
    }
}

fun searchRestaurant() {
    println("\n--- Search Restaurant ---")

    print("Enter ID: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null) {
        val restaurant = store.findOne(id)

        if (restaurant != null) {
            println("Restaurant found:")
            println("ID: ${restaurant.id}")
            println("Name: ${restaurant.name}")
            println("Location: ${restaurant.location}")
            println("Rating: ${restaurant.rating}/5")
        } else {
            println("Restaurant with ID $id not found.")
        }
    } else {
        println("Invalid ID entered.")
    }
}
