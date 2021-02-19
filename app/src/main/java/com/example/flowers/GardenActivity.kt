package com.example.flowers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.flowers.databinding.ActivityGardenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GardenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityGardenBinding>(this, R.layout.activity_garden)
    }
}