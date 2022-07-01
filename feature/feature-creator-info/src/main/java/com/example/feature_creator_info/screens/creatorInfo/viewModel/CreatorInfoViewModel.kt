package com.example.feature_creator_info.screens.creatorInfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core_model.api.creator.CreatorInfo
import com.example.core_model.api.videoGame.VideoGameItem
import com.example.core_network_domain.response.Result
import com.example.core_network_domain.source.VideoGamesPagingSource
import com.example.core_network_domain.useCase.creator.GetCreatorByIdUseCase
import com.example.core_network_domain.useCase.game.GetGamesUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class CreatorInfoViewModel(
    private val getCreatorByIdUseCase: GetCreatorByIdUseCase,
    private val getGamesUseCase: GetGamesUseCase
):ViewModel() {

    private val _responseCreator:MutableStateFlow<Result<CreatorInfo>> = MutableStateFlow(Result.Loading())
    val responseCreator = _responseCreator.asStateFlow()

    fun getGames(creatorId: Int): Flow<PagingData<VideoGameItem>> {
        return Pager(PagingConfig(pageSize = 20)){
            VideoGamesPagingSource(
                getGamesUseCase = getGamesUseCase,
                creators = creatorId.toString()
            )
        }.flow.cachedIn(viewModelScope)
    }

    fun getCreator(id:Int){
        getCreatorByIdUseCase.invoke(id).onEach {
            _responseCreator.value = it
        }.launchIn(viewModelScope)
    }

    class Factory @Inject constructor(
        private val getCreatorByIdUseCase: GetCreatorByIdUseCase,
        private val getGamesUseCase: GetGamesUseCase
    ):ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CreatorInfoViewModel(
                getCreatorByIdUseCase = getCreatorByIdUseCase,
                getGamesUseCase = getGamesUseCase
            ) as T
        }
    }
}