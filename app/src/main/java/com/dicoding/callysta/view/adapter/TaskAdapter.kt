package com.dicoding.callysta.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.callysta.databinding.ItemRowTaskBinding
import com.dicoding.callysta.model.WriteItem
import com.dicoding.callysta.view.ui.SubLevelActivity

class TaskAdapter(private val data: List<WriteItem>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemRowTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemRowTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binding.apply {
            taskNumberTextView.text = data[position].level.toString()
            taskTitleTextView.text = data[position].name
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, SubLevelActivity::class.java)
            intent.putParcelableArrayListExtra(SubLevelActivity.EXTRA_QUESTION, ArrayList(data[position].sublevel))
            holder.itemView.context.startActivity(intent)
        }
    }

}