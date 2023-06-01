package com.madproject.feature_main.screens.videoGamesScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.madproject.core_model.api.videoGame.VideoGameItem
import com.madproject.feature_main.databinding.ItemVideoGameVerticalBinding

internal class VideoGameVerticalAdapter(
    private val onClickVideoGame:(VideoGameItem?) -> Unit
):
    PagingDataAdapter<VideoGameItem, VideoGameVerticalViewHolder>(VideoGameVerticalItemDiffItemCallback) {

    override fun onBindViewHolder(holder: VideoGameVerticalViewHolder, position: Int) {
        val videoGame = getItem(position)

        with(holder.binding){
            videoGameImage.load(videoGame?.background_image)
            videoGameTitle.text = videoGame?.name

            itemVideoGame.setOnClickListener { onClickVideoGame(videoGame) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoGameVerticalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVideoGameVerticalBinding.inflate(inflater, parent, false)

        return VideoGameVerticalViewHolder(binding)
    }

}

internal class VideoGameVerticalViewHolder(
    val binding:ItemVideoGameVerticalBinding
):RecyclerView.ViewHolder(binding.root)

private object VideoGameVerticalItemDiffItemCallback : DiffUtil.ItemCallback<VideoGameItem>() {

    override fun areItemsTheSame(oldItem: VideoGameItem, newItem: VideoGameItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: VideoGameItem, newItem: VideoGameItem): Boolean {
        return oldItem.name == newItem.name && oldItem.background_image == newItem.background_image
    }
}