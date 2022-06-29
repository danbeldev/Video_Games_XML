package com.example.core_network_domain.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core_model.api.videoGame.ScreenshotItem
import com.example.core_network_domain.response.Result
import com.example.core_network_domain.useCase.game.GetScreenshotsUseCase

class VideoGameScreenshots(
    private val getScreenshotsUseCase: GetScreenshotsUseCase,
    private val gamePk:String
):PagingSource<Int, ScreenshotItem>() {
    override fun getRefreshKey(state: PagingState<Int, ScreenshotItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ScreenshotItem> {
        return try {
            val page = params.key ?: 1
            val nextKey = page.plus(1)
            val prevKey = if (page == 1) null else page -1

            when(val responseScreenshots = getScreenshotsUseCase.invoke(gamePk = gamePk, page = page)){
                is Result.Error -> LoadResult.Error(Exception(responseScreenshots.message))
                is Result.Loading -> LoadResult.Invalid()
                is Result.Success -> LoadResult.Page(
                    data = responseScreenshots.data!!.results,
                    nextKey = nextKey,
                    prevKey = prevKey
                )
            }

        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}