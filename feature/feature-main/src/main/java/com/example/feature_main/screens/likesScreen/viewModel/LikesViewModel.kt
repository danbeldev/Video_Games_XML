package com.example.feature_main.screens.likesScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.core_database_domain.userCase.favoriteVideoGame.DeleteFavoriteVideoGamesUseCase
import com.example.core_database_domain.userCase.favoriteVideoGame.GetFavoriteVideoGamesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class LikesViewModel(
    private val deleteFavoriteVideoGamesUseCase: DeleteFavoriteVideoGamesUseCase,
    getFavoriteVideoGamesUseCase: GetFavoriteVideoGamesUseCase
): ViewModel() {

    val getFavoriteVideoGames = getFavoriteVideoGamesUseCase.invoke()

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