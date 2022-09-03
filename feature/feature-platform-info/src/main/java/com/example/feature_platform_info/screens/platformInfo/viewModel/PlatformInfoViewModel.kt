package com.example.feature_platform_info.screens.platformInfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core_model.api.platform.PlatformInfo
import com.example.core_model.api.videoGame.VideoGameItem
import com.example.core_network_domain.response.Result
import com.example.core_network_domain.pagingSource.VideoGamesPagingSource
import com.example.core_network_domain.useCase.game.GetGamesUseCase
import com.example.core_network_domain.useCase.platform.GetPlatformByIdUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class PlatformInfoViewModel(
    private val getPlatformByIdUseCase: GetPlatformByIdUseCase,
    private val getGamesUseCase: GetGamesUseCase
) : ViewModel() {

    private val _responsePlatforms:MutableStateFlow<Result<PlatformInfo>> =
        MutableStateFlow(Result.Loading())
    val responsePlatforms = _responsePlatforms.asStateFlow()

    fun getPlatforms(id:Int){
        getPlatformByIdUseCase.invoke(id).onEach {
            _responsePlatforms.value = it
        }.launchIn(viewModelScope)
    }

    fun getGames(platformId:Int): Flow<PagingData<VideoGameItem>> {
        return Pager(PagingConfig(pageSize = 20)){
            VideoGamesPagingSource(
                getGamesUseCase = getGamesUseCase,
                platforms = platformId.toString()
            )
        }.flow.cachedIn(viewModelScope)
    }

    class Factory @Inject constructor(
        private val getPlatformByIdUseCase:GetPlatformByIdUseCase,
        private val getGamesUseCase: GetGamesUseCase
    ):ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PlatformInfoViewModel(
                getPlatformByIdUseCase = getPlatformByIdUseCase,
                getGamesUseCase = getGamesUseCase
            ) as T
        }
    }
}