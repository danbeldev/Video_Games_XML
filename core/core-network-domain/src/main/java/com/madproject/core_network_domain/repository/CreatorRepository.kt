package com.madproject.core_network_domain.repository

import com.madproject.core_model.api.creator.Creator
import com.madproject.core_model.api.creator.CreatorInfo
import retrofit2.Response

interface CreatorRepository {

    suspend fun getCreators(
        page:Int
    ):Response<Creator>

    suspend fun getCreatorById(
        id:Int
    ):Response<CreatorInfo>
}