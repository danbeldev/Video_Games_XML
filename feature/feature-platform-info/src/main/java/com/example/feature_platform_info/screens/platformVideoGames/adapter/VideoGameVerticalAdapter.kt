package com.example.feature_platform_info.screens.platformVideoGames.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core_model.api.videoGame.VideoGameItem
import com.example.feature_platform_info.databinding.ItemPlatformVideoGameVerticalBinding

internal class VideoGameVerticalAdapter(
    private val onClickVideoGame:(VideoGameItem?) -> Unit
):PagingDataAdapter<VideoGameItem, VideoGameVerticalViewHolder>(VideoGameItemDiffItemCallback) {

    override fun onBindViewHolder(holder: VideoGameVerticalViewHolder, position: Int) {
        val videoGame = getItem(position)

        with(holder.binding){
            videoGameBackgroundImage.load(videoGame?.background_image)
            videoGameTitle.text = videoGame?.name
            this.videoGame.setOnClickListener { onClickVideoGame(videoGame) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoGameVerticalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlatformVideoGameVerticalBinding.inflate(inflater, parent, false)

        return VideoGameVerticalViewHolder(binding)
    }
}

internal class VideoGameVerticalViewHolder(
    val binding: ItemPlatformVideoGameVerticalBinding
): RecyclerView.ViewHolder(binding.root)

private object VideoGameItemDiffItemCallback : DiffUtil.ItemCallback<VideoGameItem>() {

    override fun areItemsTheSame(oldItem: VideoGameItem, newItem: VideoGameItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: VideoGameItem, newItem: VideoGameItem): Boolean {
        return oldItem.name == newItem.name && oldItem.background_image == newItem.background_image
    }
}