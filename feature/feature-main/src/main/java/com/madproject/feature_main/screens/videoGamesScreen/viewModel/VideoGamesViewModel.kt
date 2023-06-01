package com.madproject.feature_main.screens.videoGamesScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.madproject.core_model.api.videoGame.VideoGameItem
import com.madproject.core_network_domain.pagingSource.VideoGamesPagingSource
import com.madproject.core_network_domain.useCase.game.GetGamesUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class VideoGamesViewModel(
    private val getVideoGamesUseCase: GetGamesUseCase
):ViewModel() {

    private val _responseVideoGames:MutableStateFlow<PagingData<VideoGameItem>> =
        MutableStateFlow(PagingData.empty())
    val responseVideoGames = _responseVideoGames.asStateFlow()

    fun getVideoGames(search:String? = null) {
        Pager(PagingConfig(pageSize = 20)){
            VideoGamesPagingSource(
                getGamesUseCase = getVideoGamesUseCase,
                search = search
            )
        }.flow.cachedIn(viewModelScope).onEach {
            _responseVideoGames.value = it
        }.launchIn(viewModelScope)
    }

    class Factory @Inject constructor(
        private val getVideoGamesUseCase: GetGamesUseCase
    ):ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VideoGamesViewModel(
                getVideoGamesUseCase = getVideoGamesUseCase
            ) as T
        }
    }
}