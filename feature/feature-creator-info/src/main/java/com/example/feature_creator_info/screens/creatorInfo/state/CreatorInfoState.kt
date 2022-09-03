package com.example.feature_creator_info.screens.creatorInfo.state

import com.example.core_model.state.ErrorState
import com.example.feature_creator_info.screens.creatorInfo.model.CreatorInfoStateSucces

sealed class CreatorInfoState {
    object Loading:CreatorInfoState()
    data class Error(val error:ErrorState):CreatorInfoState()
    data class Succes(val data:CreatorInfoStateSucces):CreatorInfoState()
}