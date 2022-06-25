package com.example.feature_video_game_info.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core_model.api.TrailerItem
import com.example.feature_video_game_info.databinding.ItemTrailerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

internal class TrailerAdapter(
    private val context: Context,
    private val trailer: List<TrailerItem>
):RecyclerView.Adapter<TrailerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTrailerBinding.inflate(inflater, parent, false)

        return TrailerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val trailerItem = trailer[position]

        val exoPlayer = ExoPlayer.Builder(context)
            .build()

        exoPlayer.apply {
            setMediaItem(MediaItem.fromUri(trailerItem.data.max))
            playWhenReady = true
            prepare()
            pause()
        }

        with(holder.binding){
            playerView.player = exoPlayer
        }
    }

    override fun getItemCount(): Int  = trailer.size

}

internal class TrailerViewHolder(
    val binding:ItemTrailerBinding
):RecyclerView.ViewHolder(binding.root)