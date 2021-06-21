package com.example.desafio_android_rodrigo_dias_soares.model.api.entity

import java.io.Serializable

data class Comic(
    var title:String,
    var description:String,
    var prices: List<ComicPrice>,
    var thumbnail: Thumbnail
):Serializable
