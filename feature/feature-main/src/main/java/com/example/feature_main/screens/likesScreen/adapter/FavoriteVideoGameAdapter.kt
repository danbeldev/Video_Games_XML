package com.example.feature_main.screens.likesScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core_model.database.room.favoriteVideoGame.FavoriteVideoGameDTO
import com.example.feature_main.databinding.ItemFavoriteVideoGameBinding

internal class FavoriteVideoGameAdapter(
    private val onClickItem:(FavoriteVideoGameDTO) -> Unit
): PagingDataAdapter<FavoriteVideoGameDTO, FavoriteVideoGameViewHolder>(FavoriteVideoGameDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteVideoGameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFavoriteVideoGameBinding.inflate(inflater, parent, false)

        return FavoriteVideoGameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteVideoGameViewHolder, position: Int) {
        val item = getItem(position) ?: return

        with(holder.binding){
            backgroundImage.load(item.backgroundImage)
            videoGameName.text = item.name
            videoGameItem.setOnClickListener { onClickItem(item) }
        }
    }
}

internal class FavoriteVideoGameViewHolder(
    val binding:ItemFavoriteVideoGameBinding
):RecyclerView.ViewHolder(binding.root)

internal object FavoriteVideoGameDiffItemCallback:DiffUtil.ItemCallback<FavoriteVideoGameDTO>(){

    override fun areItemsTheSame(
        oldItem: FavoriteVideoGameDTO,
        newItem: FavoriteVideoGameDTO
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: FavoriteVideoGameDTO,
        newItem: FavoriteVideoGameDTO
    ): Boolean = oldItem.id == newItem.id
}

