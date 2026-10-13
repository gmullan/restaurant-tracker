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
}