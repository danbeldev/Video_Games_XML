package com.example.feature_main.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.core_model.api.VideoGame
import com.example.core_network_domain.useCase.game.GetGamesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class MainViewModel(
    private val getGamesUseCase: GetGamesUseCase
) : ViewModel() {

    private val _responseVideoGame = MutableStateFlow<VideoGame?>(null)
    val responseVideoGame:StateFlow<VideoGame?> = _responseVideoGame.asStateFlow()

    fun getGames(){
        viewModelScope.launch {
            try {
                _responseVideoGame.value = getGamesUseCase.invoke()
            }catch (e:Exception){
                Log.e("Retrofit_Tag",e.message.toString())
            }
        }
    }

    class Factor @Inject constructor(
        private val getGamesUseCase: GetGamesUseCase
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MainViewModel::class.java)
            return MainViewModel(getGamesUseCase) as T
        }
    }
}