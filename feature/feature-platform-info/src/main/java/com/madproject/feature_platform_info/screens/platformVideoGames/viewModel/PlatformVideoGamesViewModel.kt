package com.madproject.feature_platform_info.screens.platformVideoGames.viewModel

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
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class PlatformVideoGamesViewModel(
    private val getVideoGamesUseCase: GetGamesUseCase
):ViewModel() {

    fun getVideoGames(platformId:Int): Flow<PagingData<VideoGameItem>> {
        return Pager(PagingConfig(pageSize = 20)){
            VideoGamesPagingSource(
                getGamesUseCase = getVideoGamesUseCase,
                platforms = platformId.toString()
            )
        }.flow.cachedIn(viewModelScope)
    }

    class Factory @Inject constructor(
        private val getGamesUseCase: GetGamesUseCase
    ):ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PlatformVideoGamesViewModel(
                getVideoGamesUseCase = getGamesUseCase
            ) as T
        }
    }
}