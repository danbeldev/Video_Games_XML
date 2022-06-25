package com.example.core_model.api

data class Trailer(
    val count:Int,
    val results:List<TrailerItem>
)

data class TrailerItem(
    val id:Int,
    val name:String,
    val preview:String,
    val data:TrailerItemData
)

data class TrailerItemData(
    val max:String
)