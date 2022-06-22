package com.example.feature_video_game_info.screen

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.example.core_common.extension.launchWhenStarted
import com.example.feature_video_game_info.R
import com.example.feature_video_game_info.databinding.FragmentVideoGameInfoBinding
import com.example.feature_video_game_info.di.GameInfoComponentViewModel
import com.example.feature_video_game_info.viewModel.VideoGameInfoViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class VideoGameInfoFragment : Fragment(R.layout.fragment_video_game_info){

    companion object {
        const val VIDEO_GAME_ID_KEY = "video_game_id"
    }

    @Inject
    internal lateinit var videoGameInfoViewModelFactory:Lazy<VideoGameInfoViewModel.Factory>

    private val viewModel by viewModels<VideoGameInfoViewModel>{
        videoGameInfoViewModelFactory.get()
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<GameInfoComponentViewModel>()
            .gameInfoDetailsComponent.inject(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentVideoGameInfoBinding.bind(view)

        val videoGameId = arguments?.getInt(VIDEO_GAME_ID_KEY) ?: 0

        viewModel.getVideoGameInfo(videoGameId)
        viewModel.responseVideoGameInfo.onEach {
            binding.testText.text = it.data?.name ?: videoGameId.toString()
        }.launchWhenStarted(lifecycle,lifecycleScope)
    }

}