package com.dicoding.callysta.view.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.dicoding.callysta.R
import com.dicoding.callysta.databinding.ActivityLearnToReadBinding
import com.dicoding.callysta.model.Progress
import com.dicoding.callysta.model.SublevelItem
import com.dicoding.callysta.utils.InterfaceUtil
import com.dicoding.callysta.utils.Response
import com.dicoding.callysta.utils.dataStore
import com.dicoding.callysta.view.ui.SubLevelActivity.Companion.EXTRA_TYPE
import com.dicoding.callysta.viewmodel.LearnToReadViewModel
import com.dicoding.callysta.viewmodel.LearnToWriteViewModel
import com.dicoding.callysta.viewmodel.ViewModelFactory
import com.github.squti.androidwaverecorder.WaveRecorder
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class LearnToReadActivity : AppCompatActivity() {

    private val binding: ActivityLearnToReadBinding by lazy {
        ActivityLearnToReadBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<LearnToReadViewModel> {
        ViewModelFactory.getInstance(dataStore)
    }

    private var fileName: String = ""

    private var waveRecorder: WaveRecorder? = null
    private var onRecord: Boolean = false

    private var player: MediaPlayer? = null
    private var onPlay: Boolean = false

    private var data : ArrayList<SublevelItem>? = null
    private var progress : Progress? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)

        data = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableArrayListExtra(LearnToReadActivity.EXTRA_QUESTION, SublevelItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra(LearnToReadActivity.EXTRA_QUESTION)
        }

        progress = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(SubLevelActivity.EXTRA_PROGRESS, Progress::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(SubLevelActivity.EXTRA_PROGRESS)
        }

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSION
            )
        }

        val circularProgressDrawable = CircularProgressDrawable(this@LearnToReadActivity)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(this@LearnToReadActivity)
            .load(intent.getStringExtra(IMAGE_URL))
            .placeholder(circularProgressDrawable)
            .into(binding.exampleImageView)

        binding.btnRec.setOnTouchListener { _, event ->
            val action = event.action

            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    val simpleDateFormat = SimpleDateFormat("yyyy.MM.DD_hh.mm.ss", Locale.US)
                    val date = simpleDateFormat.format(Date())

                    fileName = "${externalCacheDir?.absolutePath}/audiorecord${date}.wav"
                    waveRecorder = WaveRecorder(fileName)

                    binding.tvInstruction.setText(R.string.rec_stop_instruction)
                    binding.btnRec.setImageResource(R.drawable.ic_mic_active)
                    startRecording(waveRecorder)
                }

                MotionEvent.ACTION_UP -> {
                    binding.tvInstruction.setText(R.string.rec_instruction)
                    binding.btnRec.setImageResource(R.drawable.ic_mic)
                    stopRecording(waveRecorder)
                }
            }
            true
        }

        binding.checkMaterialButton.setOnClickListener {
            if (onRecord) {
                onRecord = false
                stopRecording(waveRecorder)
            } else {
                onRecord = true
                startRecording(waveRecorder)
            }
        }

        binding.btnPlay.setOnClickListener {
            if (onPlay) {
                onPlay = false
                binding.btnPlay.setImageResource(R.drawable.ic_play)
                stopPlaying()
            } else {
                onPlay = true
                binding.btnPlay.setImageResource(R.drawable.ic_pause)
                startPlaying()
            }
        }

        binding.checkMaterialButton.setOnClickListener {
            checkAudio()
        }

        binding.subHeader.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun startPlaying() {
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
                setOnCompletionListener {
                    release()
                    binding.btnPlay.setImageResource(R.drawable.ic_play)
                    onPlay = false
                }
            } catch (e: IOException) {
                Toast.makeText(this@LearnToReadActivity, "Silahkan rekam dulu", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "prepare() failed: ${e.message}")
                binding.btnPlay.setImageResource(R.drawable.ic_play)
                onPlay = false
            }
        }
    }

    private fun stopPlaying() {
        player?.release()
        player = null
    }

    private fun startRecording(waveRecorder: WaveRecorder?) {
        binding.wavefromView.clear()
        waveRecorder?.startRecording()
        waveRecorder?.onAmplitudeListener = {
            binding.wavefromView.addAmplitude(it.toFloat())
        }
    }

    private fun stopRecording(waveRecorder: WaveRecorder?) {
        waveRecorder?.stopRecording()
    }

    private fun checkAudio() {
        val file = File(fileName)
        viewModel.checkAudio(file).observe(this) { response ->
            when (response) {
                is Response.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Response.Success -> {
                    binding.progressBar.visibility = View.GONE

                    if (response.data.transcriptions[0].lowercase() == intent.getStringExtra(
                            LearnToWriteActivity.ACTUAL_ANSWER.lowercase()
                        )) {
                        InterfaceUtil.showToast(
                            getString(R.string.draw_valid),
                            this@LearnToReadActivity
                        )

                        viewModel.getProgress().observe(this@LearnToReadActivity) {
                            if (intent.getIntExtra(SUB_LEVEL, 0) == intent.getIntExtra(
                                    SUB_LEVEL_SIZE, -1) ) {
                                progress = Progress(
                                    it.levelRead + 1,
                                    1,
                                    it.levelWrite,
                                    it.subLevelWrite
                                )
                                updateProgress(progress!!)
                            } else if (intent.getIntExtra(SUB_LEVEL, 0) == it.subLevelRead) {
                                progress = Progress(
                                    it.levelRead,
                                    it.subLevelRead + 1,
                                    it.levelWrite,
                                    it.subLevelWrite
                                )
                                updateProgress(progress!!)
                            }
                        }
                    } else {
                        val msg = if (response.data.transcriptions[0] == "") "Suara tidak jelas" else "Terdengar: ${response.data.transcriptions[0]}"
                        InterfaceUtil.showToast(
                            msg,
                            this@LearnToReadActivity
                        )
                    }

                }
                is Response.Error -> {
                    binding.progressBar.visibility = View.GONE
                    InterfaceUtil.showToast(response.error, this@LearnToReadActivity)
                }
            }
        }
    }

    private fun updateProgress(progress: Progress) {
        Log.d(TAG, "onCreate: $progress")
        viewModel.updateProgress(progress)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@LearnToReadActivity, SubLevelActivity::class.java)
        Log.d(TAG, "onBackPressed: $progress")
        intent.putParcelableArrayListExtra(
            SubLevelActivity.EXTRA_QUESTION,
            data
        )
        intent.putExtra(SubLevelActivity.EXTRA_PROGRESS, progress)
        intent.putExtra(SubLevelActivity.EXTRA_TYPE, 0)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val TAG = "LearnToReadActivity"

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.RECORD_AUDIO)
        private const val REQUEST_CODE_PERMISSION = 10

        const val IMAGE_URL = "image_url"
        const val ACTUAL_ANSWER = "actual_answer"
        const val SUB_LEVEL = "sub_level"
        const val SUB_LEVEL_SIZE = "sub_level_size"

        const val EXTRA_QUESTION = "extra_question"
        const val EXTRA_PROGRESS = "extra_progress"
    }
}

