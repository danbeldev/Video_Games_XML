package com.example.feature_video_game_info.screens.videoGameInfo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core_model.api.videoGame.AchievementItem
import com.example.feature_video_game_info.databinding.ItemAchievementBinding

internal class AchievementAdapter(
    private val achievements:List<AchievementItem>,
    private val onClickAchievement:(AchievementItem) -> Unit
): RecyclerView.Adapter<AchievementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAchievementBinding.inflate(inflater, parent, false)

        return AchievementViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val achievement = achievements[position]
        with(holder.binding){
            achievementImage.load(achievement.image)
            achievementTitle.text = "${achievement.name}, ${achievement.percent}"
            achievementDescription.text = achievement.description
            this.achievement.setOnClickListener { onClickAchievement(achievement) }
        }
    }

    override fun getItemCount(): Int = achievements.size
}

internal class AchievementViewHolder(
    val binding: ItemAchievementBinding
): RecyclerView.ViewHolder(binding.root)