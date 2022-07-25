package com.example.feature_main.screens.feedScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.core_common.extension.replaceRange
import com.example.core_model.api.platform.PlatformGame
import com.example.core_model.api.platform.PlatformItem
import com.example.feature_main.databinding.ItemPlatformBinding

internal class PlatformsAdapter(
  private val onPlatformItemClick:(PlatformItem?) -> Unit,
  private val onClickGame:(PlatformGame?) -> Unit
) : PagingDataAdapter<PlatformItem, PlatformViewHolder>(PlatformDiffItemCallback){

    override fun onBindViewHolder(holder: PlatformViewHolder, position: Int) {
        val platform = getItem(position)

        with(holder.binding.platform){
            setImageBackground(data = platform?.image_background)

            title(platform?.name ?: "")

            gamesCount((platform?.games_count ?: 0).toString())

            platform?.games?.let {
                if (platform.games.lastIndex >= 0){
                    val item = platform.games[0]
                    videoGameOneText(text = item.name.replaceRange(25))
                    videoGameOneCount(text = (item.added ?: 0).toString())
                    onVideoGameOneClick { onClickGame(item) }
                }

                if (platform.games.lastIndex >= 1){
                    val item = platform.games[1]
                    videoGameTwoText(text = item.name.replaceRange(25))
                    videoGameTwoCount(text = (item.added ?: 0).toString())

                    onVideoGameTwoClick { onClickGame(item) }
                }

                if (platform.games.lastIndex >= 2){
                    val item = platform.games[2]

                    videoGameThreeText(text = item.name.replaceRange(25))
                    videoGameThreeCount(text = (item.added ?: 0).toString())

                    onVideoGameThreeClick { onClickGame(item) }
                }
            }

            setOnClickListener { onPlatformItemClick(platform) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatformViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlatformBinding.inflate(inflater, parent, false)

        return PlatformViewHolder(binding)
    }
}

internal class PlatformViewHolder(
    val binding:ItemPlatformBinding
):RecyclerView.ViewHolder(binding.root)

private object PlatformDiffItemCallback : DiffUtil.ItemCallback<PlatformItem>() {

    override fun areItemsTheSame(oldItem: PlatformItem, newItem: PlatformItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PlatformItem, newItem: PlatformItem): Boolean {
        return oldItem.name == newItem.name && oldItem.image == newItem.image
    }
}