package com.example.feature_video_game_info.screens.videoGameInfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core_model.api.videoGame.VideoGameItem
import com.example.feature_video_game_info.databinding.ItemAdditionsHorizontalBinding

internal class AdditionsHorizontalAdapter(
    private val onClickAdditions:(VideoGameItem?) -> Unit
):PagingDataAdapter<VideoGameItem, AdditionsViewHolder>(AdditionsDiffItemCallback) {

    override fun onBindViewHolder(holder: AdditionsViewHolder, position: Int) {
        val additions = getItem(position)

        with(holder.binding){
            videoGameImage.load(additions?.background_image)
            videoGameTitle.text = additions?.name

            this.additions.setOnClickListener { onClickAdditions(additions) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdditionsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAdditionsHorizontalBinding.inflate(inflater, parent, false)

        return AdditionsViewHolder(binding)
    }
}

internal class AdditionsViewHolder(
    val binding:ItemAdditionsHorizontalBinding
):RecyclerView.ViewHolder(binding.root)

private object AdditionsDiffItemCallback : DiffUtil.ItemCallback<VideoGameItem>() {

    override fun areItemsTheSame(oldItem: VideoGameItem, newItem: VideoGameItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: VideoGameItem, newItem: VideoGameItem): Boolean {
        return oldItem.background_image == newItem.background_image && oldItem.name == newItem.name
    }
}