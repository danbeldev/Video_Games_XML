package com.example.core_network_domain.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core_model.api.VideoGameItem
import com.example.core_network_domain.useCase.game.GetGamesUseCase

class VideoGamesPagingSource(
    private val getGamesUseCase: GetGamesUseCase
):PagingSource<Int, VideoGameItem>() {
    override fun getRefreshKey(state: PagingState<Int, VideoGameItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoGameItem> {
        return try {

            val page = params.key ?: 0

            val videoGameItem = getGamesUseCase.invoke(
                page = page
            ).results

            LoadResult.Page(
                data = videoGameItem,
                prevKey = if (page == 1) null else page -1,
                nextKey = page.plus(1)
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}