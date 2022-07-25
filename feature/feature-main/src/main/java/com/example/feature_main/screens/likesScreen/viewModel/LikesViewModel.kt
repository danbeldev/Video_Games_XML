package com.example.feature_main.screens.likesScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core_database_domain.userCase.favoriteVideoGame.GetFavoriteVideoGamesUseCase
import javax.inject.Inject

internal class LikesViewModel(
    getFavoriteVideoGamesUseCase: GetFavoriteVideoGamesUseCase
): ViewModel() {

    val getFavoriteVideoGames = getFavoriteVideoGamesUseCase.invoke()

    class Factory @Inject constructor(
        private val getFavoriteVideoGamesUseCase: GetFavoriteVideoGamesUseCase
    ):ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LikesViewModel(
                getFavoriteVideoGamesUseCase = getFavoriteVideoGamesUseCase
            ) as T
        }
    }
}