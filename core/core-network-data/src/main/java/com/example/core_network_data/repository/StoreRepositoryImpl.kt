package com.example.core_network_data.repository

import com.example.core_model.api.store.Store
import com.example.core_network_data.api.StoreApi
import com.example.core_network_domain.repository.StoreRepository
import retrofit2.Response
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val storeApi: StoreApi
): StoreRepository {

    override suspend fun getStore(page: Int): Response<Store> {
        return storeApi.getStores(page = page)
    }
}