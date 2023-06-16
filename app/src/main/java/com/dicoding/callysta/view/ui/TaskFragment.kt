package com.dicoding.callysta.view.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.callysta.R
import com.dicoding.callysta.viewmodel.ViewModelFactory
import com.dicoding.callysta.databinding.FragmentTaskBinding
import com.dicoding.callysta.model.Progress
import com.dicoding.callysta.model.QuestionItem
import com.dicoding.callysta.model.QuestionResponse
import com.dicoding.callysta.utils.Response
import com.dicoding.callysta.view.adapter.TaskAdapter
import com.dicoding.callysta.viewmodel.HomepageViewModel

class TaskFragment(private val dataStore: DataStore<Preferences>, private val type: Int) : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding
        get() = _binding!!

    private val homepageViewModel: HomepageViewModel by viewModels {
        ViewModelFactory.getInstance(dataStore)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewSetup(view)

        homepageViewModel.getProgress().observe(viewLifecycleOwner) { progress ->
            homepageViewModel.getQuestionList().observe(viewLifecycleOwner) {response ->
                when (response) {
                    is Response.Success -> {
                        //showLoading(false)
                        showTasks(response.data, progress, view)
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
    }

    private fun showTasks(data: QuestionResponse, progress: Progress, view: View) {
        val adapter = when(type) {
            0 -> TaskAdapter(data.read, progress, type)
            1 -> TaskAdapter(data.write, progress, type)
            else -> throw IndexOutOfBoundsException()
        }
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