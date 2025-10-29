package com.example.filmsearcher.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsearcher.data.Film
import com.example.filmsearcher.databinding.FilmItemBinding
import com.squareup.picasso.Picasso

class FilmAdapter(
    var items: List<Film>,
    val onClickListener: (String) -> Unit
) : RecyclerView.Adapter<FilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val binding= FilmItemBinding.inflate(layoutInflater,parent,false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val item = items[position]
        holder.render(item,onClickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateItems(items:List<Film>){
        this.items = items
        notifyDataSetChanged()
    }
}
class FilmViewHolder(val binding: FilmItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(film: Film, onClickListener: (String) -> Unit) {

        binding.filmImageView.setOnClickListener { onClickListener(film.id) }
        binding.filmTextView.text= film.title
        binding.yearTextView.text= film.year
        Picasso.get().load(film.poster).into(binding.filmImageView)
    }

}