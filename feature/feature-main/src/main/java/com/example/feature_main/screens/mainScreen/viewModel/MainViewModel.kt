package com.example.feature_main.screens.mainScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core_database_domain.userCase.favoriteVideoGame.GetFavoriteVideoGamesCountUseCase
import javax.inject.Inject

internal class MainViewModel(
    getFavoriteVideoGamesCountUseCase: GetFavoriteVideoGamesCountUseCase
):ViewModel() {

    val responseFavoriteVideoGames = getFavoriteVideoGamesCountUseCase.invoke()

    class Factory @Inject constructor(
        private val getFavoriteVideoGamesCountUseCase: GetFavoriteVideoGamesCountUseCase
    ):ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(
                getFavoriteVideoGamesCountUseCase = getFavoriteVideoGamesCountUseCase
            ) as T
        }
    }
}