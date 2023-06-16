package com.dicoding.callysta.view.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.callysta.view.ui.TaskFragment

class TaskPagerAdapter(activity: AppCompatActivity, private val dataStore: DataStore<Preferences>) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {

        val fragment = TaskFragment(dataStore, position)

        fragment.arguments = Bundle().apply {
            putString(TaskFragment.TASKS, when (position) {
                BACA -> "baca"
                TULIS -> "tulis"
                else -> throw IndexOutOfBoundsException()
            })
        }

        return fragment

    }

    override fun getItemCount(): Int = 2

    companion object {
        private const val BACA = 0
        private const val TULIS = 1
    }
}