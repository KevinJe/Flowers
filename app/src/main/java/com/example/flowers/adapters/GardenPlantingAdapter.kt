package com.example.flowers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flowers.HomeViewPagerFragment
import com.example.flowers.HomeViewPagerFragmentDirections
import com.example.flowers.R
import com.example.flowers.data.PlantAndGardenPlantings
import com.example.flowers.databinding.ListItemGardenPlantingBinding
import com.example.flowers.viewmodels.PlantAndGardenPlantingsViewModel

class GardenPlantingAdapter :
    ListAdapter<PlantAndGardenPlantings, GardenPlantingAdapter.ViewHolder>(GardenPlantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_garden_planting, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemGardenPlantingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                binding.viewModel?.plantId?.let { plantId ->
                    navigateToPlant(plantId, view)
                }
            }
        }

        private fun navigateToPlant(plantId: String, view: View) {
            val directions = HomeViewPagerFragmentDirections
                .actionViewPagerFragmentToPlantDetailFragment(plantId)
            view.findNavController().navigate(directions)
        }

        fun bind(plantings: PlantAndGardenPlantings) {
            with(binding) {
                viewModel = PlantAndGardenPlantingsViewModel(plantings)
                executePendingBindings()
            }
        }
    }

}

private class GardenPlantDiffCallback : DiffUtil.ItemCallback<PlantAndGardenPlantings>() {

    override fun areItemsTheSame(
        oldItem: PlantAndGardenPlantings,
        newItem: PlantAndGardenPlantings
    ): Boolean {
        return oldItem.plant.plantId == newItem.plant.plantId
    }

    override fun areContentsTheSame(
        oldItem: PlantAndGardenPlantings,
        newItem: PlantAndGardenPlantings
    ): Boolean {
        return oldItem == newItem
    }

}