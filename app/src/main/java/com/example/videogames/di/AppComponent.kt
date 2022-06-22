package com.example.videogames.di

import com.example.core_network_domain.useCase.creator.GetCreatorsUseCase
import com.example.core_network_domain.useCase.game.GetGameInfoUseCase
import com.example.core_network_domain.useCase.game.GetGamesUseCase
import com.example.feature_main.di.MainDeps
import com.example.feature_video_game_info.di.GameInfoDeps
import dagger.Component
import javax.inject.Scope

@[AppScope Component(modules = [ApiModule::class])]
interface AppComponent: MainDeps, GameInfoDeps {

    override val getGamesUseCase: GetGamesUseCase

    override val getCreatorsUseCase: GetCreatorsUseCase

    override val getGameInfoUseCase: GetGameInfoUseCase
}

@Scope
annotation class AppScope