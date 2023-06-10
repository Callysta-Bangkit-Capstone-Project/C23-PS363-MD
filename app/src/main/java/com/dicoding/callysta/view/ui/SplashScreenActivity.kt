package com.dicoding.callysta.view.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.core.app.ActivityOptionsCompat
import com.dicoding.callysta.databinding.ActivitySplashScreenBinding
import androidx.core.util.Pair

@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    private val binding: ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, HomepageActivity::class.java)
            val optionCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, Pair(binding.logoImageView, "logo")
            )
            startActivity(intent, optionCompat.toBundle())
            finish()
        }, DURATION_3_SEC)

    }

    companion object {
        private const val DURATION_3_SEC = 3_000L
    }
}