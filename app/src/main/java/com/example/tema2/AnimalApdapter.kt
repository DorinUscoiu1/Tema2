package com.example.tema2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AnimalAdapter(
    private val animals: List<Animal>,
    private val onDelete: (Animal) -> Unit
) : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameOfAnAnimal: TextView = itemView.findViewById(R.id.nameOfAnAnimal)
        val continent: TextView = itemView.findViewById(R.id.continent)
        val deleteItem: ImageButton = itemView.findViewById(R.id.deleteItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.animal_item, parent, false)
        return AnimalViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val currentAnimal = animals[position]
        holder.nameOfAnAnimal.text = currentAnimal.name
        holder.continent.text = currentAnimal.continent
        holder.deleteItem.setOnClickListener {
            onDelete(currentAnimal)
        }
    }

    override fun getItemCount() = animals.size
}
