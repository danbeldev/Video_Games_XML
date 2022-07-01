package com.example.feature_video_game_info.screens.videoGameInfo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core_model.api.creator.CreatorItem
import com.example.feature_video_game_info.databinding.ItemDeveloperTeamBinding

internal class DeveloperTeamAdapter(
  private val onClickDeveloperTeam:(CreatorItem?) -> Unit
) :PagingDataAdapter<CreatorItem, DeveloperTeamViewHolder>(DeveloperTeamDiffItemCallback) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DeveloperTeamViewHolder, position: Int) {
        val developerTeam = getItem(position)

        var developerTeamPositions = ""
        developerTeam?.positions?.forEachIndexed { index, item ->
            developerTeamPositions = if (index == 0)
                if (developerTeam.positions.lastIndex == 0)
                    "${item.name}."
                else
                    item.name
            else if (developerTeam.positions.lastIndex == index)
                "$developerTeamPositions, ${item.name}."
            else
                "$developerTeamPositions, ${item.name}"
        }

        with(holder.binding){
            photoDeveloperTeam.load(developerTeam?.image)
            developerTeamName.text = developerTeam?.name
            developerTeamGameCount.text = "Game count: ${developerTeam?.games_count ?: 0}"
            this.developerTeamPositions.text = developerTeamPositions

            this.developerTeam.setOnClickListener { onClickDeveloperTeam(developerTeam) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeveloperTeamViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDeveloperTeamBinding.inflate(inflater, parent, false)

        return DeveloperTeamViewHolder(binding)
    }

}

internal class DeveloperTeamViewHolder(
    val binding:ItemDeveloperTeamBinding
):RecyclerView.ViewHolder(binding.root)


private object DeveloperTeamDiffItemCallback : DiffUtil.ItemCallback<CreatorItem>() {

    override fun areItemsTheSame(oldItem: CreatorItem, newItem: CreatorItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CreatorItem, newItem: CreatorItem): Boolean {
        return oldItem.image == newItem.image && oldItem.name == newItem.name
    }
}