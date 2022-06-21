package com.example.core_network_domain.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core_model.api.CreatorItem
import com.example.core_network_domain.useCase.creator.GetCreatorsUseCase

class CreatorsPagingSource(
    private val getCreatorsUseCase: GetCreatorsUseCase
):PagingSource<Int, CreatorItem>() {
    override fun getRefreshKey(state: PagingState<Int, CreatorItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CreatorItem> {
        return try {

            val page = params.key ?: 0

            val creatorItem = getCreatorsUseCase.invoke(page = page).results

            LoadResult.Page(
                data = creatorItem,
                prevKey = if (page == 1) null else page -1,
                nextKey = page.plus(1)
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}