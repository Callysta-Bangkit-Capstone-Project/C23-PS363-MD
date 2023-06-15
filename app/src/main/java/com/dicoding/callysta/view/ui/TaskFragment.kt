package com.dicoding.callysta.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.callysta.R
import com.dicoding.callysta.ViewModelFactory
import com.dicoding.callysta.databinding.FragmentTaskBinding
import com.dicoding.callysta.core.model.Task
import com.dicoding.callysta.core.response.QuestionResponse
import com.dicoding.callysta.core.response.WriteItem
import com.dicoding.callysta.core.utils.Response
import com.dicoding.callysta.view.adapter.TaskAdapter
import com.dicoding.callysta.viewmodel.HomepageViewModel


class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding
        get() = _binding!!

    private val homepageViewModel: HomepageViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewSetup(view)
        homepageViewModel.getQuestionList().observe(viewLifecycleOwner) {response ->
            when (response) {
                is Response.Success -> {
                    //showLoading(false)
                    showTasks(response.data)
                }
                is Response.Error -> {
                    //showLoading(false)
                    Toast.makeText(view.context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
                is Response.Loading -> {
                    //showLoading(true)
                }

            }
        }
    }

    private fun showTasks(data: QuestionResponse) {
        val adapter = TaskAdapter(data.write)

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