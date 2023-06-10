package com.dicoding.callysta.view.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.callysta.databinding.ItemRowTaskBinding
import com.dicoding.callysta.model.Task
import com.dicoding.callysta.view.ui.SubLevelActivity

class TaskAdapter(private val tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemRowTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemRowTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binding.apply {
            taskNumberTextView.text = (position + 1).toString()
            taskTitleTextView.text = "Belajar"
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, SubLevelActivity::class.java)
            holder.itemView.context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(holder.itemView.context as Activity?).toBundle())
        }
    }

}