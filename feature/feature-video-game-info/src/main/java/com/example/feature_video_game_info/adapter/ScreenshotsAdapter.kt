package com.example.feature_video_game_info.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core_model.api.ScreenshotItem
import com.example.feature_video_game_info.databinding.ItemScreenshotsBinding

internal class ScreenshotsAdapter: PagingDataAdapter<ScreenshotItem, ScreenshotsViewHolder>(ScreenshotsDiffItemCallback){
    override fun onBindViewHolder(holder: ScreenshotsViewHolder, position: Int) {
        val screenshot = getItem(position)

        with(holder.binding){
            this.screenshot.load(screenshot?.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemScreenshotsBinding.inflate(inflater, parent, false)

        return ScreenshotsViewHolder(binding)
    }
}

internal class ScreenshotsViewHolder(
    val binding: ItemScreenshotsBinding
):RecyclerView.ViewHolder(binding.root)

private object ScreenshotsDiffItemCallback : DiffUtil.ItemCallback<ScreenshotItem>() {

    override fun areItemsTheSame(oldItem: ScreenshotItem, newItem: ScreenshotItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ScreenshotItem, newItem: ScreenshotItem): Boolean {
        return oldItem.image == newItem.image && oldItem.hidden == newItem.hidden
    }
}