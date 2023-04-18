package com.example.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.network.NetworkState
import com.example.movieapp.ui.MoviesPagedListRepository
import io.reactivex.disposables.CompositeDisposable

class MoviesViewModel(private val moviesPagedListRepository: MoviesPagedListRepository) : ViewModel() {

    private val compositeDisposable =CompositeDisposable()

    val moviePagedList : LiveData<PagedList<Movie>> by lazy {
        moviesPagedListRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState : LiveData<NetworkState> by lazy {
        moviesPagedListRepository.getNetworkState()
    }

    fun listIsEmpty() : Boolean{
        return moviePagedList.value?.isEmpty()?:true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}