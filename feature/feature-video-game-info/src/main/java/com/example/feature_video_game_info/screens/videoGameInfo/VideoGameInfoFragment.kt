package com.example.feature_video_game_info.screens.videoGameInfo

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.core_common.extension.launchWhenStarted
import com.example.core_common.extension.parseHtml
import com.example.core_common.naigation.NavCommand
import com.example.core_common.naigation.NavCommands
import com.example.core_common.naigation.Screen
import com.example.core_common.naigation.navigation
import com.example.core_model.api.videoGame.Achievement
import com.example.core_model.api.videoGame.Trailer
import com.example.core_model.api.videoGame.VideoGameInfo
import com.example.core_model.database.favoriteVideoGame.favoriteVideoGame
import com.example.core_network_domain.response.Result
import com.example.core_ui.animation.navOptionIsModal
import com.example.feature_video_game_info.R
import com.example.feature_video_game_info.databinding.FragmentVideoGameInfoBinding
import com.example.feature_video_game_info.di.GameInfoComponentViewModel
import com.example.feature_video_game_info.screens.achievementsScreen.AchievementsFragment
import com.example.feature_video_game_info.screens.videoGameInfo.adapter.AchievementAdapter
import com.example.feature_video_game_info.screens.videoGameInfo.adapter.AdditionsHorizontalAdapter
import com.example.feature_video_game_info.screens.videoGameInfo.adapter.DeveloperTeamAdapter
import com.example.feature_video_game_info.screens.videoGameInfo.adapter.ScreenshotsAdapter
import com.example.feature_video_game_info.screens.videoGameInfo.adapter.SeriesAdapter
import com.example.feature_video_game_info.screens.videoGameInfo.adapter.TrailerAdapter
import com.example.feature_video_game_info.screens.videoGameInfo.viewModel.VideoGameInfoViewModel
import com.example.feature_video_game_info.screens.videoPlayer.VideoPlayerFragment
import dagger.Lazy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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

    private var achievementAdapter:AchievementAdapter? = null

    private var trailerAdapter:TrailerAdapter? = null

    private val screenshotsAdapter by lazy(LazyThreadSafetyMode.NONE){
        ScreenshotsAdapter(context = this.context)
    }

    private val developerTeamAdapter by lazy(LazyThreadSafetyMode.NONE) {
        DeveloperTeamAdapter(
            onClickDeveloperTeam = {
                navigation(
                    NavCommand(
                        NavCommands.DeepLink(
                            url = Uri.parse(Screen.CreatorInfo.arguments(it?.id ?: 0)),
                            isModal = true,
                            isSingleTop = false
                        )
                    )
                )
            },
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

    private val additionsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        AdditionsHorizontalAdapter(
            onClickAdditions = {
                navigation(
                    NavCommand(
                        NavCommands.DeepLink(
                            url = Uri.parse(Screen.VideoGameInfo.arguments(it?.id ?: 0)),
                            isModal = true,
                            isSingleTop = false
                        )
                    )
                )
            }
        )
    }

    private val seriesAdapter by lazy(LazyThreadSafetyMode.NONE){
        SeriesAdapter(
            onClickSeries = {
                navigation(
                    NavCommand(
                        NavCommands.DeepLink(
                            url = Uri.parse(Screen.VideoGameInfo.arguments(it?.id ?: 0)),
                            isModal = true,
                            isSingleTop = false
                        )
                    )
                )
            }
        )
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<GameInfoComponentViewModel>()
            .gameInfoDetailsComponent.injectVideoGameInfo(this)

        super.onAttach(context)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentVideoGameInfoBinding.bind(view)

        val videoGameId = arguments?.getInt(VIDEO_GAME_ID_KEY) ?: 0

        val screenshotsLayoutManager = LinearLayoutManager(this.context)
        screenshotsLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val developerTeamLayoutManager = LinearLayoutManager(this.context)
        developerTeamLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val seriesLayoutManager = LinearLayoutManager(this.context)
        seriesLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val additionsLayoutManager = LinearLayoutManager(this.context)
        additionsLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        viewModel.getVideoGameInfo(videoGameId)
        viewModel.responseVideoGameInfo.onEach {
            when(val result = it){
                is Result.Error -> {
                    binding.videoGameInfo.visibility = View.GONE
                    binding.responseVideoGameInfo.visibility = View.VISIBLE
                    binding.message.text = result.message
                }
                is Result.Loading -> {
                    binding.videoGameInfo.visibility = View.GONE
                    binding.responseVideoGameInfo.visibility = View.VISIBLE
                    binding.message.text = "Loading..."
                }
                is Result.Success -> successVideoGameInfo(binding, result.data!!)
            }
        }.launchWhenStarted(lifecycle,lifecycleScope)

        viewModel.getTrailer(videoGameId)
        viewModel.responseTrailer.onEach {
            when(val result = it){
                is Result.Error -> Unit
                is Result.Loading -> Unit
                is Result.Success -> successTrailer(binding, result.data!!, videoGameId)
            }
        }.launchWhenStarted(lifecycle,lifecycleScope)

        viewModel.getAchievements(videoGameId)
        viewModel.responseAchievements.onEach {
            when(val result = it){
                is Result.Error -> Unit
                is Result.Loading -> Unit
                is Result.Success -> successAchievements(binding, result.data!!)
            }
        }.launchWhenStarted(lifecycle, lifecycleScope)

        lifecycleScope.launchWhenStarted {
            lifecycle.whenStateAtLeast(Lifecycle.State.STARTED){
                viewModel.getScreenshots(
                    gamePk = videoGameId.toString()
                ).collectLatest(screenshotsAdapter::submitData)
            }
        }

        lifecycleScope.launchWhenStarted {
            lifecycle.whenStateAtLeast(Lifecycle.State.STARTED){
                viewModel.getDeveloperTeam(
                    gamePk = videoGameId
                ).collectLatest(developerTeamAdapter::submitData)
            }
        }

        lifecycleScope.launchWhenStarted {
            lifecycle.whenStateAtLeast(Lifecycle.State.STARTED){
                viewModel.getAdditions(
                    gamePk = videoGameId
                ).collectLatest(additionsAdapter::submitData)
            }
        }

        lifecycleScope.launchWhenStarted {
            lifecycle.whenStateAtLeast(Lifecycle.State.STARTED){
                viewModel.getSeries(
                    gamePk = videoGameId
                ).collectLatest(seriesAdapter::submitData)
            }
        }

        binding.screenshots.adapter = screenshotsAdapter
        binding.screenshots.layoutManager = screenshotsLayoutManager

        binding.developerTeam.adapter = developerTeamAdapter
        binding.developerTeam.layoutManager = developerTeamLayoutManager

        binding.series.adapter = seriesAdapter
        binding.series.layoutManager = seriesLayoutManager

        binding.additions.adapter = additionsAdapter
        binding.additions.layoutManager = additionsLayoutManager

        binding.achievementName.setOnClickListener {
            findNavController().navigate(
                R.id.action_videoGameInfoFragment_to_achievementsFragment,
                bundleOf(AchievementsFragment.VIDEO_GAME_ID_KEY to videoGameId),
                navOptionIsModal()
            )
        }
    }


    private fun successVideoGameInfo(
        binding: FragmentVideoGameInfoBinding,
        data: VideoGameInfo
    ){
        binding.videoGameInfo.visibility = View.VISIBLE
        binding.responseVideoGameInfo.visibility = View.GONE

        binding.name.text = data.name
        binding.description.text = data.description.parseHtml()
        binding.backgroundImage.load(data.background_image)

        binding.redditName.text = data.reddit_name
        binding.redditDescription.text = data.reddit_description
        binding.redditUrl.text = data.reddit_url

        binding.metacriticUrl.text = data.metacritic_url

        favoriteVideoGame(
            binding = binding,
            videoGame = data
        )
    }

    private fun successAchievements(
        binding: FragmentVideoGameInfoBinding,
        data: Achievement
    ){
        val achievementLayoutManager = LinearLayoutManager(this.context)
        achievementLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        achievementAdapter = AchievementAdapter(
            achievements = data.results,
            onClickAchievement = {}
        )
        binding.achievements.adapter = achievementAdapter
        binding.achievements.layoutManager = achievementLayoutManager
    }

    private fun successTrailer(
        binding: FragmentVideoGameInfoBinding,
        data: Trailer,
        videoGameId:Int
    ){
        val trailerLayoutManager = LinearLayoutManager(this.context)
        trailerLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        trailerAdapter = TrailerAdapter(
            trailer = data.results,
            onClickTrailerPreview = {
                findNavController().navigate(
                    R.id.action_videoGameInfoFragment_to_videoPlayerFragment,
                    bundleOf(
                        VideoPlayerFragment.VIDEO_GAME_ID_KEY to videoGameId,
                        VideoPlayerFragment.VIDEO_GAME_TRAILER_ID_KEY to it
                    ),
                    navOptionIsModal()
                )
            }
        )

        binding.trailers.adapter = trailerAdapter
        binding.trailers.layoutManager = trailerLayoutManager
    }

    private fun favoriteVideoGame(
        binding: FragmentVideoGameInfoBinding,
        videoGame:VideoGameInfo
    ){
        viewModel.getFavoriteCheckVideoGameById(videoGame.id)
        lifecycleScope.launchWhenStarted {
            lifecycle.whenStateAtLeast(Lifecycle.State.STARTED){
                viewModel.responseFavoriteVideoGameFavoriteCheck.onEach { check ->
                    binding.favoriteImage.setImageResource(
                        if (check){ R.drawable.ic_favorite_red }else { R.drawable.ic_favorite }
                    )
                }.collect()
            }
        }

        binding.favoriteImage.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.responseFavoriteVideoGameFavoriteCheck.onEach { check ->
                    if (check){
//                        binding.favoriteImage.setImageResource(R.drawable.ic_favorite)
//                        viewModel.deleteFavoriteVideoGames(videoGame.id)
                    }else {
                        binding.favoriteImage.setImageResource(R.drawable.ic_favorite_red)
                        viewModel.writeFavoriteVideoGames(
                            item = favoriteVideoGame {
                                id = videoGame.id
                                name = videoGame.name
                                backgroundImage = videoGame.background_image
                            }
                        )
                    }
                }.collect()
            }
        }
    }
}