package com.madproject.feature_creator_info.screens.creatorInfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.madproject.core_model.state.ErrorState
import com.madproject.core_network_domain.response.Result
import com.madproject.core_network_domain.pagingSource.VideoGamesPagingSource
import com.madproject.core_network_domain.useCase.creator.GetCreatorByIdUseCase
import com.madproject.core_network_domain.useCase.game.GetGamesUseCase
import com.madproject.feature_creator_info.screens.creatorInfo.action.CreatorInfoAction
import com.madproject.feature_creator_info.screens.creatorInfo.model.CreatorInfoStateSucces
import com.madproject.feature_creator_info.screens.creatorInfo.state.CreatorInfoState
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class CreatorInfoViewModel(
    private val getCreatorByIdUseCase: GetCreatorByIdUseCase,
    private val getGamesUseCase: GetGamesUseCase
):ViewModel() {

    private val _creatorInfoState = MutableStateFlow<CreatorInfoState>(CreatorInfoState.Loading)
    val creatorInfoState = _creatorInfoState.asStateFlow()

    fun renderAction(action:CreatorInfoAction){
        when(action){
            is CreatorInfoAction.GetCreatorInfo -> {
                getCreator(creatorId = action.creatorId)
                getGames(creatorId = action.creatorId)
            }
        }
    }

    private fun getGames(creatorId: Int) {

        val pagerData =  Pager(PagingConfig(pageSize = 20)){
            VideoGamesPagingSource(
                getGamesUseCase = getGamesUseCase,
                creators = creatorId.toString()
            )
        }.flow.cachedIn(viewModelScope)

        _creatorInfoState.value = CreatorInfoState.Succes(
            data = CreatorInfoStateSucces(
                creatorInfo = if (_creatorInfoState.value is CreatorInfoState.Succes)
                    (_creatorInfoState.value as CreatorInfoState.Succes).data.creatorInfo
                else
                    null,
                videoGamesCreatorInfo = pagerData
            )
        )
    }

    private fun getCreator(creatorId:Int){
        getCreatorByIdUseCase.invoke(creatorId).onEach { result ->
            when(result){
                is Result.Error -> {
                    _creatorInfoState.value = CreatorInfoState.Error(
                        error = ErrorState(
                            message = result.message ?: "Error"
                        )
                    )
                }
                is Result.Loading -> {
                    _creatorInfoState.value = CreatorInfoState.Loading
                }
                is Result.Success -> {

                    _creatorInfoState.value = CreatorInfoState.Succes(
                        data = CreatorInfoStateSucces(
                            creatorInfo = result.data,
                            videoGamesCreatorInfo = if (_creatorInfoState.value is CreatorInfoState.Succes)
                                (_creatorInfoState.value as CreatorInfoState.Succes).data.videoGamesCreatorInfo
                            else
                                null
                        )
                    )
                }
            }
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