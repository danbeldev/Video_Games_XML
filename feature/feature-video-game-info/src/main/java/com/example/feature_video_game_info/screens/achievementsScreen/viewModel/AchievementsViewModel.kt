package com.example.feature_video_game_info.screens.achievementsScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.core_model.api.videoGame.Achievement
import com.example.core_network_domain.response.Result
import com.example.core_network_domain.useCase.game.GetAchievementsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class AchievementsViewModel(
    private val getAchievementsUseCase: GetAchievementsUseCase
):ViewModel() {

    private val _responseAchievements:MutableStateFlow<Result<Achievement>> =
        MutableStateFlow(Result.Loading())
    val responseAchievements = _responseAchievements.asStateFlow()

    fun getAchievements(id:Int){
        getAchievementsUseCase.invoke(id).onEach {
            _responseAchievements.value = it
        }.launchIn(viewModelScope)
    }

    class Factory @Inject constructor(
        private val getAchievementsUseCase: GetAchievementsUseCase
    ):ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AchievementsViewModel(
                getAchievementsUseCase = getAchievementsUseCase
            ) as T
        }
    }
}