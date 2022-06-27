package com.example.feature_video_game_info.screens.videoGameInfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core_model.api.TrailerItem
import com.example.feature_video_game_info.databinding.ItemTrailerBinding

internal class TrailerAdapter(
    private val trailer: List<TrailerItem>,
    private val onClickTrailerPreview:(Int) -> Unit
):RecyclerView.Adapter<TrailerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTrailerBinding.inflate(inflater, parent, false)

        return TrailerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val trailerItem = trailer[position]

        with(holder.binding){
            videoPreview.load(trailerItem.preview)
            videoPreview.setOnClickListener { onClickTrailerPreview(trailerItem.id) }
        }
    }

    override fun getItemCount(): Int  = trailer.size

}

internal class TrailerViewHolder(
    val binding:ItemTrailerBinding
):RecyclerView.ViewHolder(binding.root)