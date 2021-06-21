package com.example.desafio_android_rodrigo_dias_soares.view.view


import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_android_rodrigo_dias_soares.R
import com.example.desafio_android_rodrigo_dias_soares.adapter.CharactersAdapter
import com.example.desafio_android_rodrigo_dias_soares.constant.CHARACTER_DESCRIPTION
import com.example.desafio_android_rodrigo_dias_soares.constant.CHARACTER_ID
import com.example.desafio_android_rodrigo_dias_soares.constant.CHARACTER_NAME
import com.example.desafio_android_rodrigo_dias_soares.constant.CHARACTER_THUMBNAIL
import com.example.desafio_android_rodrigo_dias_soares.listener.CharacterListener
import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.*
import com.example.desafio_android_rodrigo_dias_soares.viewModel.CharactersViewModel
import kotlinx.android.synthetic.main.activity_characters.*


class CharactersActivity : AppCompatActivity() {

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }

    private val adapter: CharactersAdapter by lazy {
        CharactersAdapter()
    }

    private var recyclerState: Parcelable? = null
    private lateinit var mListener: CharacterListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)

        val llm = LinearLayoutManager(this)
        recyclerCharacters.layoutManager = llm
        recyclerCharacters.adapter = adapter
        recyclerCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastVisibleItemPosition = llm.findLastVisibleItemPosition()
                if(lastVisibleItemPosition == adapter.itemCount -1 && !viewModel.isLoading){
                    Log.d("RDS","Loading more...")
                    loadCharacters(viewModel.currentPage + 1)
                }
            }
        })
        loadCharacters(0)

        mListener =object : CharacterListener{
            override fun onClick(character: Character) {

                val img = "${character.thumbnail.path}.${character.thumbnail.extension}"

                val intent = Intent(applicationContext, DescriptionActivity::class.java)
                val bundle = Bundle()

                bundle.putInt(CHARACTER_ID, character.id)
                bundle.putString(CHARACTER_NAME,character.name)
                bundle.putString(CHARACTER_DESCRIPTION,character.description)
                bundle.putString(CHARACTER_THUMBNAIL,img)

                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
        adapter.attachListener(mListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("lmState", recyclerCharacters.layoutManager?.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        recyclerState = savedInstanceState.getParcelable("lmState")
    }

    private fun loadCharacters(page: Int) {
        viewModel.load(page) { characters ->
            characters.forEach { character ->
                adapter.add(character)
            }
            if (recyclerState != null) {
                recyclerCharacters.layoutManager?.onRestoreInstanceState(recyclerState)
                recyclerState = null
            }
        }
    }


}
