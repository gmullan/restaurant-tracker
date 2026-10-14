package org.setu.restauranttracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.setu.restauranttracker.models.RestaurantModel

class RestaurantAdapter(
    private val restaurants: List<RestaurantModel>,
    private val onEdit: (RestaurantModel) -> Unit,
    private val onDelete: (RestaurantModel) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    class RestaurantViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val titleText: TextView =
            itemView.findViewById(R.id.titleText)

        val descriptionText: TextView =
            itemView.findViewById(R.id.descriptionText)

        val coordinatesText: TextView =
            itemView.findViewById(R.id.coordinatesText)

        val editButton: Button =
            itemView.findViewById(R.id.editButton)

        val deleteButton: Button =
            itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurant, parent, false)

        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RestaurantViewHolder,
        position: Int
    ) {
        val restaurant = restaurants[position]

        holder.titleText.text = "${restaurant.id}: ${restaurant.name}"
        holder.descriptionText.text = restaurant.location
        holder.coordinatesText.text = "Rating: ${restaurant.rating}/5"

        holder.editButton.setOnClickListener {
            onEdit(restaurant)
        }

        holder.deleteButton.setOnClickListener {
            onDelete(restaurant)
        }
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

}