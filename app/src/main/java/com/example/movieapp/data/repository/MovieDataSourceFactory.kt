package com.example.movieapp.data.repository


import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movieapp.data.api.ApiService
import com.example.movieapp.data.model.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(private val apiService: ApiService,private val compositeDisposable: CompositeDisposable) :androidx.paging.DataSource.Factory<Int,Movie>() {
    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val movieDataSource=MovieDataSource(apiService,compositeDisposable)
        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}