package com.example.filmsearcher.data

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmService {

    @GET("/")
    suspend fun getFilmSearch(@Query("s") query :String, @Query("apikey") token: String): FilmResponse
    @GET("/")
    suspend fun getFilmById(@Query("i") id: String, @Query("apikey") token: String): Film

    companion object{
        fun getInstance(): FilmService{
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(FilmService::class.java)

        }
    }
}