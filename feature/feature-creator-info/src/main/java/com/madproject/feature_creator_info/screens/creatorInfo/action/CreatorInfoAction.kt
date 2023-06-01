package com.madproject.feature_creator_info.screens.creatorInfo.action

internal sealed class CreatorInfoAction {
    data class GetCreatorInfo(val creatorId:Int):CreatorInfoAction()
}