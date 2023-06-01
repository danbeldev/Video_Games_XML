package com.madproject.feature_video_game_info.screens.videoGameInfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.madproject.core_model.api.videoGame.VideoGameItem
import com.madproject.feature_video_game_info.databinding.ItemSeriesHorizontalBinding

internal class SeriesAdapter(
    private val onClickSeries:(VideoGameItem?) -> Unit
) : PagingDataAdapter<VideoGameItem, SeriesViewHolder>(SeriesDiffItemCallback) {

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val series = getItem(position)

        with(holder.binding){
            videoGameImage.load(series?.background_image)
            videoGameTitle.text = series?.name
            this.series.setOnClickListener { onClickSeries(series) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSeriesHorizontalBinding.inflate(inflater, parent, false)

        return SeriesViewHolder(binding)
    }
}


internal class SeriesViewHolder(
    val binding:ItemSeriesHorizontalBinding
):RecyclerView.ViewHolder(binding.root)

private object SeriesDiffItemCallback : DiffUtil.ItemCallback<VideoGameItem>() {

    override fun areItemsTheSame(oldItem: VideoGameItem, newItem: VideoGameItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: VideoGameItem, newItem: VideoGameItem): Boolean {
        return oldItem.background_image == newItem.background_image && oldItem.name == newItem.name
    }
}