package org.setu.restauranttracker.models

data class RestaurantModel(
    var id: Long = 0L,
    var name: String = "",
    var location: String = "",
    var rating: Int = 0
)