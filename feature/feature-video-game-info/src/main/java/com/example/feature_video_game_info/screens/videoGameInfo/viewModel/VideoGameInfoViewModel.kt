package com.example.feature_video_game_info.screens.videoGameInfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core_database_domain.userCase.favoriteVideoGame.DeleteFavoriteVideoGamesUseCase
import com.example.core_database_domain.userCase.favoriteVideoGame.GetCheckVideoGameByIdUseCase
import com.example.core_database_domain.userCase.favoriteVideoGame.WriteFavoriteVideoGamesUseCase
import com.example.core_model.api.creator.CreatorItem
import com.example.core_model.api.videoGame.*
import com.example.core_model.database.favoriteVideoGame.FavoriteVideoGame
import com.example.core_network_domain.response.Result
import com.example.core_network_domain.source.AdditionsPagingSource
import com.example.core_network_domain.source.DeveloperTeamPageSource
import com.example.core_network_domain.source.SeriesPagingSource
import com.example.core_network_domain.source.VideoGameScreenshots
import com.example.core_network_domain.useCase.game.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class VideoGameInfoViewModel (
    private val getGameInfoUseCase: GetGameInfoUseCase,
    private val getAchievementsUseCase: GetAchievementsUseCase,
    private val getScreenshotsUseCase: GetScreenshotsUseCase,
    private val getDeveloperTeamUseCase:GetDeveloperTeamUseCase,
    private val getTrailerUseCase: GetTrailerUseCase,
    private val getSeriesUseCase: GetSeriesUseCase,
    private val getAdditionsUseCase:GetAdditionsUseCase,
    private val getFavoriteCheckVideoGameByIdUseCase: GetCheckVideoGameByIdUseCase,
    private val deleteFavoriteVideoGamesUseCase: DeleteFavoriteVideoGamesUseCase,
    private val writeFavoriteVideoGamesUseCase: WriteFavoriteVideoGamesUseCase
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

    private val _responseFavoriteVideoGameFavoriteCheck:MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val responseFavoriteVideoGameFavoriteCheck = _responseFavoriteVideoGameFavoriteCheck.asStateFlow().filterNotNull()

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

    fun getAdditions(gamePk: Int):Flow<PagingData<VideoGameItem>>{
        return Pager(PagingConfig(pageSize = 20)){
            AdditionsPagingSource(
                getAdditionsUseCase = getAdditionsUseCase,
                gamePk = gamePk.toString()
            )
        }.flow.cachedIn(viewModelScope)
    }

    fun getTrailer(id: Int){
        getTrailerUseCase.invoke(id).onEach {
            _responseTrailer.value = it
        }.launchIn(viewModelScope)
    }

    fun getSeries(gamePk:Int): Flow<PagingData<VideoGameItem>> {
        return Pager(PagingConfig(pageSize = 20)){
            SeriesPagingSource(
                getSeriesUseCase = getSeriesUseCase,
                gamePk = gamePk.toString()
            )
        }.flow.cachedIn(viewModelScope)
    }

    fun getFavoriteCheckVideoGameById(id:Int){
        val response = getFavoriteCheckVideoGameByIdUseCase.invoke(id)
        _responseFavoriteVideoGameFavoriteCheck.value = response
    }

    fun deleteFavoriteVideoGames(id:Int){
        viewModelScope.launch { deleteFavoriteVideoGamesUseCase.invoke(id) }
    }

    fun writeFavoriteVideoGames(item: FavoriteVideoGame){
        viewModelScope.launch { writeFavoriteVideoGamesUseCase.invoke(item) }
    }

    class Factory @Inject constructor(
        private val getGameInfoUseCase: GetGameInfoUseCase,
        private val getAchievementsUseCase: GetAchievementsUseCase,
        private val getScreenshotsUseCase:GetScreenshotsUseCase,
        private val getDeveloperTeamUseCase: GetDeveloperTeamUseCase,
        private val getTrailerUseCase: GetTrailerUseCase,
        private val getSeriesUseCase: GetSeriesUseCase,
        private val getAdditionsUseCase:GetAdditionsUseCase,
        private val getFavoriteCheckVideoGameByIdUseCase: GetCheckVideoGameByIdUseCase,
        private val deleteFavoriteVideoGamesUseCase: DeleteFavoriteVideoGamesUseCase,
        private val writeFavoriteVideoGamesUseCase: WriteFavoriteVideoGamesUseCase
    ):ViewModelProvider.Factory{
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == VideoGameInfoViewModel::class.java)
            return VideoGameInfoViewModel(
                getGameInfoUseCase = getGameInfoUseCase,
                getAchievementsUseCase = getAchievementsUseCase,
                getScreenshotsUseCase = getScreenshotsUseCase,
                getDeveloperTeamUseCase = getDeveloperTeamUseCase,
                getTrailerUseCase = getTrailerUseCase,
                getSeriesUseCase = getSeriesUseCase,
                getAdditionsUseCase = getAdditionsUseCase,
                getFavoriteCheckVideoGameByIdUseCase = getFavoriteCheckVideoGameByIdUseCase,
                deleteFavoriteVideoGamesUseCase = deleteFavoriteVideoGamesUseCase,
                writeFavoriteVideoGamesUseCase = writeFavoriteVideoGamesUseCase
            ) as T
        }
    }
}