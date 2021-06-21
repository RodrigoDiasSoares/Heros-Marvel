package com.example.desafio_android_rodrigo_dias_soares.netWork

import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.CHARACTER_ID
import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.PARAM_CHARACTER_ID
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MarvelApi {
    @GET("characters")
    fun allCharacters(@Query("offset") offset: Int = 0): Call<CharacterResponse>

    @GET("characters/{${PARAM_CHARACTER_ID}}/comics")
    fun comics(@Path(PARAM_CHARACTER_ID) id: Int = 0): Call<ComicResponse>

}