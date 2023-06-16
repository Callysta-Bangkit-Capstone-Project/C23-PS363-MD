package com.dicoding.callysta.view.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.callysta.databinding.ItemRowSublevelBinding
import com.dicoding.callysta.model.SublevelItem
import com.dicoding.callysta.view.ui.LearnToWriteActivity
import java.util.ArrayList

class SubLevelAdapter(private val data: ArrayList<SublevelItem>?) :
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

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: SubLevelViewHolder, position: Int) {
        holder.binding.apply {
            subLevelNumberView.text = data?.get(position)?.id.toString()
        }

        holder.itemView.run {
            setOnClickListener {
                val intent = Intent(holder.itemView.context, LearnToWriteActivity::class.java)
                intent.also {
                    it.putExtra(LearnToWriteActivity.IMAGE_URL, data?.get(position)?.imageUrl)
                    it.putExtra(LearnToWriteActivity.ACTUAL_ANSWER, data?.get(position)?.answer)
                }
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(holder.itemView.context as Activity?).toBundle())
            }
        }
    }

}