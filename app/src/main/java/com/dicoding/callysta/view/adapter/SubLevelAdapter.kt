package com.dicoding.callysta.view.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.callysta.databinding.ItemRowSublevelBinding
import com.dicoding.callysta.model.Task
import com.dicoding.callysta.view.ui.LearnToWriteActivity
import com.dicoding.callysta.view.ui.SubLevelActivity

class SubLevelAdapter(private val tasks: List<Task>) :
    RecyclerView.Adapter<SubLevelAdapter.SubLevelViewHolder>() {

    inner class SubLevelViewHolder(val binding: ItemRowSublevelBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubLevelAdapter.SubLevelViewHolder {
        val binding =
            ItemRowSublevelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubLevelViewHolder(binding)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: SubLevelViewHolder, position: Int) {
        holder.binding.apply {
            subLevelNumberView.text = (position + 1).toString()
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, LearnToWriteActivity::class.java)
            holder.itemView.context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(holder.itemView.context as Activity?).toBundle())
        }
    }

}