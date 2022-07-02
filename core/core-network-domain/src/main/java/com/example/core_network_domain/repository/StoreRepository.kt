package com.example.core_network_domain.repository

import com.example.core_model.api.store.Store
import retrofit2.Response

interface StoreRepository {

    suspend fun getStore(
        page:Int
    ):Response<Store>
}