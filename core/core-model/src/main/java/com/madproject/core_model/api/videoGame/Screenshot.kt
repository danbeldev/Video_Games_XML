package com.madproject.core_model.api.videoGame

data class Screenshot(
    val count:Int,
    val next:String,
    val previous:String,
    val results:List<ScreenshotItem>
)

data class ScreenshotItem(
    val image:String,
    val width:Int,
    val height:Int
)