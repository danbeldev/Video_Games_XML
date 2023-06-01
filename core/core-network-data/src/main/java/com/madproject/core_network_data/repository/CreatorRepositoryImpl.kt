package com.madproject.core_network_data.repository

import com.madproject.core_model.api.creator.Creator
import com.madproject.core_model.api.creator.CreatorInfo
import com.madproject.core_network_data.api.CreatorApi
import com.madproject.core_network_domain.repository.CreatorRepository
import retrofit2.Response
import javax.inject.Inject

class CreatorRepositoryImpl @Inject constructor(
    private val creatorApi: CreatorApi
): CreatorRepository {

    override suspend fun getCreators(page: Int): Response<Creator> {
        return creatorApi.getCreators()
    }

    override suspend fun getCreatorById(id: Int): Response<CreatorInfo> {
        return creatorApi.getCreatorById(id = id)
    }
}