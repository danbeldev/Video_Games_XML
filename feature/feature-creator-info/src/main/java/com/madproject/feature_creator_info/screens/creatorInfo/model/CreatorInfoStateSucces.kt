package com.madproject.feature_creator_info.screens.creatorInfo.model

import androidx.paging.PagingData
import com.madproject.core_model.api.creator.CreatorInfo
import com.madproject.core_model.api.videoGame.VideoGameItem
import kotlinx.coroutines.flow.Flow

data class CreatorInfoStateSucces(
    val creatorInfo:CreatorInfo? = null,
    val videoGamesCreatorInfo: Flow<PagingData<VideoGameItem>>? = null
)