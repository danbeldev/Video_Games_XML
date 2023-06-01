package com.madproject.feature_video_game_info.screens.videoGameInfo

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
import com.madproject.core_common.extension.launchWhenStarted
import com.madproject.core_common.extension.launchWhenStartedPagingData
import com.madproject.core_common.extension.parseHtml
import com.madproject.core_common.naigation.NavCommand
import com.madproject.core_common.naigation.NavCommands
import com.madproject.core_common.naigation.Screen
import com.madproject.core_common.naigation.navigation
import com.madproject.core_model.api.videoGame.Achievement
import com.madproject.core_model.api.videoGame.Trailer
import com.madproject.core_model.api.videoGame.VideoGameInfo
import com.madproject.core_network_domain.response.Result
import com.madproject.core_ui.animation.navOptionIsModal
import com.madproject.feature_video_game_info.R
import com.madproject.feature_video_game_info.databinding.FragmentVideoGameInfoBinding
import com.madproject.feature_video_game_info.di.GameInfoComponentViewModel
import com.madproject.feature_video_game_info.screens.achievementsScreen.AchievementsFragment
import com.madproject.feature_video_game_info.screens.qrCodeVideoGameScreen.QrCodeVideoGameFragment
import com.madproject.feature_video_game_info.screens.videoGameInfo.adapter.AchievementAdapter
import com.madproject.feature_video_game_info.screens.videoGameInfo.adapter.AdditionsHorizontalAdapter
import com.madproject.feature_video_game_info.screens.videoGameInfo.adapter.DeveloperTeamAdapter
import com.madproject.feature_video_game_info.screens.videoGameInfo.adapter.ScreenshotsAdapter
import com.madproject.feature_video_game_info.screens.videoGameInfo.adapter.SeriesAdapter
import com.madproject.feature_video_game_info.screens.videoGameInfo.adapter.TrailerAdapter
import com.madproject.feature_video_game_info.screens.videoGameInfo.viewModel.VideoGameInfoViewModel
import com.madproject.feature_video_game_info.screens.videoPlayer.VideoPlayerFragment
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

    private var achievementAdapter:AchievementAdapter? = null

    private var trailerAdapter:TrailerAdapter? = null

    private val screenshotsAdapter by lazy(LazyThreadSafetyMode.NONE){
        ScreenshotsAdapter(context = this.context)
    }

    private var videoGame = VideoGameInfo()

    private var favoriteVideoGame = false

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

        launchWhenStarted {
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
                    is Result.Success -> {
                        videoGame = result.data!!
                        successVideoGameInfo(binding)
                    }
                }
            }
        }

        launchWhenStarted {
            viewModel.getTrailer(videoGameId)

            viewModel.responseTrailer.onEach {
                when(val result = it){
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> successTrailer(binding, result.data!!, videoGameId)
                }
            }
        }

        launchWhenStarted {
            viewModel.getAchievements(videoGameId)

            viewModel.responseAchievements.onEach {
                when(val result = it){
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> successAchievements(binding, result.data!!)
                }
            }
        }

        launchWhenStartedPagingData(
            adapter = screenshotsAdapter
        ){
            viewModel.getScreenshots(
                gamePk = videoGameId.toString()
            )
        }

        launchWhenStartedPagingData(
            adapter = developerTeamAdapter
        ){
            viewModel.getDeveloperTeam(
                gamePk = videoGameId
            )
        }

        launchWhenStartedPagingData(
            adapter = additionsAdapter
        ){
            viewModel.getAdditions(
                gamePk = videoGameId
            )
        }

        launchWhenStartedPagingData(
            adapter = seriesAdapter
        ){
            viewModel.getSeries(
                gamePk = videoGameId
            )
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

        binding.qrCodeImage.setOnClickListener {
            findNavController().navigate(
                R.id.action_videoGameInfoFragment_to_qrCodeVideoGameFragment,
                bundleOf(QrCodeVideoGameFragment.VIDEO_GAME_ID_KEY to videoGameId),
                navOptionIsModal()
            )
        }
    }


    private fun successVideoGameInfo(
        binding: FragmentVideoGameInfoBinding
    ){
        binding.videoGameInfo.visibility = View.VISIBLE
        binding.responseVideoGameInfo.visibility = View.GONE

        binding.name.text = videoGame.name
        binding.description.text = videoGame.description.parseHtml()
        binding.backgroundImage.load(videoGame.background_image)

        binding.redditName.text = videoGame.reddit_name
        binding.redditDescription.text = videoGame.reddit_description
        binding.redditUrl.text = videoGame.reddit_url

        binding.metacriticUrl.text = videoGame.metacritic_url

        favoriteVideoGame(
            binding = binding
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
        binding: FragmentVideoGameInfoBinding
    ){
        viewModel.getFavoriteCheckVideoGameById(videoGame.id)
        launchWhenStarted {
            viewModel.responseFavoriteVideoGameFavoriteCheck.onEach { check ->
                favoriteVideoGame = check

                binding.favoriteImage.setImageResource(
                    if (favoriteVideoGame) R.drawable.ic_favorite_red else
                        R.drawable.ic_favorite
                )
            }
        }

        binding.favoriteImage.setOnClickListener {
            viewModel.updateFavoriteCheckVideoGame(
                check = !favoriteVideoGame
            )
        }
    }
}