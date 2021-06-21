package com.example.desafio_android_rodrigo_dias_soares.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_android_rodrigo_dias_soares.listener.CharacterListener
import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_character.view.*

class MyViewHolder(itemView: View, private val listener: CharacterListener) : RecyclerView.ViewHolder(itemView) {
    fun bind(character: Character){
        val img = "${character.thumbnail.path}.${character.thumbnail.extension}"
        Picasso.get().load(img).into(itemView.imgThumbnail)
        itemView.txtName.text = character.name

        itemView.setOnClickListener {
            listener.onClick(character)
        }
    }
}