package com.example.filmsearcher.data

import com.google.gson.annotations.SerializedName

data class FilmResponse(
    @SerializedName("Search") val results: List<Film>
)

    data class Film (@SerializedName("imdbID") val id: String,
                 @SerializedName("Title") val title: String,
                 @SerializedName("Year") val year: String,
                 @SerializedName("Poster") val poster: String,
                 @SerializedName("Plot") val plot: String?,
                 @SerializedName("Runtime")val duration: String?,
                 @SerializedName("Director")val director: String?,
                 @SerializedName("Genre")val genre: String?,
                 @SerializedName("Country")val country: String?,
                )
