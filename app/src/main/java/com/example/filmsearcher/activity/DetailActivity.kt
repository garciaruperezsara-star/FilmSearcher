package com.example.filmsearcher.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.filmsearcher.R
import com.example.filmsearcher.data.Film
import com.example.filmsearcher.data.FilmService
import com.example.filmsearcher.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var film: Film

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()
        val filmId= intent.getStringExtra("FILM_ID")
        Log.i("api", filmId.toString())
        getFilm(filmId.toString())

    }
    fun getFilm(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = FilmService.getInstance()
                film = service.getFilmById(query, "31b9190c")
                Log.i("api", film.toString())
                CoroutineScope(Dispatchers.Main).launch {
                    initVals()}
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun initVals(){
        binding.titleText.text = film.title
        binding.yearText.text = film.year
        binding.plotText.text = film.plot
        binding.genreText.text = film.genre
        Log.i("api", film.genre.toString())
        binding.timeText.text = (buildString {
            append("Duration: ")
            append(film.duration)
        })
        binding.directorText.text= (buildString {
            append(film.director)
            append(", ")
            append(film.country)
        })
        Picasso.get().load(film.poster).into(binding.posterView)
    }
}