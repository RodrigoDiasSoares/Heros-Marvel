package com.example.desafio_android_rodrigo_dias_soares.netWork

import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.Data

data class CharacterResponse(
        val code: Int,
        val etag: String,
        val data: Data
)