package com.example.filmsearcher.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Adapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.filmsearcher.activity.DetailActivity
import com.example.filmsearcher.R
import com.example.filmsearcher.adapters.FilmAdapter
import com.example.filmsearcher.data.Film
import com.example.filmsearcher.data.FilmService
import com.example.filmsearcher.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: FilmAdapter
    lateinit var filmList: List<Film>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        filmList = mutableListOf()
        adapter= FilmAdapter(filmList){id ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("FILM_ID", id)
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.searchbar_menu, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
               getFilmList(newText)
                adapter.updateItems(filmList)
                return true
            }
        })
        return true
    }
    fun getFilmList(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = FilmService.getInstance()
               filmList = service.getFilmSearch(query, "31b9190c").results
                Log.i("api", filmList.toString())
                CoroutineScope(Dispatchers.Main).launch {
                   adapter.updateItems(filmList)
                    binding.searchText.text= " "
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}