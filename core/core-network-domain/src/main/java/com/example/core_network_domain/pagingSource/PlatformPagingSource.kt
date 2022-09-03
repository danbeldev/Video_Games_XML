package com.example.core_network_domain.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core_model.api.platform.PlatformItem
import com.example.core_network_domain.response.Result
import com.example.core_network_domain.useCase.platform.GetPlatformUseCase

class PlatformPagingSource (
    private val getPlatformUseCase: GetPlatformUseCase
):PagingSource<Int, PlatformItem>() {

    override fun getRefreshKey(state: PagingState<Int, PlatformItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlatformItem> {
        return try {
            val page = params.key ?: 1

            when(val response = getPlatformUseCase.invoke(
                page = page
            )){
                is Result.Error -> LoadResult.Error(Error(response.message))
                is Result.Loading -> LoadResult.Invalid()
                is Result.Success -> LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (page == 1) null else page -1,
                    nextKey = page.plus(1)
                )
            }
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

}