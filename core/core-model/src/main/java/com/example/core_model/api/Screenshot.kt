package com.example.core_model.api

data class Screenshot(
    val count:Int,
    val next:String,
    val previous:String,
    val results:List<ScreenshotItem>
)

data class ScreenshotItem(
    val image:String,
    val hidden:Boolean
)