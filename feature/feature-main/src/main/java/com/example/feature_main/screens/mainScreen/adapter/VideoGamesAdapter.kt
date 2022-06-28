package com.example.feature_main.screens.mainScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core_model.api.VideoGameItem
import com.example.feature_main.databinding.ItemVideoGameHorizontalBinding

internal class VideoGamesPagerAdapter(
    private val onClickVideoGame:(Int) -> Unit
) : PagingDataAdapter<VideoGameItem, VideoGamesViewHolder>(VideoGameItemDiffItemCallback) {
    override fun onBindViewHolder(holder: VideoGamesViewHolder, position: Int) {
        val videoGame = getItem(position)

        with(holder.binding){
            videoGameTitle.text = videoGame?.name
            videoGameImage.load(videoGame?.background_image)
            videoGameCard.setOnClickListener { videoGame?.id?.let { onClickVideoGame(it) } }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoGamesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVideoGameHorizontalBinding.inflate(inflater, parent, false)

        return VideoGamesViewHolder(binding)
    }

}

internal class VideoGamesViewHolder(
    val binding: ItemVideoGameHorizontalBinding
): RecyclerView.ViewHolder(binding.root)

private object VideoGameItemDiffItemCallback : DiffUtil.ItemCallback<VideoGameItem>() {

    override fun areItemsTheSame(oldItem: VideoGameItem, newItem: VideoGameItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: VideoGameItem, newItem: VideoGameItem): Boolean {
        return oldItem.name == newItem.name && oldItem.background_image == newItem.background_image
    }
}