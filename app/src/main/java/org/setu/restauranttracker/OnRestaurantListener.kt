package org.setu.restauranttracker

import org.setu.restauranttracker.models.RestaurantModel

interface OnRestaurantListener {
    fun onRestaurantClick(restaurant: RestaurantModel)

    fun onRestaurantLongClick(restaurant: RestaurantModel): Boolean
}