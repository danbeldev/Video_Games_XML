package com.madproject.feature_video_game_info.screens.videoPlayer

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.madproject.core_common.extension.launchWhenStarted
import com.madproject.core_model.api.videoGame.Trailer
import com.madproject.core_model.api.videoGame.VideoGameInfo
import com.madproject.core_network_domain.response.Result
import com.madproject.core_ui.animation.navOptionIsModal
import com.madproject.feature_video_game_info.R
import com.madproject.feature_video_game_info.databinding.FragmentVideoPlayerBinding
import com.madproject.feature_video_game_info.di.GameInfoComponentViewModel
import com.madproject.feature_video_game_info.screens.videoGameInfo.VideoGameInfoFragment
import com.madproject.feature_video_game_info.screens.videoPlayer.adapter.VideoPlayerTrailerAdapter
import com.madproject.feature_video_game_info.screens.videoPlayer.viewModel.VideoPlayerViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import dagger.Lazy
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class VideoPlayerFragment : Fragment(R.layout.fragment_video_player) {

    companion object {
        const val VIDEO_GAME_ID_KEY = "video_game_id"
        const val VIDEO_GAME_TRAILER_ID_KEY = "video_game_trailer_id"
    }

    @Inject
    internal lateinit var videoPlayerViewModelFactory:Lazy<VideoPlayerViewModel.Factory>

    private val viewModel by viewModels<VideoPlayerViewModel> {
        videoPlayerViewModelFactory.get()
    }

    private var videoPlayerTrailerAdapter:VideoPlayerTrailerAdapter? = null

    override fun onAttach(context: Context) {
        ViewModelProvider(this)[GameInfoComponentViewModel::class.java]
            .gameInfoDetailsComponent.injectVideoPlayer(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentVideoPlayerBinding.bind(view)

        val videoGameId = arguments?.getInt(VIDEO_GAME_ID_KEY) ?: 0
        val videoGameTrailerId = arguments?.getInt(VIDEO_GAME_TRAILER_ID_KEY) ?: 0

        viewModel.getTrailer(videoGameId)
        viewModel.responseTrailer.onEach {
            when(val result = it){
                is Result.Error -> Unit
                is Result.Loading -> Unit
                is Result.Success -> trailerSuccess(binding, result.data!!, videoGameTrailerId)
            }
        }.launchWhenStarted(lifecycle, lifecycleScope)

        viewModel.getGameInfo(videoGameId)
        viewModel.responseVideoGame.onEach {
            when(val result = it){
                is Result.Error -> Unit
                is Result.Loading -> Unit
                is Result.Success -> gameInfoSuccess(binding,result.data!!, videoGameId)
            }
        }.launchWhenStarted(lifecycle,lifecycleScope)
    }

    private fun trailerSuccess(
        biding:FragmentVideoPlayerBinding,
        data: Trailer,
        videoGameTrailerId:Int
    ){
        val layoutManagerTrailers = LinearLayoutManager(this.context)

        val exoPlayer = ExoPlayer.Builder(this.context as Context)
            .build()

        exoPlayer.apply {
            playWhenReady = true
            prepare()
            play()
        }

        videoPlayerTrailerAdapter = VideoPlayerTrailerAdapter(
            trailer = data,
            onClickTrailerPreview = { item ->
                exoPlayer.setMediaItem(MediaItem.fromUri(item.data.max))
                biding.trailerTitle.text = item.name
            }
        )

        biding.trailers.adapter = videoPlayerTrailerAdapter
        biding.trailers.layoutManager = layoutManagerTrailers

        data.results.forEach { item ->
            if (item.id == videoGameTrailerId){
                exoPlayer.setMediaItem(MediaItem.fromUri(item.data.max))
                biding.trailerTitle.text = item.name
            }
        }

        biding.videoPlayer.player = exoPlayer
    }

    private fun gameInfoSuccess(
        biding:FragmentVideoPlayerBinding,
        data: VideoGameInfo,
        videoGameId:Int
    ){
        biding.videoGameImage.load(data.background_image)
        biding.videoGameTitle.text = data.name
        biding.videoGameDescription.text = data.description

        biding.videoGameInfo.setOnClickListener {
            findNavController().navigate(
                R.id.action_videoPlayerFragment_to_videoGameInfoFragment,
                bundleOf(VideoGameInfoFragment.VIDEO_GAME_ID_KEY to videoGameId),
                navOptionIsModal()
            )
        }
    }
}