package com.example.core_network_domain.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core_model.api.creator.CreatorItem
import com.example.core_network_domain.response.Result
import com.example.core_network_domain.useCase.game.GetDeveloperTeamUseCase

class DeveloperTeamPageSource(
    private val getDeveloperTeamUseCase: GetDeveloperTeamUseCase,
    private val gamePk:String
):PagingSource<Int, CreatorItem>() {
    override fun getRefreshKey(state: PagingState<Int, CreatorItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CreatorItem> {
        return try {

            val nextPage = params.key ?: 1

            when(val creators = getDeveloperTeamUseCase.invoke(gamePk,nextPage)){
                is Result.Error -> LoadResult.Error(Exception(creators.message))
                is Result.Loading -> LoadResult.Invalid()
                is Result.Success -> LoadResult.Page(
                    data = creators.data!!.results,
                    prevKey = if (nextPage == 1) null else nextPage -1,
                    nextKey = nextPage.plus(1)
                )
            }

        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

}