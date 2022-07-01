package com.example.feature_creator_info.screens.creatorInfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core_model.api.videoGame.VideoGameItem
import com.example.feature_creator_info.databinding.ItemCreatorVideoGameBinding

internal class CreatorVideoGamesAdapter(
    private val onClickVideoGame:(VideoGameItem?) -> Unit
):PagingDataAdapter<VideoGameItem, CreatorVideoGamesViewHolder>(CreatorVideoGameItemDiffItemCallback) {

    override fun onBindViewHolder(holder: CreatorVideoGamesViewHolder, position: Int) {
        val videoGame = getItem(position)

        with(holder.binding){
            videoGameTitle.text = videoGame?.name
            videoGameImage.load(videoGame?.background_image)

            this.videoGame.setOnClickListener { onClickVideoGame(videoGame) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorVideoGamesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCreatorVideoGameBinding.inflate(inflater, parent, false)

        return CreatorVideoGamesViewHolder(binding)
    }
}

internal class CreatorVideoGamesViewHolder(
    val binding:ItemCreatorVideoGameBinding
):RecyclerView.ViewHolder(binding.root)

private object CreatorVideoGameItemDiffItemCallback : DiffUtil.ItemCallback<VideoGameItem>() {

    override fun areItemsTheSame(oldItem: VideoGameItem, newItem: VideoGameItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: VideoGameItem, newItem: VideoGameItem): Boolean {
        return oldItem.name == newItem.name && oldItem.background_image == newItem.background_image
    }
}