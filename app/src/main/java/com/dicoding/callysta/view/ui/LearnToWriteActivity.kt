package com.dicoding.callysta.view.ui

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.dicoding.callysta.R
import com.dicoding.callysta.databinding.ActivityLearnToWriteBinding
import com.dicoding.callysta.model.ImageCheckRequest
import com.dicoding.callysta.model.Progress
import com.dicoding.callysta.model.SublevelItem
import com.dicoding.callysta.utils.InterfaceUtil
import com.dicoding.callysta.utils.Response
import com.dicoding.callysta.utils.dataStore
import com.dicoding.callysta.viewmodel.LearnToWriteViewModel
import com.dicoding.callysta.viewmodel.ViewModelFactory
import java.io.ByteArrayOutputStream

class LearnToWriteActivity : AppCompatActivity() {

    private val binding: ActivityLearnToWriteBinding by lazy {
        ActivityLearnToWriteBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<LearnToWriteViewModel> {
        ViewModelFactory.getInstance(dataStore)
    }

    private var data : ArrayList<SublevelItem>? = null

    private var progress : Progress? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)

        data = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableArrayListExtra(EXTRA_QUESTION, SublevelItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra(EXTRA_QUESTION)
        }

        progress = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(SubLevelActivity.EXTRA_PROGRESS, Progress::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(SubLevelActivity.EXTRA_PROGRESS)
        }

        val circularProgressDrawable = CircularProgressDrawable(this@LearnToWriteActivity)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(this@LearnToWriteActivity)
            .load(intent.getStringExtra(IMAGE_URL))
            .placeholder(circularProgressDrawable)
            .into(binding.exampleImageView)

        binding.apply {

            drawView.setStrokeWidth(50f)

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
                                    viewModel.getProgress().observe(this@LearnToWriteActivity) {
                                        if (intent.getIntExtra(SUB_LEVEL, 0) == intent.getIntExtra(
                                                SUB_LEVEL_SIZE, -1) ) {
                                            progress = Progress(
                                                it.levelRead,
                                                it.subLevelRead,
                                                it.levelWrite + 1,
                                                1
                                            )
                                            updateProgress(progress!!)
                                        } else if (intent.getIntExtra(SUB_LEVEL, 0) == it.subLevelWrite) {
                                            progress = Progress(
                                                it.levelRead,
                                                it.subLevelRead,
                                                it.levelWrite,
                                                it.subLevelWrite + 1
                                            )
                                            updateProgress(progress!!)
                                        }
                                    }
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

            binding.subHeader.btnBack.setOnClickListener {
                onBackPressed()
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

    private fun updateProgress(progress: Progress) {
        Log.d(TAG, "onCreate: $progress")
        viewModel.updateProgress(progress)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@LearnToWriteActivity, SubLevelActivity::class.java)

        Log.d(TAG, "onBackPressed: $progress")
        intent.putParcelableArrayListExtra(
            SubLevelActivity.EXTRA_QUESTION,
            data
        )
        intent.putExtra(SubLevelActivity.EXTRA_PROGRESS, progress)
        intent.putExtra(SubLevelActivity.EXTRA_TYPE, 1)
        startActivity(intent)
        finish()
    }
    companion object {
        private const val TAG = "LearnToWriteActivity"
        const val IMAGE_URL = "image_url"
        const val ACTUAL_ANSWER = "actual_answer"
        const val SUB_LEVEL = "sub_level"
        const val SUB_LEVEL_SIZE = "sub_level_size"

        const val EXTRA_QUESTION = "extra_question"
        const val EXTRA_PROGRESS = "extra_progress"
    }
}