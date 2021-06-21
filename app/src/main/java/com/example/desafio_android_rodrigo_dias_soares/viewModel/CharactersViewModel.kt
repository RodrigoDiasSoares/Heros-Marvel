package com.example.desafio_android_rodrigo_dias_soares.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.Character
import com.example.desafio_android_rodrigo_dias_soares.netWork.MarvelClient
import kotlinx.coroutines.*
import retrofit2.await


class CharactersViewModel : ViewModel() {

    var isLoading: Boolean = false
        private set
    var currentPage = -1
        private set

    private val characters = mutableListOf<Character>()


    fun load(page: Int, callback:(List<Character>)->Unit){
        GlobalScope.launch(Dispatchers.Main) {
            isLoading = true
            Log.d("NGL", "page:$page | currentPage:$currentPage")

            if (page <= currentPage) {
                callback(characters)
            }else{
                val result = MarvelClient.loadCharacters(page).await()
                characters.addAll(result.data.results)
                callback(result.data.results)
            }
            isLoading = false
            currentPage++
        }
    }

    fun reset() {
        isLoading = false
        currentPage = -1
        characters.clear()
    }
}