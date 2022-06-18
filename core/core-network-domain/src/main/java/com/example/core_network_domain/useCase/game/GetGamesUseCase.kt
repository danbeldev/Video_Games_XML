package com.example.core_network_domain.useCase.game

import com.example.core_model.api.VideoGame
import com.example.core_network_domain.repository.GamesRepository
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {
    suspend operator fun invoke():VideoGame{
        return gamesRepository.getGames().body() ?: VideoGame()
    }
}