package com.dicoding.callysta.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.callysta.databinding.ItemRowSublevelBinding
import com.dicoding.callysta.model.Task

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
    }

}