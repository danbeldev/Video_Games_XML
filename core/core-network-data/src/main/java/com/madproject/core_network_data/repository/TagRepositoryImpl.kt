package com.madproject.core_network_data.repository

import com.madproject.core_model.api.tag.Tag
import com.madproject.core_model.api.tag.TagInfo
import com.madproject.core_network_data.api.TagApi
import com.madproject.core_network_domain.repository.TagRepository
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagApi: TagApi
):TagRepository {

    override suspend fun getTags(
        page:Int
    ): Response<Tag> {
        return tagApi.getTags(page = page)
    }

    override fun getTagById(id: Int): Single<TagInfo> {
        return tagApi.getTagById(id = id)
    }
}