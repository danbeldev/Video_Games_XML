package com.example.core_network_domain.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core_model.api.store.StoreItem
import com.example.core_network_domain.response.Result
import com.example.core_network_domain.useCase.store.GetStoresUseCase

class StoresPagingSource(
    private val getStoresUseCase: GetStoresUseCase
) : PagingSource<Int, StoreItem>() {
    override fun getRefreshKey(state: PagingState<Int, StoreItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoreItem> {
        return try {
            val page = params.key ?: 1
            val nextKey = page.plus(1)
            val prevKey = if (page == 1) null else page -1

            when(val result = getStoresUseCase.invoke(page)){
                is Result.Error -> LoadResult.Error(Error(result.message))
                is Result.Loading -> LoadResult.Invalid()
                is Result.Success -> LoadResult.Page(
                    data = result.data!!.results,
                    nextKey = nextKey,
                    prevKey = prevKey
                )
            }
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}