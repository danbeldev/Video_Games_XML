package com.example.feature_main.screens.feedScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.core_common.extension.replaceRange
import com.example.core_model.api.tag.TagItem
import com.example.core_model.api.tag.TagVideoGame
import com.example.feature_main.databinding.ItemTagBinding

internal class TagsAdapter(
    private val onClickVideoGame:(TagVideoGame) -> Unit,
    private val onClickTag:(TagItem) -> Unit
): PagingDataAdapter<TagItem, TagsViewHolder>(TagDiffItemCallback) {

    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) {
        val tag = getItem(position)

        with(holder.binding.tagItem){

            setImageBackground(data = tag?.image_background)

                    title(tag?.name ?: "")

                    gamesCount((tag?.games_count ?: 0).toString())

                    tag?.games?.let {
                if (tag.games.lastIndex >= 0){
                    val item = tag.games[0]
                    videoGameOneText(text = item.name.replaceRange(25))
                    videoGameOneCount(text = (item.added ?: 0).toString())
                    onVideoGameOneClick { onClickVideoGame(item) }
                }

                if (tag.games.lastIndex >= 1){
                    val item = tag.games[1]
                    videoGameTwoText(text = item.name.replaceRange(25))
                    videoGameTwoCount(text = (item.added ?: 0).toString())

                    onVideoGameTwoClick { onClickVideoGame(item) }
                }

                if (tag.games.lastIndex >= 2){
                    val item = tag.games[2]

                    videoGameThreeText(text = item.name.replaceRange(25))
                    videoGameThreeCount(text = (item.added ?: 0).toString())

                    onVideoGameThreeClick { onClickVideoGame(item) }
                }
            }

            setOnClickListener { tag?.let { it1 -> onClickTag(it1) } }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTagBinding.inflate(inflater, parent, false)

        return TagsViewHolder(binding)
    }
}

internal class TagsViewHolder(
    val binding:ItemTagBinding
):RecyclerView.ViewHolder(binding.root)

private object TagDiffItemCallback : DiffUtil.ItemCallback<TagItem>() {

    override fun areItemsTheSame(oldItem: TagItem, newItem: TagItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TagItem, newItem: TagItem): Boolean {
        return oldItem.name == newItem.name && oldItem.image_background == newItem.image_background
    }
}