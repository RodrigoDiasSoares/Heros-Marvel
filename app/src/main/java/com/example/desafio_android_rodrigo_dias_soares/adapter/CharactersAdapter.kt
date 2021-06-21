package com.example.desafio_android_rodrigo_dias_soares.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.Character
import com.example.desafio_android_rodrigo_dias_soares.R
import com.example.desafio_android_rodrigo_dias_soares.listener.CharacterListener
import com.example.desafio_android_rodrigo_dias_soares.viewHolder.MyViewHolder


class CharactersAdapter : RecyclerView.Adapter<MyViewHolder>() {

    private val items = mutableListOf<Character>()
    private lateinit var mListener: CharacterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return MyViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val character = items[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int = items.size

    fun add(character: Character){
        items.add(character)
        notifyItemInserted(items.lastIndex)
    }

    fun attachListener(listener: CharacterListener){
        mListener = listener
    }
}