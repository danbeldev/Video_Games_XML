package com.madproject.feature_video_game_info.screens.videoGameInfo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.madproject.core_common.extension.replaceRange
import com.madproject.core_model.api.creator.CreatorGameItem
import com.madproject.core_model.api.creator.CreatorItem
import com.madproject.feature_video_game_info.databinding.ItemDeveloperTeamBinding

internal class DeveloperTeamAdapter(
  private val onClickDeveloperTeam:(CreatorItem?) -> Unit,
  private val onClickVideoGame:(CreatorGameItem?) -> Unit
) :PagingDataAdapter<CreatorItem, DeveloperTeamViewHolder>(DeveloperTeamDiffItemCallback) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DeveloperTeamViewHolder, position: Int) {
        val developerTeam = getItem(position)

        with(holder.binding.developerTeam){

            setImageBackground(data = developerTeam?.image_background)
            setPersonPhoto(data = developerTeam?.image)

            personName(developerTeam?.name ?: "")

            personPositions(developerTeam?.positions?.map { it.name })
            gamesCount((developerTeam?.games_count ?: 0).toString())

            developerTeam?.games?.let {
                if (developerTeam.games.lastIndex >= 0){
                    val item = developerTeam.games[0]
                    videoGameOneText(text = item.name.replaceRange(25))
                    videoGameOneCount(text = (item.added ?: 0).toString())
                    onVideoGameOneClick { onClickVideoGame(item) }
                }

                if (developerTeam.games.lastIndex >= 1){
                    val item = developerTeam.games[1]
                    videoGameTwoText(text = item.name.replaceRange(25))
                    videoGameTwoCount(text = (item.added ?: 0).toString())

                    onVideoGameTwoClick { onClickVideoGame(item) }
                }

                if (developerTeam.games.lastIndex >= 2){
                    val item = developerTeam.games[2]

                    videoGameThreeText(text = item.name.replaceRange(25))
                    videoGameThreeCount(text = (item.added ?: 0).toString())

                    onVideoGameThreeClick { onClickVideoGame(item) }
                }
            }

            setOnClickListener { onClickDeveloperTeam(developerTeam) }
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