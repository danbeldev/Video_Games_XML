package com.example.core_model.database.favoriteVideoGame

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

fun favoriteVideoGame(block:FavoriteVideoGame.() -> Unit):FavoriteVideoGame =
    FavoriteVideoGame().apply(block)

class FavoriteVideoGame : RealmObject {
    @PrimaryKey var id:Int = 0
    var name:String = ""
    var description:String = ""
    var backgroundImage:String = ""
}

data class FavoriteVideoGameDTO(
    val id:Int,
    val name:String,
    val description:String,
    val backgroundImage:String
)

fun FavoriteVideoGame.mapToDTO():FavoriteVideoGameDTO{
    return FavoriteVideoGameDTO(
        id = this.id,
        name = this.name,
        description = this.description,
        backgroundImage = this.backgroundImage,
    )
}