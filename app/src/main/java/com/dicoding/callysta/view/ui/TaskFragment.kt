package com.dicoding.callysta.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.callysta.R
import com.dicoding.callysta.databinding.FragmentTaskBinding
import com.dicoding.callysta.model.Task
import com.dicoding.callysta.view.adapter.TaskAdapter


class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewSetup(view)

        showTasks()
    }

    private fun showTasks() {
        val tasks = mutableListOf<Task>()
        for (i in 1..10) {
            tasks.add(Task("Belajar"))
        }

        val adapter = TaskAdapter(tasks)

        binding.taskRecyclerView.adapter = adapter
    }

    private fun recyclerViewSetup(view: View) {
        val layoutManager = LinearLayoutManager(view.context)
        binding.taskRecyclerView.layoutManager = layoutManager
    }

    companion object {
        const val TASKS = "tasks"
    }

}