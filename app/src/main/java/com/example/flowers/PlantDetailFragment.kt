package com.example.flowers

import android.os.Bundle
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.flowers.data.Plant
import com.example.flowers.databinding.FragmentPlantDetailBinding
import com.example.flowers.viewmodels.PlantDetailViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.time.Duration

@AndroidEntryPoint
class PlantDetailFragment : Fragment() {

    private val args: PlantDetailFragmentArgs by navArgs()

    @Inject
    lateinit var plantDetailViewModelFacotry: PlantDetailViewModel.AssistedFactory

    private val plantDetailViewModel: PlantDetailViewModel by viewModels {
        PlantDetailViewModel.provideFactory(
            plantDetailViewModelFacotry, args.plantId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPlantDetailBinding>(
            inflater, R.layout.fragment_plant_detail, container, false
        ).apply {
            viewModel = plantDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            callback = object: Callback {
                override fun add(plant: Plant?) {
                    hideAppBarFab(fab)
                    plantDetailViewModel.addPlantToGarden()
                    Snackbar.make(root, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
            galleryNav.setOnClickListener { navigateToGallery() }
        }

        return binding.root
    }

    private fun navigateToGallery() {
        plantDetailViewModel.plant.value?.let { plant ->
            

        }
    }


    // FloatingActionButtons anchored to AppBarLayouts have their visibility controlled by the scroll position.
    // We want to turn this behavior off to hide the FAB when it is clicked.
    //
    // This is adapted from Chris Banes' Stack Overflow answer: https://stackoverflow.com/a/41442923
    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = true
        fab.hide()
    }

    interface Callback {
        fun add(plant: Plant?)
    }

}