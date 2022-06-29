package com.example.feature_video_game_info.screens.videoPlayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core_model.api.videoGame.Trailer
import com.example.core_model.api.videoGame.TrailerItem
import com.example.feature_video_game_info.databinding.ItemVideoPlayerTrailerBinding

internal class VideoPlayerTrailerAdapter(
    private val trailer: Trailer,
    private val onClickTrailerPreview:(TrailerItem) -> Unit
):RecyclerView.Adapter<VideoPlayerTrailerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): VideoPlayerTrailerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVideoPlayerTrailerBinding.inflate(inflater, parent, false)

        return VideoPlayerTrailerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoPlayerTrailerViewHolder, position: Int) {
        val trailerItem = trailer.results[position]

        with(holder.biding){
            videoPlayerPreview.load(trailerItem.preview)
            videoPlayerTitle.text = trailerItem.name
            videoPlayerPreview.setOnClickListener { onClickTrailerPreview(trailerItem) }
        }
    }

    override fun getItemCount(): Int = trailer.results.size
}

internal class VideoPlayerTrailerViewHolder(
    val biding:ItemVideoPlayerTrailerBinding
):RecyclerView.ViewHolder(biding.root)