package com.dicoding.callysta.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.dicoding.callysta.R
import com.dicoding.callysta.databinding.ActivitySubLevelBinding

class SubLevelActivity : AppCompatActivity() {

    private val binding: ActivitySubLevelBinding by lazy {
        ActivitySubLevelBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)
    }
}