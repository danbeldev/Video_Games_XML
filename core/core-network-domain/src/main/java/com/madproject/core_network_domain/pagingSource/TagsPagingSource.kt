package com.madproject.core_network_domain.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.madproject.core_model.api.tag.TagItem
import com.madproject.core_network_domain.response.Result
import com.madproject.core_network_domain.useCase.tag.GetTagsUseCase

class TagsPagingSource(
    private val getTagsUseCase: GetTagsUseCase
):PagingSource<Int, TagItem>() {

    override fun getRefreshKey(state: PagingState<Int, TagItem>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TagItem> {
        return try {
            val page = params.key ?: 1
            val nextKey = page.plus(1)
            val prevKey = if (page == 1) null else page -1

            when(val result = getTagsUseCase.invoke(page)){
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