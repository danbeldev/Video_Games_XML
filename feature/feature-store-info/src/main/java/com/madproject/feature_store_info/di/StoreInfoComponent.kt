package com.madproject.feature_store_info.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.madproject.core_common.di.Feature
import com.madproject.core_network_domain.useCase.game.GetGamesUseCase
import com.madproject.core_network_domain.useCase.store.GetStoreByIdUseCase
import com.madproject.feature_store_info.screens.storeInfo.StoreInfoFragment
import dagger.Component
import kotlin.properties.Delegates

@[Feature Component(dependencies = [StoreDeps::class])]
interface StoreInfoComponent {

    fun injectStoreInfoFragment(fragment: StoreInfoFragment)

    @Component.Builder
    interface Builder {

        fun storeDeps(deps: StoreDeps):Builder

        fun build():StoreInfoComponent
    }
}

interface StoreDeps {
    val getStoreByIdUseCase:GetStoreByIdUseCase
    val getVideoGamesUseCase: GetGamesUseCase
}

interface StoreDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps:StoreDeps

    companion object: StoreDepsProvider by StoreDepsStore
}

object StoreDepsStore : StoreDepsProvider {
    override var deps: StoreDeps by Delegates.notNull()
}

internal class StoreCompanyViewModel:ViewModel() {
    val storeDetailComponent = DaggerStoreInfoComponent
        .builder()
        .storeDeps(StoreDepsProvider.deps)
        .build()
}