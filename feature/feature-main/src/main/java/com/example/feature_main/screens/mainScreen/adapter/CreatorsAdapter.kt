package com.example.feature_main.screens.mainScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core_model.api.CreatorItem
import com.example.feature_main.databinding.ItemCreatorBinding

internal class CreatorsAdapter
    : PagingDataAdapter<CreatorItem, CreatorViewHolder>(CreatorItemDiffItemCallback) {
    override fun onBindViewHolder(holder: CreatorViewHolder, position: Int) {

        val creatorItem = getItem(position)

        with(holder.binding){
            creatorPhoto.load(data = creatorItem?.image)
            creatorName.text = creatorItem?.name
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