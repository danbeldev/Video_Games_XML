package com.example.core_network_domain.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core_model.api.videoGame.VideoGameItem
import com.example.core_network_domain.response.Result
import com.example.core_network_domain.useCase.game.GetGamesUseCase

class VideoGamesPagingSource(
    private val getGamesUseCase: GetGamesUseCase,
    private val search:String? = null,
    private val platforms:String? = null,
    private val creators:String? = null,
    private val stores:String? = null
):PagingSource<Int, VideoGameItem>() {
    override fun getRefreshKey(state: PagingState<Int, VideoGameItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoGameItem> {
        return try {

            val page = params.key ?: 1

            when(val videoGameItem = getGamesUseCase.invoke(
                page = page,
                search = search,
                platforms = platforms,
                creators = creators,
                stores = stores
            )){
                is Result.Error -> LoadResult.Error(Exception(videoGameItem.message))
                is Result.Loading -> LoadResult.Invalid()
                is Result.Success -> {
                    LoadResult.Page(
                        data = videoGameItem.data!!.results,
                        prevKey = if (page == 1) null else page -1,
                        nextKey = page.plus(1)
                    )
                }
            }
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}