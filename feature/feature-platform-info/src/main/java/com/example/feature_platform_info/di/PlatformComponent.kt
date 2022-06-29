package com.example.feature_platform_info.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.core_common.di.Feature
import com.example.core_network_domain.useCase.platform.GetPlatformByIdUseCase
import com.example.feature_platform_info.screens.platformInfo.PlatformInfoFragment
import dagger.Component
import kotlin.properties.Delegates

@[Feature Component(dependencies = [PlatformDeps::class])]
interface PlatformComponent {

    fun injectPlatformInfo(fragment: PlatformInfoFragment)

    @Component.Builder
    interface Builder {

        fun platformDeps(platformDeps:PlatformDeps):Builder

        fun build():PlatformComponent
    }
}

interface PlatformDeps {
    val getPlatformByIdUseCase:GetPlatformByIdUseCase
}

interface PlatformDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: PlatformDeps

    companion object : PlatformDepsProvider by PlatformDepsStore
}

object PlatformDepsStore : PlatformDepsProvider {

    override var deps: PlatformDeps by Delegates.notNull()
}

internal class PlatformComponentViewModel : ViewModel() {

    val platformDetailComponent = DaggerPlatformComponent
        .builder()
        .platformDeps(PlatformDepsProvider.deps)
        .build()
}