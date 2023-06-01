package com.madproject.feature_main.screens.feedScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.madproject.core_model.api.videoGame.VideoGameItem
import com.madproject.core_network_domain.pagingSource.*
import com.madproject.core_network_domain.useCase.creator.GetCreatorsUseCase
import com.madproject.core_network_domain.useCase.game.GetGamesUseCase
import com.madproject.core_network_domain.useCase.platform.GetPlatformUseCase
import com.madproject.core_network_domain.useCase.store.GetStoresUseCase
import com.madproject.core_network_domain.useCase.tag.GetTagsUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class FeedViewModel(
    private val getGamesUseCase: GetGamesUseCase,
    private val getCreatorsUseCase: GetCreatorsUseCase,
    private val getPlatformPagingSource:GetPlatformUseCase,
    private val getStoresUseCase: GetStoresUseCase,
    private val getTagsUseCase: GetTagsUseCase
) : ViewModel() {

    val tags = Pager(
        PagingConfig(pageSize = 20)
    ){
        TagsPagingSource(
            getTagsUseCase = getTagsUseCase
        )
    }.flow.cachedIn(viewModelScope)

    val stores = Pager(
        PagingConfig(pageSize = 20)
    ){
        StoresPagingSource(
            getStoresUseCase = getStoresUseCase
        )
    }.flow.cachedIn(viewModelScope)

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
        private val getPlatformPagingSource: GetPlatformUseCase,
        private val getStoresUseCase: GetStoresUseCase,
        private val getTagsUseCase: GetTagsUseCase
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == FeedViewModel::class.java)
            return FeedViewModel(
                getGamesUseCase,
                getCreatorsUseCase,
                getPlatformPagingSource,
                getStoresUseCase,
                getTagsUseCase
            ) as T
        }
    }
}