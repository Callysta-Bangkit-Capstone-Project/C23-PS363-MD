package com.dicoding.callysta.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
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
    private val progress: Progress,
    private val type: Int
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
        val level = when(type) {
            0 -> progress.levelRead
            1 -> progress.levelWrite
            else -> throw IndexOutOfBoundsException()
        }

        holder.binding.apply {
            taskNumberTextView.text = data[position].level.toString()
            if (level >= data[position].level) {
                taskTitleTextView.text = data[position].name
                lockImage.visibility = View.INVISIBLE
            } else {
                taskTitleTextView.text = ""
                lockImage.visibility = View.VISIBLE
            }
        }

        holder.itemView.setOnClickListener {
            if (level >= data[position].level) {
                val intent = Intent(holder.itemView.context, SubLevelActivity::class.java)
                intent.putParcelableArrayListExtra(
                    SubLevelActivity.EXTRA_QUESTION,
                    ArrayList(data[position].sublevel)
                )
                intent.putExtra(SubLevelActivity.EXTRA_PROGRESS, progress)
                intent.putExtra(SubLevelActivity.EXTRA_TYPE, type)
                holder.itemView.context.startActivity(intent)
            } else {
                showToast(holder.itemView.context.getString(R.string.locked), holder.itemView.context)
            }
        }
    }

}