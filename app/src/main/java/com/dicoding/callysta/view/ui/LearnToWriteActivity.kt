package com.dicoding.callysta.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.callysta.databinding.ActivityLearnToWriteBinding

class LearnToWriteActivity : AppCompatActivity() {

    private val binding: ActivityLearnToWriteBinding by lazy {
        ActivityLearnToWriteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)

        binding.apply {

            Glide.with(this@LearnToWriteActivity)
                .load(intent.getStringExtra(IMAGE_URL))
                .into(tutorialGIFImageView)


            checkMaterialButton.setOnClickListener {

            }


            clearMaterialButton.setOnClickListener {
                drawView.clearCanvas()
            }



        }

    }

    companion object {
        const val IMAGE_URL = "image_url"
    }
}