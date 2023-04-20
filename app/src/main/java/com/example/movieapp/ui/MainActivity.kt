package com.example.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.data.api.ApiClient
import com.example.movieapp.data.network.NetworkState
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MovieViewModel
    lateinit var movieRepository: MoviesPagedListRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        var view=binding.root
        setContentView(view)

        val apiService=ApiClient.getClient()
        movieRepository= MoviesPagedListRepository(apiService)

        viewModel=getViewModel()

        val movieAdapter=MoviePageListAdapter(this)
        val gridLayoutManager=GridLayoutManager(this,3)

        gridLayoutManager.spanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType=movieAdapter.getItemViewType(position)
                if(viewType ==  movieAdapter.MOVIE_VIEW_TYPE) return 1
                else return 3
            }
        }

        binding.rvMovieList.layoutManager=gridLayoutManager
        binding.rvMovieList.setHasFixedSize(true)
        binding.rvMovieList.adapter=movieAdapter


        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            binding.progressBarPopular.visibility = if(viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.txtErrorPopular.visibility = if(viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if(!viewModel.listIsEmpty()){
                movieAdapter.setNetworkState(it)
            }
        })


    }

    private fun getViewModel() : MovieViewModel {
        return ViewModelProvider(this,object :ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(movieRepository) as T
            }
        })[MovieViewModel::class.java]
    }
}