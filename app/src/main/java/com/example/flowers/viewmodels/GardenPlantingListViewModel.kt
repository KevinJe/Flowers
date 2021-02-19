package com.example.flowers.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.flowers.data.GardenPlantingRepository
import com.example.flowers.data.Plant
import com.example.flowers.data.PlantAndGardenPlantings
import com.example.flowers.data.PlantRepository

class GardenPlantingListViewModel @ViewModelInject internal constructor(
    gardenPlantingRepository: GardenPlantingRepository,
    plantRepository: PlantRepository
): ViewModel() {

    val plantAndGardenPlantings: LiveData<List<PlantAndGardenPlantings>> =
        gardenPlantingRepository.getPlantedGardens().asLiveData()

    val plants: LiveData<List<Plant>> =
        plantRepository.getPlants().asLiveData()

}