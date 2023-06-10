package com.dicoding.callysta.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.callysta.databinding.ActivityLearnToWriteBinding

class LearnToWriteActivity : AppCompatActivity() {

    private val binding: ActivityLearnToWriteBinding by lazy {
        ActivityLearnToWriteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        binding.apply {


            checkMaterialButton.setOnClickListener {

            }


            clearMaterialButton.setOnClickListener {
                drawView.clearCanvas()
            }



        }

    }
}