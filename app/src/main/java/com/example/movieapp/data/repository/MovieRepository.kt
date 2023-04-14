package com.example.movieapp.data.repository

import androidx.lifecycle.LiveData
import com.example.movieapp.data.api.ApiService
import com.example.movieapp.data.model.MovieDetails
import com.example.movieapp.data.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieRepository(private val apiService: ApiService) {
    lateinit var apiDataSource: ApiDataSource

    fun fetchSingleMovieDetail(compositeDisposable: CompositeDisposable,movieId :Int) : LiveData<MovieDetails>{
        apiDataSource= ApiDataSource(apiService,compositeDisposable)
        apiDataSource.fetchMovieDetail(movieId)

        return apiDataSource.movieDetailResponse
    }


    fun getNetworkState() : LiveData<NetworkState>{
        return apiDataSource.networkState
    }
}