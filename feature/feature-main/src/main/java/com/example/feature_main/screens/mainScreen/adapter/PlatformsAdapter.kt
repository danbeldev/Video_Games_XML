package com.example.feature_main.screens.mainScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core_model.api.platform.PlatformItem
import com.example.feature_main.databinding.ItemPlatformBinding

internal class PlatformsAdapter(
  private val onPlatformItemClick:(PlatformItem?) -> Unit
) : PagingDataAdapter<PlatformItem, PlatformViewHolder>(PlatformDiffItemCallback){

    override fun onBindViewHolder(holder: PlatformViewHolder, position: Int) {
        val platform = getItem(position)

        with(holder.binding){
            platformName.text = platform?.name
            platformImageBackground.load(platform?.image ?: platform?.image_background)

            platformItem.setOnClickListener { onPlatformItemClick(platform) }
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

