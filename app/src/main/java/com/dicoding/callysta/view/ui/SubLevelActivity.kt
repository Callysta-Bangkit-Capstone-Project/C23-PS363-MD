package com.dicoding.callysta.view.ui

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.callysta.databinding.ActivitySubLevelBinding
import com.dicoding.callysta.model.Progress
import com.dicoding.callysta.model.SublevelItem
import com.dicoding.callysta.view.adapter.SubLevelAdapter
import java.util.ArrayList

class SubLevelActivity : AppCompatActivity() {

    private val binding: ActivitySubLevelBinding by lazy {
        ActivitySubLevelBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)

        val layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        binding.subLevelRecyclerView.layoutManager = layoutManager

        val data = if (Build.VERSION.SDK_INT >= 33) {
            Log.d(ContentValues.TAG, "onCreate: 0")
            intent.getParcelableArrayListExtra(EXTRA_QUESTION, SublevelItem::class.java)
        } else {
            Log.d(ContentValues.TAG, "onCreate: 1")
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra(EXTRA_QUESTION)
        }

        showSubLevel(data)

        binding.subHeader.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun showSubLevel(data: ArrayList<SublevelItem>?) {
        val progress  = if (Build.VERSION.SDK_INT >= 33) {
            Log.d(ContentValues.TAG, "onCreate: 0")
            intent.getParcelableExtra(EXTRA_PROGRESS, Progress::class.java)
        } else {
            Log.d(ContentValues.TAG, "onCreate: 1")
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_PROGRESS)
        }

        val adapter = SubLevelAdapter(data, progress)

        binding.subLevelRecyclerView.adapter = adapter
    }

    companion object {
        const val EXTRA_QUESTION = "extra_question"
        const val EXTRA_PROGRESS = "extra_progress"
    }
}