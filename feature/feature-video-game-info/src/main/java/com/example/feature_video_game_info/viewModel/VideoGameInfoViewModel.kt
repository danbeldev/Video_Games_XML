package com.example.feature_video_game_info.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.core_model.api.VideoGameInfo
import com.example.core_network_domain.response.Result
import com.example.core_network_domain.useCase.game.GetGameInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class VideoGameInfoViewModel (
    private val getGameInfoUseCase: GetGameInfoUseCase
) : ViewModel() {

    private val _responseVideoGameInfo:MutableStateFlow<Result<VideoGameInfo>> =
        MutableStateFlow(Result.Loading())
    val responseVideoGameInfo = _responseVideoGameInfo.asStateFlow()

    fun getVideoGameInfo(id:Int){
        getGameInfoUseCase.invoke(id).onEach {
            _responseVideoGameInfo.value = it
        }.launchIn(viewModelScope)
    }

    class Factory @Inject constructor(
        private val getGameInfoUseCase: GetGameInfoUseCase
    ):ViewModelProvider.Factory{
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == VideoGameInfoViewModel::class.java)
            return VideoGameInfoViewModel(
                getGameInfoUseCase = getGameInfoUseCase
            ) as T
        }
    }
}