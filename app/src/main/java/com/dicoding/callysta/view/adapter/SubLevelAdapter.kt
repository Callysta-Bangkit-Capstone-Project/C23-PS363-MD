package com.dicoding.callysta.view.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.callysta.R
import com.dicoding.callysta.databinding.ItemRowSublevelBinding
import com.dicoding.callysta.model.Progress
import com.dicoding.callysta.model.SublevelItem
import com.dicoding.callysta.utils.InterfaceUtil.showToast
import com.dicoding.callysta.view.ui.LearnToReadActivity
import com.dicoding.callysta.view.ui.LearnToWriteActivity

class SubLevelAdapter(
    private val data: ArrayList<SublevelItem>?,
    private val progress: Progress?,
    private val type: Int,
) :
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

        val subLevel = when (type) {
            0 -> progress?.subLevelRead
            1 -> progress?.subLevelWrite
            else -> throw IndexOutOfBoundsException()
        }

        holder.binding.apply {
            if ((subLevel ?: 1) >= (data?.get(position)?.id ?: Int.MAX_VALUE)) {
                subLevelNumberView.text = data?.get(position)?.id.toString()
                lockImage.visibility = View.INVISIBLE
            } else {
                subLevelNumberView.text = ""
                lockImage.visibility = View.VISIBLE
            }
        }

        holder.itemView.run {
            setOnClickListener {

                if ((subLevel ?: 1) >= (data?.get(position)?.id ?: Int.MAX_VALUE)) {
                    val intent = when (type) {
                        0 -> Intent(holder.itemView.context, LearnToReadActivity::class.java)
                        1 -> Intent(holder.itemView.context, LearnToWriteActivity::class.java)
                        else -> throw IndexOutOfBoundsException()
                    }
                    when (type) {
                        0 -> {
                            intent.also {
                                it.putExtra(
                                    LearnToWriteActivity.IMAGE_URL,
                                    data?.get(position)?.imageUrl
                                )
                                it.putExtra(
                                    LearnToWriteActivity.ACTUAL_ANSWER,
                                    data?.get(position)?.answer
                                )
                                it.putExtra(LearnToWriteActivity.SUB_LEVEL, data?.get(position)?.id)
                                it.putExtra(LearnToWriteActivity.SUB_LEVEL_SIZE, data?.size)
                                it.putExtra(LearnToWriteActivity.EXTRA_PROGRESS, progress)
                                intent.putParcelableArrayListExtra(
                                    LearnToWriteActivity.EXTRA_QUESTION,
                                    data
                                )
                            }
                        }
                        1 -> {
                            intent.also {
                                it.putExtra(
                                    LearnToReadActivity.IMAGE_URL,
                                    data?.get(position)?.imageUrl
                                )
                                it.putExtra(
                                    LearnToReadActivity.ACTUAL_ANSWER,
                                    data?.get(position)?.answer
                                )
                                it.putExtra(LearnToReadActivity.SUB_LEVEL, data?.get(position)?.id)
                                it.putExtra(LearnToReadActivity.SUB_LEVEL_SIZE, data?.size)
                                it.putExtra(LearnToReadActivity.EXTRA_PROGRESS, progress)
                                intent.putParcelableArrayListExtra(
                                    LearnToReadActivity.EXTRA_QUESTION,
                                    data
                                )
                            }
                        }
                    }

                    Log.d(TAG, "onBindViewHolder: $data")
                    context.startActivity(
                        intent,
                        ActivityOptions.makeSceneTransitionAnimation(holder.itemView.context as Activity?)
                            .toBundle()
                    )
                    (context as Activity).finish()
                } else {
                    showToast(
                        holder.itemView.context.getString(R.string.locked),
                        holder.itemView.context
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "SubLevelAdapter"
    }

}