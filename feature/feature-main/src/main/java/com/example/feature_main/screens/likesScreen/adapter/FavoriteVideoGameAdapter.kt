package com.example.feature_main.screens.likesScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core_model.database.favoriteVideoGame.FavoriteVideoGameDTO
import com.example.feature_main.databinding.ItemFavoriteVideoGameBinding

internal class FavoriteVideoGameAdapter(
    private val videoGames:List<FavoriteVideoGameDTO>,
    private val onClickItem:(FavoriteVideoGameDTO) -> Unit
):RecyclerView.Adapter<FavoriteVideoGameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteVideoGameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFavoriteVideoGameBinding.inflate(inflater, parent, false)

        return FavoriteVideoGameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteVideoGameViewHolder, position: Int) {
        val item = videoGames[position]

        with(holder.binding){
            backgroundImage.load(item.backgroundImage)
            videoGameName.text = item.name
            videoGameItem.setOnClickListener { onClickItem(item) }
        }
    }

    override fun getItemCount(): Int = videoGames.size
}

internal class FavoriteVideoGameViewHolder(
    val binding:ItemFavoriteVideoGameBinding
):RecyclerView.ViewHolder(binding.root)

