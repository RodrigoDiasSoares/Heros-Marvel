package com.example.desafio_android_rodrigo_dias_soares.model.api.entity

data class Data(
        val offset: Int,
        val limit: Int,
        val total: Int,
        val count: Int,
        val results: List<Character>
)