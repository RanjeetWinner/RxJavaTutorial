package com.example.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.model.MovieDetails
import com.example.movieapp.data.network.NetworkState
import com.example.movieapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class SingleMovieViewModel @Inject constructor(private val movieRepository: MovieRepository) :ViewModel() {

    private var movieId:Int=1
        fun setMovieId(movieId:Int){
            this.movieId=movieId
        }
    private val compositeDisposable= CompositeDisposable()

    val movieDetails :LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetail(compositeDisposable,movieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}