package com.example.feature_video_game_info.screens.videoGameInfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core_model.api.*
import com.example.core_network_domain.response.Result
import com.example.core_network_domain.source.DeveloperTeamPageSource
import com.example.core_network_domain.source.VideoGameScreenshots
import com.example.core_network_domain.useCase.game.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class VideoGameInfoViewModel (
    private val getGameInfoUseCase: GetGameInfoUseCase,
    private val getAchievementsUseCase: GetAchievementsUseCase,
    private val getScreenshotsUseCase: GetScreenshotsUseCase,
    private val getDeveloperTeamUseCase:GetDeveloperTeamUseCase,
    private val getTrailerUseCase: GetTrailerUseCase
) : ViewModel() {

    private val _responseVideoGameInfo:MutableStateFlow<Result<VideoGameInfo>> =
        MutableStateFlow(Result.Loading())
    val responseVideoGameInfo = _responseVideoGameInfo.asStateFlow()

    private val _responseAchievements:MutableStateFlow<Result<Achievement>> =
        MutableStateFlow(Result.Loading())
    val responseAchievements = _responseAchievements.asStateFlow().filterNotNull()

    private val _responseTrailer:MutableStateFlow<Result<Trailer>> =
        MutableStateFlow(Result.Loading())
    val responseTrailer = _responseTrailer.asStateFlow()

    fun getVideoGameInfo(id:Int){
        getGameInfoUseCase.invoke(id).onEach {
            _responseVideoGameInfo.value = it
        }.launchIn(viewModelScope)
    }

    fun getAchievements(id: Int){
        getAchievementsUseCase.invoke(id).onEach {
            _responseAchievements.value = it
        }.launchIn(viewModelScope)
    }

    fun getScreenshots(gamePk:String):Flow<PagingData<ScreenshotItem>>{
        return Pager(PagingConfig(pageSize = 20)){
            VideoGameScreenshots(
                getScreenshotsUseCase = getScreenshotsUseCase,
                gamePk = gamePk
            )
        }.flow.cachedIn(viewModelScope)
    }

    fun getDeveloperTeam(gamePk: Int):Flow<PagingData<CreatorItem>>{
        return Pager(PagingConfig(pageSize = 20)){
            DeveloperTeamPageSource(
                getDeveloperTeamUseCase = getDeveloperTeamUseCase,
                gamePk = gamePk.toString()
            )
        }.flow.cachedIn(viewModelScope)
    }

    fun getTrailer(id: Int){
        getTrailerUseCase.invoke(id).onEach {
            _responseTrailer.value = it
        }.launchIn(viewModelScope)
    }

    class Factory @Inject constructor(
        private val getGameInfoUseCase: GetGameInfoUseCase,
        private val getAchievementsUseCase: GetAchievementsUseCase,
        private val getScreenshotsUseCase:GetScreenshotsUseCase,
        private val getDeveloperTeamUseCase: GetDeveloperTeamUseCase,
        private val getTrailerUseCase: GetTrailerUseCase
    ):ViewModelProvider.Factory{
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == VideoGameInfoViewModel::class.java)
            return VideoGameInfoViewModel(
                getGameInfoUseCase = getGameInfoUseCase,
                getAchievementsUseCase = getAchievementsUseCase,
                getScreenshotsUseCase = getScreenshotsUseCase,
                getDeveloperTeamUseCase = getDeveloperTeamUseCase,
                getTrailerUseCase = getTrailerUseCase
            ) as T
        }
    }
}