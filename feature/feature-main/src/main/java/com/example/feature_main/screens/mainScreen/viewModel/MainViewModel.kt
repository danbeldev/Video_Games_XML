package com.example.feature_main.screens.mainScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core_model.api.videoGame.VideoGameItem
import com.example.core_network_domain.source.CreatorsPagingSource
import com.example.core_network_domain.source.PlatformPagingSource
import com.example.core_network_domain.source.VideoGamesPagingSource
import com.example.core_network_domain.useCase.creator.GetCreatorsUseCase
import com.example.core_network_domain.useCase.game.GetGamesUseCase
import com.example.core_network_domain.useCase.platform.GetPlatformUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class MainViewModel(
    private val getGamesUseCase: GetGamesUseCase,
    private val getCreatorsUseCase: GetCreatorsUseCase,
    private val getPlatformPagingSource:GetPlatformUseCase
) : ViewModel() {

    val platforms = Pager(
        PagingConfig(pageSize = 20)
    ){
        PlatformPagingSource(
            getPlatformUseCase = getPlatformPagingSource
        )
    }.flow.cachedIn(viewModelScope)

    val creators = Pager(
        PagingConfig(pageSize = 20)
    ){
        CreatorsPagingSource(
            getCreatorsUseCase = getCreatorsUseCase
        )
    }.flow.cachedIn(viewModelScope)

    val videoGames: Flow<PagingData<VideoGameItem>> = Pager(
        config = PagingConfig(pageSize = 20)
    ){
        VideoGamesPagingSource(
            getGamesUseCase = getGamesUseCase
        )
    }.flow.cachedIn(viewModelScope)

    class Factor @Inject constructor(
        private val getGamesUseCase: GetGamesUseCase,
        private val getCreatorsUseCase: GetCreatorsUseCase,
        private val getPlatformPagingSource: GetPlatformUseCase
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MainViewModel::class.java)
            return MainViewModel(
                getGamesUseCase,
                getCreatorsUseCase,
                getPlatformPagingSource
            ) as T
        }
    }
}