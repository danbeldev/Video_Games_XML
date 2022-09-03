package com.example.core_network_domain.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core_model.api.videoGame.VideoGameItem
import com.example.core_network_domain.response.Result
import com.example.core_network_domain.useCase.game.GetSeriesUseCase

class SeriesPagingSource(
    private val getSeriesUseCase: GetSeriesUseCase,
    private val gamePk:String
):PagingSource<Int, VideoGameItem>() {

    override fun getRefreshKey(state: PagingState<Int, VideoGameItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoGameItem> {
        return try {
            val page = params.key ?: 1
            val nextKey = page.plus(1)
            val prevKey = if (page == 1) null else page -1

            when(val response = getSeriesUseCase.invoke(page = page, gamePk = gamePk)){
                is Result.Error -> LoadResult.Error(Error(response.message))
                is Result.Loading -> LoadResult.Invalid()
                is Result.Success -> LoadResult.Page(
                    data = response.data!!.results,
                    nextKey = nextKey,
                    prevKey = prevKey
                )
            }
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

}