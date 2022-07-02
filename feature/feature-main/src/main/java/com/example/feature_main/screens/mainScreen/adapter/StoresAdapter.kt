package com.example.feature_main.screens.mainScreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core_model.api.store.StoreGame
import com.example.core_model.api.store.StoreItem
import com.example.feature_main.databinding.ItemStoreBinding

internal class StoresAdapter(
    private val onClickStore:(StoreItem?) -> Unit,
    private val onClickVideoGame:(StoreGame?) -> Unit
):PagingDataAdapter<StoreItem, StoreViewHolder>(StoreDiffItemCallback) {

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = getItem(position)

        with(holder.binding){
            storeName.text = store?.name
            storeImageBackground.load(store?.image_background)
            popularItemsCount.text = (store?.games_count ?: 0 ).toString()

            this.store.setOnClickListener { onClickStore(store) }

            videoGameOneText.visibility = View.GONE
            videoGameOneCount.visibility = View.GONE

            videoGameTwoText.visibility = View.GONE
            videoGameTwoCount.visibility = View.GONE

            videoGameThreeText.visibility = View.GONE
            videoGameThreeCount.visibility = View.GONE

            store?.games?.get(0)?.let { game ->
                videoGameOneText.visibility = View.VISIBLE
                videoGameOneCount.visibility = View.VISIBLE

                videoGameOneCount.text = game.added.toString()
                videoGameOneText.setOnClickListener { onClickVideoGame(game) }
            }

            store?.games?.get(1)?.let { game ->
                videoGameTwoText.visibility = View.VISIBLE
                videoGameTwoCount.visibility = View.VISIBLE

                videoGameTwoCount.text = game.added.toString()
                videoGameTwoText.setOnClickListener { onClickVideoGame(game) }
            }

            store?.games?.get(2)?.let { game ->
                videoGameThreeText.visibility = View.VISIBLE
                videoGameThreeCount.visibility = View.VISIBLE

                videoGameThreeCount.text = game.added.toString()
                videoGameThreeText.setOnClickListener { onClickVideoGame(game) }
            }
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