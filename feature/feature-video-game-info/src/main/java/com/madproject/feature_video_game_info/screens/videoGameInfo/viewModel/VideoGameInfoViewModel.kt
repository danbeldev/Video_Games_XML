package com.madproject.feature_video_game_info.screens.videoGameInfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.madproject.core_database_domain.userCase.favoriteVideoGame.DeleteFavoriteVideoGamesUseCase
import com.madproject.core_database_domain.userCase.favoriteVideoGame.IfVideoGameInDatabaseUseCase
import com.madproject.core_database_domain.userCase.favoriteVideoGame.AddFavoriteVideoGamesUseCase
import com.madproject.core_model.api.creator.CreatorItem
import com.madproject.core_model.api.videoGame.*
import com.madproject.core_model.database.room.favoriteVideoGame.FavoriteVideoGameDTO
import com.madproject.core_network_domain.response.Result
import com.madproject.core_network_domain.pagingSource.AdditionsPagingSource
import com.madproject.core_network_domain.pagingSource.DeveloperTeamPageSource
import com.madproject.core_network_domain.pagingSource.SeriesPagingSource
import com.madproject.core_network_domain.pagingSource.VideoGameScreenshots
import com.madproject.core_network_domain.useCase.game.*
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
    private val getFavoriteCheckVideoGameByIdUseCase: IfVideoGameInDatabaseUseCase,
    private val deleteFavoriteVideoGamesUseCase: DeleteFavoriteVideoGamesUseCase,
    private val writeFavoriteVideoGamesUseCase: AddFavoriteVideoGamesUseCase
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

    fun updateFavoriteCheckVideoGame(check:Boolean){
        viewModelScope.launch {
            _responseVideoGameInfo.collect { result ->
                if (result is Result.Success){
                    _responseFavoriteVideoGameFavoriteCheck.value = check

                    if (check){
                        writeFavoriteVideoGames(
                            item = FavoriteVideoGameDTO(
                                id = result.data!!.id,
                                name = result.data!!.name,
                                backgroundImage = result.data!!.background_image
                            )
                        )
                    }else{
                        deleteFavoriteVideoGames(result.data!!.id)
                    }
                }
            }
        }
    }

    fun getFavoriteCheckVideoGameById(id:Int){
        viewModelScope.launch {
            val response = getFavoriteCheckVideoGameByIdUseCase.invoke(id)
            _responseFavoriteVideoGameFavoriteCheck.value = response
        }
    }

    private fun deleteFavoriteVideoGames(id:Int){
        viewModelScope.launch { deleteFavoriteVideoGamesUseCase.invoke(id) }
    }

    private fun writeFavoriteVideoGames(item: FavoriteVideoGameDTO){
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
        private val getFavoriteCheckVideoGameByIdUseCase: IfVideoGameInDatabaseUseCase,
        private val deleteFavoriteVideoGamesUseCase: DeleteFavoriteVideoGamesUseCase,
        private val writeFavoriteVideoGamesUseCase: AddFavoriteVideoGamesUseCase
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