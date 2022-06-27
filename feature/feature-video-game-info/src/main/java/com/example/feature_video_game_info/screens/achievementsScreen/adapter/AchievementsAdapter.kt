package com.example.feature_video_game_info.screens.achievementsScreen.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core_model.api.AchievementItem
import com.example.feature_video_game_info.databinding.ItemAchievementMoreBinding

internal class AchievementsAdapter(
    private val achievements:List<AchievementItem>
):RecyclerView.Adapter<AchievementsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAchievementMoreBinding.inflate(inflater, parent, false)

        return AchievementsViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AchievementsViewHolder, position: Int) {
        val achievement = achievements[position]

        with(holder.binding){
            achievementImage.load(achievement.image)
            achievementTitle.text = achievement.name
            achievementDescription.text = achievement.description
            achievementPercent.text = "${achievement.percent} %"
        }
    }

    override fun getItemCount(): Int = achievements.size
}

internal class AchievementsViewHolder(
    val binding: ItemAchievementMoreBinding
):RecyclerView.ViewHolder(binding.root)