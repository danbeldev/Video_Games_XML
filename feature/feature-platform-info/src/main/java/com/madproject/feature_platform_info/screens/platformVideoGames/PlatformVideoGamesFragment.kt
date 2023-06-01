package com.madproject.feature_platform_info.screens.platformVideoGames

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import com.madproject.core_common.naigation.NavCommand
import com.madproject.core_common.naigation.NavCommands
import com.madproject.core_common.naigation.Screen
import com.madproject.core_common.naigation.navigation
import com.madproject.feature_platform_info.R
import com.madproject.feature_platform_info.databinding.FragmentPlatformVideoGamesBinding
import com.madproject.feature_platform_info.di.PlatformComponentViewModel
import com.madproject.feature_platform_info.screens.platformVideoGames.adapter.VideoGameVerticalAdapter
import com.madproject.feature_platform_info.screens.platformVideoGames.viewModel.PlatformVideoGamesViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class PlatformVideoGamesFragment : Fragment(R.layout.fragment_platform_video_games) {

    companion object {
        const val PLATFORM_ID_KEY = "platform_id"
    }

    @Inject
    internal lateinit var platformVideGamesViewModelFactory:Lazy<PlatformVideoGamesViewModel.Factory>

    private val viewModel by viewModels<PlatformVideoGamesViewModel> {
        platformVideGamesViewModelFactory.get()
    }

    private val videoGamesVerticalAdapter by lazy(LazyThreadSafetyMode.NONE){
        VideoGameVerticalAdapter(
            onClickVideoGame = {
                navigation(
                    NavCommand(
                        NavCommands.DeepLink(
                            url = Uri.parse(Screen.VideoGameInfo.arguments(
                                videoGameId = it?.id ?: 0
                            )),
                            isModal = true,
                            isSingleTop = false
                        )
                    )
                )
            }
        )
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<PlatformComponentViewModel>()
            .platformDetailComponent
            .injectPlatformVideoGames(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentPlatformVideoGamesBinding.bind(view)

        val platformId = arguments?.getInt(PLATFORM_ID_KEY) ?: 0

        val videoGamesLayoutManager = GridLayoutManager(this.context, 2)
        binding.platformVideoGames.layoutManager = videoGamesLayoutManager

        lifecycleScope.launchWhenStarted {
            lifecycle.whenStateAtLeast(Lifecycle.State.STARTED){
                viewModel.getVideoGames(platformId).collectLatest(videoGamesVerticalAdapter::submitData)
            }
        }

        binding.platformVideoGames.layoutManager
        binding.platformVideoGames.adapter = videoGamesVerticalAdapter
    }
}