package com.example.desafio_android_rodrigo_dias_soares.listener

import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.Character

interface CharacterListener {
    fun onClick(character: Character)
}