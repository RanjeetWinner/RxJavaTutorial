package com.example.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.api.ApiClient
import com.example.movieapp.data.api.ApiService
import com.example.movieapp.data.api.POSTER_BASE_URL
import com.example.movieapp.data.model.MovieDetails
import com.example.movieapp.data.network.NetworkState
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.databinding.ActivitySingleMovieBinding
import java.text.NumberFormat
import java.util.Locale


class SingleMovie : AppCompatActivity() {
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieRepository: MovieRepository
    private lateinit var binding: ActivitySingleMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySingleMovieBinding.inflate(layoutInflater)
        val view=binding.root
        //setContentView(R.layout.activity_single_movie)
        setContentView(view)
        val movieId:Int=intent.getIntExtra("id",1)

        val apiService:ApiService=ApiClient.getClient()
        movieRepository= MovieRepository(apiService)
        
        viewModel=getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            binding.progressBar.visibility = if(it== NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.txtError.visibility= if(it== NetworkState.ERROR) View.VISIBLE else View.GONE
        })

    }

    private fun bindUI(movieDetails: MovieDetails) {
        binding.movieTitle.text=movieDetails.title
        binding.movieTagline.text=movieDetails.tagline
        binding.movieReleaseDate.text=movieDetails.releaseDate
        binding.movieRating.text=movieDetails.rating.toString()
        binding.movieRuntime.text=movieDetails.runtime.toString()
        binding.movieOverview.text=movieDetails.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        binding.movieBudget.text=formatCurrency.format(movieDetails.budget)
        binding.movieRevenue.text=formatCurrency.format(movieDetails.revenue)


        val moviePosterUrl= POSTER_BASE_URL+movieDetails.posterPath
        Glide.with(this)
            .load(moviePosterUrl)
            .into(binding.ivMoviePoster)
       // movie_title.text=movieDetails.title
    }

    private fun getViewModel(movieId: Int): MovieViewModel {
        return ViewModelProvider(this,object :ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieViewModel(movieRepository,movieId) as T
            }
        })[MovieViewModel::class.java]
    }
}