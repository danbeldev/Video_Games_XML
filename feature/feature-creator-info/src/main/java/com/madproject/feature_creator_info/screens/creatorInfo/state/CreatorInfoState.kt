package com.madproject.feature_creator_info.screens.creatorInfo.state

import com.madproject.core_model.state.ErrorState
import com.madproject.feature_creator_info.screens.creatorInfo.model.CreatorInfoStateSucces

sealed class CreatorInfoState {
    object Loading:CreatorInfoState()
    data class Error(val error:ErrorState):CreatorInfoState()
    data class Succes(val data:CreatorInfoStateSucces):CreatorInfoState()
}