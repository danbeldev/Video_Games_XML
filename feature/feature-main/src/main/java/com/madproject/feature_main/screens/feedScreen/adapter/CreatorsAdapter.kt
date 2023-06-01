package com.madproject.feature_main.screens.feedScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.madproject.core_common.extension.replaceRange
import com.madproject.core_model.api.creator.CreatorGameItem
import com.madproject.core_model.api.creator.CreatorItem
import com.madproject.feature_main.databinding.ItemCreatorBinding

internal class CreatorsAdapter(
    private val onClickCreator:(CreatorItem?) -> Unit,
    private val onClickVideoGame:(CreatorGameItem?) -> Unit
): PagingDataAdapter<CreatorItem, CreatorViewHolder>(CreatorItemDiffItemCallback) {
    override fun onBindViewHolder(holder: CreatorViewHolder, position: Int) {

        val creatorItem = getItem(position)

        with(holder.binding.creator){

            setImageBackground(data = creatorItem?.image_background)
            setPersonPhoto(data = creatorItem?.image)

            personName(creatorItem?.name ?: "")

            personPositions(creatorItem?.positions?.map { it.name })
            gamesCount((creatorItem?.games_count ?: 0).toString())

            creatorItem?.games?.let {
                if (creatorItem.games.lastIndex >= 0){
                    val item = creatorItem.games[0]
                    videoGameOneText(text = item.name.replaceRange(25))
                    videoGameOneCount(text = (item.added ?: 0).toString())
                    onVideoGameOneClick { onClickVideoGame(item) }
                }

                if (creatorItem.games.lastIndex >= 1){
                    val item = creatorItem.games[1]
                    videoGameTwoText(text = item.name.replaceRange(25))
                    videoGameTwoCount(text = (item.added ?: 0).toString())

                    onVideoGameTwoClick { onClickVideoGame(item) }
                }

                if (creatorItem.games.lastIndex >= 2){
                    val item = creatorItem.games[2]

                    videoGameThreeText(text = item.name.replaceRange(25))
                    videoGameThreeCount(text = (item.added ?: 0).toString())

                    onVideoGameThreeClick { onClickVideoGame(item) }
                }
            }

            setOnClickListener { onClickCreator(creatorItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCreatorBinding.inflate(inflater, parent, false)

        return CreatorViewHolder(binding)
    }
}

internal class CreatorViewHolder(
    val binding: ItemCreatorBinding
): RecyclerView.ViewHolder(binding.root)

private object CreatorItemDiffItemCallback : DiffUtil.ItemCallback<CreatorItem>() {

    override fun areItemsTheSame(oldItem: CreatorItem, newItem: CreatorItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CreatorItem, newItem: CreatorItem): Boolean {
        return oldItem.name == newItem.name && oldItem.image == newItem.image
    }
}