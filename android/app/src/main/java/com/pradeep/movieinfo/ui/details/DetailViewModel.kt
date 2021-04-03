package com.pradeep.movieinfo.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pradeep.movieinfo.data.models.Movie
import com.pradeep.movieinfo.repository.MovieRepository
import com.pradeep.movieinfo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
 class DetailViewModel @Inject constructor(
    val repositoryDefault: MovieRepository
) : ViewModel(){

    val movieDetails : MutableLiveData<Resource<Movie>> = MutableLiveData()

    fun getMovieDetails(movieId : Int) = viewModelScope.launch{
        movieDetails.postValue(Resource.loading())
        val response = repositoryDefault.getMovieDetail(movieId)
        movieDetails.postValue(response)
    }
}