package com.example.desafio_android_rodrigo_dias_soares.view.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.desafio_android_rodrigo_dias_soares.R
import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.CHARACTER_ID
import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.Character
import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.Comic
import com.example.desafio_android_rodrigo_dias_soares.netWork.ComicResponse
import com.example.desafio_android_rodrigo_dias_soares.viewModel.ComicViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_comic.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable

class ComicActivity : AppCompatActivity() {

    private val viewModel: ComicViewModel by lazy {
        ViewModelProvider(this).get(ComicViewModel::class.java)
    }
    private var comics = ArrayList<Comic>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic)
        val bundle = intent.extras
        val id = bundle?.getInt(CHARACTER_ID)

        if (bundle != null) {
            val result: ArrayList<Comic> = intent.getSerializableExtra("ComicList") as ArrayList<Comic>
            loadComic(result)
            Log.d("Teste------", comics.toString())
            val comic = viewModel.moreExpansiveComic()
            val path = "${comic.value?.thumbnail?.path}.${comic.value?.thumbnail?.extension}"
            Log.d("Teste", path)
            Picasso.get().load(path).into(ivComic)
            tvTitle.text = "testando"
            tvTitle.text = comic.value?.title
            tvComicDescription.text = comic.value?.description
            tvComicPrice.text = viewModel.getPrice().toString()
        }
    }
    fun loadComic(list:ArrayList<Comic>){
        list.forEach {
            viewModel.loadComics(it)
        }
    }
}

