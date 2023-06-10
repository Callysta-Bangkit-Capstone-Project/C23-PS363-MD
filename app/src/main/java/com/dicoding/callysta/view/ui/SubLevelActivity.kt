package com.dicoding.callysta.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.callysta.R
import com.dicoding.callysta.databinding.ActivitySubLevelBinding
import com.dicoding.callysta.model.Task
import com.dicoding.callysta.view.adapter.SubLevelAdapter
import com.dicoding.callysta.view.adapter.TaskAdapter

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

        showSubLevel()
    }

    private fun showSubLevel() {
        val level = mutableListOf<Task>()
        for (i in 1..10) {
            level.add(Task("Belajar"))
        }

        val adapter = SubLevelAdapter(level)

        binding.subLevelRecyclerView.adapter = adapter
    }
}