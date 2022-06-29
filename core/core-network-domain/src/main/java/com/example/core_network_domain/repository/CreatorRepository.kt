package com.example.core_network_domain.repository

import com.example.core_model.api.creator.Creator
import retrofit2.Response

interface CreatorRepository {

    suspend fun getCreators(
        page:Int
    ):Response<Creator>

}