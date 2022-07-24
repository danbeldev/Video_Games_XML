package com.example.core_network_domain.useCase.store

import com.example.core_model.api.store.StoreInfo
import com.example.core_network_domain.repository.StoreRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetStoreByIdUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    operator fun invoke(id:Int):Single<StoreInfo> = storeRepository.getStoreById(id = id)
}