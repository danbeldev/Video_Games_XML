package com.example.feature_main.screens.feedScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.core_model.api.platform.PlatformName
import com.example.core_model.api.videoGame.VideoGameItem
import com.example.feature_main.databinding.ItemVideoGameHorizontalBinding

internal class VideoGamesHorizontalAdapter(
    private val onClickVideoGame:(Int) -> Unit
) : PagingDataAdapter<VideoGameItem, VideoGamesViewHolder>(VideoGameItemDiffItemCallback) {
    override fun onBindViewHolder(holder: VideoGamesViewHolder, position: Int) {
        val videoGame = getItem(position) ?: return

        with(holder.binding.videoGameCard){
            setVideoGameTitle(videoGame.name)
            setVideoGameImage(data = videoGame.background_image)
            setVideoGameMetacriticRating(videoGame.metacritic.toString())

            setPlatforms(videoGame.parent_platforms.map { it!!.platform!!.name })

            setVideoGameGenres(videoGame.genres.map { it.name })
            setVideoGameStores(videoGame.stores.map { it.store.name })

            setOnClickListener { onClickVideoGame(videoGame.id) }
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