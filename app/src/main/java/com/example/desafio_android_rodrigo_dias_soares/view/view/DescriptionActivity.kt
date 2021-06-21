package com.example.desafio_android_rodrigo_dias_soares.view.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafio_android_rodrigo_dias_soares.R
import com.example.desafio_android_rodrigo_dias_soares.constant.CHARACTER_DESCRIPTION
import com.example.desafio_android_rodrigo_dias_soares.constant.CHARACTER_ID
import com.example.desafio_android_rodrigo_dias_soares.constant.CHARACTER_NAME
import com.example.desafio_android_rodrigo_dias_soares.constant.CHARACTER_THUMBNAIL
import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.*
import com.example.desafio_android_rodrigo_dias_soares.netWork.MarvelClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_description.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await


class DescriptionActivity : AppCompatActivity() {

    private var comics = ArrayList<Comic>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        val bundle = intent.extras
        tvNameDescription.text = bundle?.getString(CHARACTER_NAME)
        tvCharacterDescription.text = bundle?.getString(CHARACTER_DESCRIPTION)
        Picasso.get().load(bundle?.getString(CHARACTER_THUMBNAIL)).into(iVCharaterDescription)

        btnMoreExpensive.setOnClickListener {
            val intent = Intent(applicationContext, ComicActivity::class.java)
            val bundleComic = Bundle()

            GlobalScope.launch(Dispatchers.Main) {
                val result = bundle?.let { it1 -> MarvelClient.getComics(it1.getInt(CHARACTER_ID)).await() }
                if (result != null) {
                    comics.addAll(result.data.results)
                    bundleComic.putInt(CHARACTER_ID, bundle.getInt(CHARACTER_ID))
                    bundleComic.putSerializable("ComicList",result)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }
        }
    }
}


