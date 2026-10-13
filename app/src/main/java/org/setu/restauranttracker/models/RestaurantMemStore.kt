package org.setu.restauranttracker.models

import java.util.concurrent.atomic.AtomicLong

class RestaurantMemStore {

    private val restaurants = ArrayList<RestaurantModel>()
    private val lastId = AtomicLong(0L)

    fun findAll(): List<RestaurantModel> {
        return restaurants
    }

    fun create(restaurant: RestaurantModel) {
        restaurant.id = lastId.incrementAndGet()
        restaurants.add(restaurant)
    }

    fun findOne(id: Long): RestaurantModel? {
        return restaurants.find { r -> r.id == id }
    }

    fun update(restaurant: RestaurantModel): Boolean {
        val foundRestaurant = findOne(restaurant.id)

        return if (foundRestaurant != null) {
            foundRestaurant.name = restaurant.name
            foundRestaurant.location = restaurant.location
            foundRestaurant.rating = restaurant.rating
            true
        } else {
            false
        }
    }

    fun delete(id: Long): Boolean {
        val foundRestaurant = findOne(id)

        return if (foundRestaurant != null) {
            restaurants.remove(foundRestaurant)
            true
        } else {
            false
        }
    }
}