package com.example.movieapp.data.api

import com.example.movieapp.data.model.MovieDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    //https://api.themoviedb.org/3/movie/popular?api_key=<<api_key>>
    //https://api.themoviedb.org/3/movie/{movie_id}?api_key=<<api_key>>
    //https://api.themoviedb.org/3/


    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id")id:Int):Single<MovieDetails>
}