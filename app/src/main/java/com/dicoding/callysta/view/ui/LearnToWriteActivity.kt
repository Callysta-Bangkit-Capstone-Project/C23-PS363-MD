package com.dicoding.callysta.view.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.callysta.R
import com.dicoding.callysta.databinding.ActivityLearnToWriteBinding
import com.dicoding.callysta.model.ImageCheckRequest
import com.dicoding.callysta.utils.InterfaceUtil
import com.dicoding.callysta.utils.Response
import com.dicoding.callysta.viewmodel.LearnToWriteViewModel
import com.dicoding.callysta.viewmodel.ViewModelFactory
import java.io.ByteArrayOutputStream

class LearnToWriteActivity : AppCompatActivity() {

    private val binding: ActivityLearnToWriteBinding by lazy {
        ActivityLearnToWriteBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<LearnToWriteViewModel> {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)

        binding.apply {

            drawView.setStrokeWidth(50f)

            Glide.with(this@LearnToWriteActivity)
                .load(intent.getStringExtra(IMAGE_URL))
                .into(exampleImageView)


            clearMaterialButton.setOnClickListener {
                drawView.clearCanvas()
            }

            checkMaterialButton.setOnClickListener {

                val imageCheckRequest = getImageCheckRequest(drawView.getBitmap())

                viewModel.checkImage(imageCheckRequest)
                    .observe(this@LearnToWriteActivity) { response ->

                        when (response) {
                            is Response.Loading -> progressBar.visibility = View.VISIBLE
                            is Response.Success -> {
                                progressBar.visibility = View.GONE

                                if (response.data) {
                                    InterfaceUtil.showToast(
                                        getString(R.string.draw_valid),
                                        this@LearnToWriteActivity
                                    )
                                } else {
                                    InterfaceUtil.showToast(
                                        getString(R.string.draw_invalid),
                                        this@LearnToWriteActivity
                                    )
                                }

                            }
                            is Response.Error -> {
                                progressBar.visibility = View.GONE
                                InterfaceUtil.showToast(response.error, this@LearnToWriteActivity)
                            }
                        }

                    }

            }

        }

    }

    private fun getImageCheckRequest(bitmap: Bitmap): ImageCheckRequest {

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        val actualAnswer = intent.getStringExtra(ACTUAL_ANSWER)!!

        return ImageCheckRequest(actualAnswer, Base64.encodeToString(byteArray, Base64.NO_WRAP))

    }

    companion object {
        const val IMAGE_URL = "image_url"
        const val ACTUAL_ANSWER = "actual_answer"
    }
}