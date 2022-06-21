package com.example.core_network_domain.repository

import com.example.core_model.api.Creator

interface CreatorRepository {

    suspend fun getCreators(
        page:Int
    ):Creator

}