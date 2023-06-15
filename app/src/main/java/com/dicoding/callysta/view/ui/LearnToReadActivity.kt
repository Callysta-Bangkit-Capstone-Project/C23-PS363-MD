package com.dicoding.callysta.view.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dicoding.callysta.R
import com.dicoding.callysta.databinding.ActivityLearnToReadBinding
import com.github.squti.androidwaverecorder.WaveRecorder
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class LearnToReadActivity : AppCompatActivity() {

    private val binding: ActivityLearnToReadBinding by lazy {
        ActivityLearnToReadBinding.inflate(layoutInflater)
    }

    private var fileName: String = ""

    private var waveRecorder: WaveRecorder? = null
    private var onRecord: Boolean = false

    private var player: MediaPlayer? = null
    private var onPlay: Boolean = false


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

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSION
            )
        }

        binding.btnRec.setOnTouchListener { v, event ->
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

    companion object {
        private const val TAG = "LearnToReadActivity"

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.RECORD_AUDIO)
        private const val REQUEST_CODE_PERMISSION = 10
    }
}

