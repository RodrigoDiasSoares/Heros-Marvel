package com.example.desafio_android_rodrigo_dias_soares.netWork

import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.Comic
import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.ComicDataContainer
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicResponse (
    val data: ComicDataContainer
):Serializable