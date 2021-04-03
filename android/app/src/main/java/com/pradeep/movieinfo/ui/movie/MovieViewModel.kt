package com.pradeep.movieinfo.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pradeep.movieinfo.data.models.MovieResponse
import com.pradeep.movieinfo.repository.MovieRepository
import com.pradeep.movieinfo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {


    var popularMoviePageNumber = 1
    private var popularMovieResponse : MovieResponse? = null

    private val _nowPlayingMovies = MutableLiveData<Resource<MovieResponse>>() // not accessible in activity
    val nowPlayingMovies : LiveData<Resource<MovieResponse>> = _nowPlayingMovies

    private val _popularMovies = MutableLiveData<Resource<MovieResponse>>() // not accessible in activity
    val popularMovies : LiveData<Resource<MovieResponse>> = _popularMovies



     fun getNowPlayingMovies() = viewModelScope.launch {
        _nowPlayingMovies.postValue(Resource.loading())
        val response = movieRepository.getNowPlaying()
        _nowPlayingMovies.postValue(response)
    }

    fun getPopularMovies() = viewModelScope.launch {
        _popularMovies.postValue(Resource.loading())
        try {
            val response = movieRepository.getPopularMovies(popularMoviePageNumber)
            if(response.isSuccessful){
                response.body()?.let { resultResponse ->
                    popularMoviePageNumber ++
                    if(popularMovieResponse == null){
                        popularMovieResponse = resultResponse
                    }else{
                        val oldResponse = popularMovieResponse?.results
                        val newResponse = resultResponse?.results
                        newResponse?.let { oldResponse?.addAll(it) }
                    }
                    _popularMovies.postValue(Resource.success(popularMovieResponse ?:resultResponse))
                }
            }else{
                _popularMovies.postValue(Resource.error(response.message(), null))
            }
        } catch (e : IOException){
            _popularMovies.postValue(Resource.error(e.message.toString(), null))
        }
    }

}