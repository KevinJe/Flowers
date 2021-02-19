package com.example.flowers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flowers.databinding.FragmentPlantListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPlantListBinding.inflate(inflater, container, false)
        return binding.root
    }
}