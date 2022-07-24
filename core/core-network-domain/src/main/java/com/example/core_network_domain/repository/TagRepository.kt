package com.example.core_network_domain.repository

import com.example.core_model.api.tag.Tag
import com.example.core_model.api.tag.TagInfo
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface TagRepository {

    suspend fun getTags(
        page:Int
    ):Response<Tag>

    fun getTagById(
        id:Int
    ):Single<TagInfo>
}