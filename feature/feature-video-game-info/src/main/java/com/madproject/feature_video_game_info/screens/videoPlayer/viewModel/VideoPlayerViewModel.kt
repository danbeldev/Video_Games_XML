package com.madproject.feature_video_game_info.screens.videoPlayer.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.madproject.core_model.api.videoGame.Trailer
import com.madproject.core_model.api.videoGame.VideoGameInfo
import com.madproject.core_network_domain.response.Result
import com.madproject.core_network_domain.useCase.game.GetGameInfoUseCase
import com.madproject.core_network_domain.useCase.game.GetTrailerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class VideoPlayerViewModel(
    private val getTrailerUseCase: GetTrailerUseCase,
    private val getGameInfoUseCase: GetGameInfoUseCase
):ViewModel() {

    private val _responseTrailer:MutableStateFlow<Result<Trailer>> = MutableStateFlow(Result.Loading())
    val responseTrailer = _responseTrailer.asStateFlow()

    private val _responseVideoGame:MutableStateFlow<Result<VideoGameInfo>> = MutableStateFlow(Result.Loading())
    val responseVideoGame = _responseVideoGame.asStateFlow()

    fun getTrailer(id:Int){
        getTrailerUseCase.invoke(id).onEach {
            _responseTrailer.value = it
        }.launchIn(viewModelScope)
    }

    fun getGameInfo(id: Int){
        getGameInfoUseCase.invoke(id).onEach {
            _responseVideoGame.value = it
        }.launchIn(viewModelScope)
    }

    class Factory @Inject constructor(
        private val getTrailerUseCase: GetTrailerUseCase,
        private val getGameInfoUseCase: GetGameInfoUseCase
    ):ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VideoPlayerViewModel(
                getTrailerUseCase = getTrailerUseCase,
                getGameInfoUseCase = getGameInfoUseCase
            ) as T
        }
    }
}