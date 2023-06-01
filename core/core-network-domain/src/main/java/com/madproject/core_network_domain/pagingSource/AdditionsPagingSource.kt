package com.madproject.core_network_domain.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.madproject.core_model.api.videoGame.VideoGameItem
import com.madproject.core_network_domain.response.Result
import com.madproject.core_network_domain.useCase.game.GetAdditionsUseCase

class AdditionsPagingSource(
    private val getAdditionsUseCase: GetAdditionsUseCase,
    private val gamePk:String
):PagingSource<Int, VideoGameItem>() {
    override fun getRefreshKey(state: PagingState<Int, VideoGameItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoGameItem> {
        return try {
            val page = params.key ?: 1

            when(val result = getAdditionsUseCase.invoke(gamePk, page)){
                is Result.Error -> LoadResult.Error(Error(result.message))
                is Result.Loading -> LoadResult.Invalid()
                is Result.Success -> LoadResult.Page(
                    data = result.data!!.results,
                    prevKey = if (page == 1) null else page -1,
                    nextKey = page.plus(1)
                )
            }
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

}