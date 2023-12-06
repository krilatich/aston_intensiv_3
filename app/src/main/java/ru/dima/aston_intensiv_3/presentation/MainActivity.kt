package ru.dima.aston_intensiv_3.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.dima.aston_intensiv_3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}