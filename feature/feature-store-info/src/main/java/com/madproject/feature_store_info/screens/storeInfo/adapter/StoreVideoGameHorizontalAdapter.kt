package com.madproject.feature_store_info.screens.storeInfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.madproject.core_model.api.videoGame.VideoGameItem
import com.madproject.feature_store_info.databinding.ItemStoreVideoGameHorizontalBinding

internal class StoreVideoGameHorizontalAdapter(
    private val onClickVideoGame:(VideoGameItem?) -> Unit
) : PagingDataAdapter<VideoGameItem, VideoGameHorizontalViewHolder>(VideoGameItemDiffItemCallback) {

    override fun onBindViewHolder(holder: VideoGameHorizontalViewHolder, position: Int) {
        val videoGame = getItem(position)

        with(holder.binding){
            videoGameBackgroundImage.load(videoGame?.background_image)
            videoGameBackgroundImage.setOnClickListener { onClickVideoGame(videoGame) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): VideoGameHorizontalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStoreVideoGameHorizontalBinding.inflate(inflater, parent, false)

        return VideoGameHorizontalViewHolder(binding)
    }

}

internal class VideoGameHorizontalViewHolder(
    val binding:ItemStoreVideoGameHorizontalBinding
):RecyclerView.ViewHolder(binding.root)

private object VideoGameItemDiffItemCallback : DiffUtil.ItemCallback<VideoGameItem>() {

    override fun areItemsTheSame(oldItem: VideoGameItem, newItem: VideoGameItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: VideoGameItem, newItem: VideoGameItem): Boolean {
        return oldItem.name == newItem.name && oldItem.background_image == newItem.background_image
    }
}