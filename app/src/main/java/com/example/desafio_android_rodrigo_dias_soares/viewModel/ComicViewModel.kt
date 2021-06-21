package com.example.desafio_android_rodrigo_dias_soares.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.Character
import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.Comic
import com.example.desafio_android_rodrigo_dias_soares.netWork.ComicResponse
import com.example.desafio_android_rodrigo_dias_soares.netWork.MarvelClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await

class ComicViewModel : ViewModel() {
    private var comics = mutableListOf<Comic>()
    private var comic = MutableLiveData<Comic>()
    private var price1 = 0.1
    private var price2 = 0.0

//    fun load(id: Int, callback: MutableList<Comic>){
//        GlobalScope.launch(Dispatchers.Main) {
//            val result = MarvelClient.getComics(id).await()
////            Log.d("Teste",result.toString())
//            comics.addAll(result.data.results)
//
//            moreExpansiveComic(id,comics)
//        }
//    }

//    fun load(id: Int, callback:(List<Comic>)->Unit){
//        GlobalScope.launch(Dispatchers.Main) {
//
//                val result = MarvelClient.getComics(id).await()
//                comics.addAll(result.data.results)
//                callback(result.data.results)
//        }
//    }

    fun moreExpansiveComic():MutableLiveData<Comic>{
        if(comics.size != 0) {
            price1 = comics[0].prices[0].price
           comics.forEach {
               if (price1 > price2) {
                   price2 = price1
                   comic.value = it
                   it.prices.forEach {prices->
                       if (price1 < prices.price) {
                           this.price1 = prices.price
                       }
                   }
               }
           }
           return comic
       }else{
           Log.d("Deu Ruim!!!!!!!!!","Caiu no else")
           return comic

       }
    }

    fun getPrice():Double{
        return price2
    }

    fun loadComics(comic: Comic){
        comics.add(comic)
    }
}
