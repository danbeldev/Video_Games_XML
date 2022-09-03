package com.example.core_network_domain.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core_model.api.creator.CreatorItem
import com.example.core_network_domain.response.Result
import com.example.core_network_domain.useCase.creator.GetCreatorsUseCase

class CreatorsPagingSource(
    private val getCreatorsUseCase: GetCreatorsUseCase
):PagingSource<Int, CreatorItem>() {
    override fun getRefreshKey(state: PagingState<Int, CreatorItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CreatorItem> {
        return try {

            val page = params.key ?: 1

            when(val creatorItem = getCreatorsUseCase.invoke(page = page)){
                is Result.Error -> LoadResult.Error(Error(creatorItem.message))
                is Result.Loading -> LoadResult.Invalid()
                is Result.Success -> LoadResult.Page(
                    data = creatorItem.data!!.results,
                    prevKey = if (page == 1) null else page -1,
                    nextKey = page.plus(1)
                )
            }
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}