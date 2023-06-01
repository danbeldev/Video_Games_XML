package com.madproject.core_network_domain.useCase.store

import com.madproject.core_model.api.store.Store
import com.madproject.core_network_domain.repository.StoreRepository
import com.madproject.core_network_domain.response.BaseApiResponse
import com.madproject.core_network_domain.response.Result
import javax.inject.Inject

class GetStoresUseCase @Inject constructor(
    private val storeRepository: StoreRepository
):BaseApiResponse() {

    suspend operator fun invoke(page:Int):Result<Store>{
        return safeApiCall { storeRepository.getStore(page = page) }
    }

}