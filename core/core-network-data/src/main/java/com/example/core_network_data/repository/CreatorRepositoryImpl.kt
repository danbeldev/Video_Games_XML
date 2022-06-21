package com.example.core_network_data.repository

import com.example.core_model.api.Creator
import com.example.core_network_data.api.CreatorApi
import com.example.core_network_domain.repository.CreatorRepository
import javax.inject.Inject

class CreatorRepositoryImpl @Inject constructor(
    private val creatorApi: CreatorApi
): CreatorRepository {

    override suspend fun getCreators(page: Int): Creator {
        return creatorApi.getCreators()
    }
}