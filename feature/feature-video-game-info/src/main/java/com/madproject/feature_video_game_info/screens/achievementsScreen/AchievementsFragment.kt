package com.madproject.feature_video_game_info.screens.achievementsScreen

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.madproject.core_common.extension.launchWhenStarted
import com.madproject.core_network_domain.response.Result
import com.madproject.feature_video_game_info.R
import com.madproject.feature_video_game_info.databinding.FragmentAchievementsBinding
import com.madproject.feature_video_game_info.di.GameInfoComponentViewModel
import com.madproject.feature_video_game_info.screens.achievementsScreen.adapter.AchievementsAdapter
import com.madproject.feature_video_game_info.screens.achievementsScreen.viewModel.AchievementsViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AchievementsFragment : Fragment(R.layout.fragment_achievements){

    companion object {
        const val VIDEO_GAME_ID_KEY = "video_game_id"
    }

    @Inject
    internal lateinit var achievementsViewModelFactory:Lazy<AchievementsViewModel.Factory>

    private val viewModel by viewModels<AchievementsViewModel> {
        achievementsViewModelFactory.get()
    }

    private var achievementsAdapter:AchievementsAdapter? = null

    override fun onAttach(context: Context) {
        ViewModelProvider(this)[GameInfoComponentViewModel::class.java]
            .gameInfoDetailsComponent.injectAchievements(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentAchievementsBinding.bind(view)

        val viewGameId = arguments?.getInt(VIDEO_GAME_ID_KEY) ?: 0

        val achievementsLayoutManager = LinearLayoutManager(this.context)

        viewModel.getAchievements(viewGameId)
        viewModel.responseAchievements.onEach {
            when(val result = it){
                is Result.Error -> Unit
                is Result.Loading -> Unit
                is Result.Success -> {
                    achievementsAdapter = AchievementsAdapter(result.data!!.results)
                    binding.achievements.adapter = achievementsAdapter
                }
            }
        }.launchWhenStarted(lifecycle, lifecycleScope)

        binding.achievements.layoutManager = achievementsLayoutManager
    }
}