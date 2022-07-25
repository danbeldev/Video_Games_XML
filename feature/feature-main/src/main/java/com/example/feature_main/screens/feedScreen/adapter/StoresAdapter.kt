package com.example.feature_main.screens.feedScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.core_common.extension.replaceRange
import com.example.core_model.api.store.StoreGame
import com.example.core_model.api.store.StoreItem
import com.example.feature_main.databinding.ItemStoreBinding

internal class StoresAdapter(
    private val onClickStore:(StoreItem?) -> Unit,
    private val onClickVideoGame:(StoreGame?) -> Unit
):PagingDataAdapter<StoreItem, StoreViewHolder>(StoreDiffItemCallback) {

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = getItem(position)

        with(holder.binding.store){
            setImageBackground(data = store?.image_background)

            title(store?.name ?: "")

            gamesCount((store?.games_count ?: 0).toString())

            store?.games?.let {
                if (store.games.lastIndex >= 0){
                    val item = store.games[0]
                    videoGameOneText(text = item.name.replaceRange(25))
                    videoGameOneCount(text = (item.added ?: 0).toString())
                    onVideoGameOneClick { onClickVideoGame(item) }
                }

                if (store.games.lastIndex >= 1){
                    val item = store.games[1]
                    videoGameTwoText(text = item.name.replaceRange(25))
                    videoGameTwoCount(text = (item.added ?: 0).toString())

                    onVideoGameTwoClick { onClickVideoGame(item) }
                }

                if (store.games.lastIndex >= 2){
                    val item = store.games[2]

                    videoGameThreeText(text = item.name.replaceRange(25))
                    videoGameThreeCount(text = (item.added ?: 0).toString())

                    onVideoGameThreeClick { onClickVideoGame(item) }
                }
            }

            setOnClickListener { onClickStore(store) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStoreBinding.inflate(inflater, parent, false)

        return StoreViewHolder(binding)
    }
}

internal class StoreViewHolder(
    val binding:ItemStoreBinding
):RecyclerView.ViewHolder(binding.root)

private object StoreDiffItemCallback : DiffUtil.ItemCallback<StoreItem>() {

    override fun areItemsTheSame(oldItem: StoreItem, newItem: StoreItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: StoreItem, newItem: StoreItem): Boolean {
        return oldItem.name == newItem.name && oldItem.image_background == newItem.image_background
    }
}