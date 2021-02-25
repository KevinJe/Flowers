package com.example.flowers.viewmodels

import androidx.hilt.Assisted
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.flowers.BuildConfig
import com.example.flowers.data.GardenPlantingRepository
import com.example.flowers.data.PlantRepository
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

/**
 * The ViewModel used in [PlantDetailFragment].
 */
class PlantDetailViewModel @AssistedInject constructor(
    plantRepository: PlantRepository,
    private val gardenPlantingRepository: GardenPlantingRepository,
    @Assisted private val plantId: String
) : ViewModel() {

    val isPlanted = gardenPlantingRepository.isPlanted(plantId).asLiveData()
    val plant = plantRepository.getPlant(plantId).asLiveData()

    fun hasValidUnsplashKey() = (BuildConfig.UNSPLASH_ACCESS_KEY != "null")

    fun addPlantToGarden() {
        viewModelScope.launch {
            gardenPlantingRepository.createGardenPlanting(plantId)
        }
    }

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(planted: String): PlantDetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            planted: String
        ): ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(planted) as T
            }

        }
    }




}