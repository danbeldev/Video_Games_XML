package com.madproject.feature_main.screens.likesScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.madproject.core_database_domain.userCase.favoriteVideoGame.DeleteFavoriteVideoGamesUseCase
import com.madproject.core_database_domain.userCase.favoriteVideoGame.GetFavoriteVideoGamesUseCase
import com.madproject.core_model.database.room.favoriteVideoGame.FavoriteVideoGameDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class LikesViewModel(
    private val deleteFavoriteVideoGamesUseCase: DeleteFavoriteVideoGamesUseCase,
    private val getFavoriteVideoGamesUseCase: GetFavoriteVideoGamesUseCase
): ViewModel() {

    fun getFavoriteVideoGames(search:String = ""): Flow<PagingData<FavoriteVideoGameDTO>> {
        return Pager(PagingConfig(pageSize = 20)){
            getFavoriteVideoGamesUseCase.invoke(search)
        }.flow.cachedIn(viewModelScope)
    }

    fun deleteFavoriteVideoGames(id:Int){
        viewModelScope.launch {
            deleteFavoriteVideoGamesUseCase.invoke(id)
        }
    }

    class Factory @Inject constructor(
        private val getFavoriteVideoGamesUseCase: GetFavoriteVideoGamesUseCase,
        private val deleteFavoriteVideoGamesUseCase: DeleteFavoriteVideoGamesUseCase
    ):ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LikesViewModel(
                getFavoriteVideoGamesUseCase = getFavoriteVideoGamesUseCase,
                deleteFavoriteVideoGamesUseCase = deleteFavoriteVideoGamesUseCase
            ) as T
        }
    }
}