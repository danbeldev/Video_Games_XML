package com.example.feature_video_game_info.screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.core_common.extension.launchWhenStarted
import com.example.core_common.extension.parseHtml
import com.example.core_model.api.Achievement
import com.example.core_model.api.Trailer
import com.example.core_model.api.VideoGameInfo
import com.example.core_network_domain.response.Result
import com.example.feature_video_game_info.R
import com.example.feature_video_game_info.adapter.AchievementAdapter
import com.example.feature_video_game_info.adapter.DeveloperTeamAdapter
import com.example.feature_video_game_info.adapter.ScreenshotsAdapter
import com.example.feature_video_game_info.adapter.TrailerAdapter
import com.example.feature_video_game_info.databinding.FragmentVideoGameInfoBinding
import com.example.feature_video_game_info.di.GameInfoComponentViewModel
import com.example.feature_video_game_info.viewModel.VideoGameInfoViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
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

    private val developerTeamAdapter by lazy(LazyThreadSafetyMode.NONE) {
        DeveloperTeamAdapter()
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<GameInfoComponentViewModel>()
            .gameInfoDetailsComponent.inject(this)

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
                is Result.Success -> successTrailer(binding, result.data!!)
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

        binding.screenshots.adapter = screenshotsAdapter
        binding.screenshots.layoutManager = screenshotsLayoutManager

        binding.developerTeam.adapter = developerTeamAdapter
        binding.developerTeam.layoutManager = developerTeamLayoutManager
    }


    private fun successVideoGameInfo(
        binding: FragmentVideoGameInfoBinding,
        data:VideoGameInfo
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
    }

    private fun successAchievements(
        binding: FragmentVideoGameInfoBinding,
        data: Achievement
    ){
        val achievementLayoutManager = LinearLayoutManager(this.context)
        achievementLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        achievementAdapter = AchievementAdapter(data.results)
        binding.achievements.adapter = achievementAdapter
        binding.achievements.layoutManager = achievementLayoutManager
    }

    private fun successTrailer(
        binding: FragmentVideoGameInfoBinding,
        data:Trailer
    ){
        val trailerLayoutManager = LinearLayoutManager(this.context)
        trailerLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        trailerAdapter = TrailerAdapter(
            context = this.requireContext(),
            trailer = data.results
        )

        binding.trailers.adapter = trailerAdapter
        binding.trailers.layoutManager = trailerLayoutManager
    }
}