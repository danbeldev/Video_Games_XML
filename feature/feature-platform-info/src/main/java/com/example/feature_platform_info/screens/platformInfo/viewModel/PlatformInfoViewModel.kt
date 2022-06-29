package com.example.feature_platform_info.screens.platformInfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.core_model.api.platform.PlatformInfo
import com.example.core_network_domain.response.Result
import com.example.core_network_domain.useCase.platform.GetPlatformByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class PlatformInfoViewModel(
    private val getPlatformByIdUseCase: GetPlatformByIdUseCase
) : ViewModel() {

    private val _responsePlatforms:MutableStateFlow<Result<PlatformInfo>> =
        MutableStateFlow(Result.Loading())
    val responsePlatforms = _responsePlatforms.asStateFlow()

    fun getPlatforms(id:Int){
        getPlatformByIdUseCase.invoke(id).onEach {
            _responsePlatforms.value = it
        }.launchIn(viewModelScope)
    }

    class Factory @Inject constructor(
        private val getPlatformByIdUseCase:GetPlatformByIdUseCase
    ):ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PlatformInfoViewModel(
                getPlatformByIdUseCase = getPlatformByIdUseCase
            ) as T
        }
    }
}