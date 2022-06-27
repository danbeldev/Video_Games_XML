package com.example.feature_main.screens.videoGamesScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core_model.api.VideoGameItem
import com.example.core_network_domain.source.VideoGamesPagingSource
import com.example.core_network_domain.useCase.game.GetGamesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class VideoGamesViewModel(
    private val getVideoGamesUseCase: GetGamesUseCase
):ViewModel() {

    fun getVideoGames(): Flow<PagingData<VideoGameItem>> {
        return Pager(PagingConfig(pageSize = 20)){
            VideoGamesPagingSource(
                getGamesUseCase = getVideoGamesUseCase
            )
        }.flow.cachedIn(viewModelScope)
    }

    class Factory @Inject constructor(
        private val getVideoGamesUseCase: GetGamesUseCase
    ):ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return VideoGamesViewModel(
                getVideoGamesUseCase = getVideoGamesUseCase
            ) as T
        }
    }
}