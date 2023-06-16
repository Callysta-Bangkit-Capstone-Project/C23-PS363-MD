package com.dicoding.callysta.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.callysta.R
import com.dicoding.callysta.databinding.ItemRowTaskBinding
import com.dicoding.callysta.model.Progress
import com.dicoding.callysta.model.QuestionItem
import com.dicoding.callysta.model.QuestionResponse
import com.dicoding.callysta.utils.InterfaceUtil.showToast
import com.dicoding.callysta.view.ui.SubLevelActivity


class TaskAdapter(
    private val data: List<QuestionItem>,
    private val progress: Progress
) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemRowTaskBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemRowTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        holder.binding.apply {
            taskNumberTextView.text = data[position].level.toString()
            if (progress.levelWrite >= data[position].level) {
                taskTitleTextView.text = data[position].name
            } else {
                taskTitleTextView.text = holder.itemView.context.getString(R.string.locked)
            }
        }

        holder.itemView.setOnClickListener {
            if (progress.levelWrite >= data[position].level) {
                val intent = Intent(holder.itemView.context, SubLevelActivity::class.java)
                intent.putParcelableArrayListExtra(
                    SubLevelActivity.EXTRA_QUESTION,
                    ArrayList(data[position].sublevel)
                )
                intent.putExtra(SubLevelActivity.EXTRA_PROGRESS, progress)
                holder.itemView.context.startActivity(intent)
            } else {
                showToast(holder.itemView.context.getString(R.string.locked), holder.itemView.context)
            }
        }
    }

}