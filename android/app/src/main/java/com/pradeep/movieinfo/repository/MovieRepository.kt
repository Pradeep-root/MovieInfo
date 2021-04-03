package com.pradeep.movieinfo.repository

import com.pradeep.movieinfo.data.models.Movie
import com.pradeep.movieinfo.data.models.MovieResponse
import com.pradeep.movieinfo.util.Resource
import retrofit2.Response

interface MovieRepository {

    suspend fun getNowPlaying() : Resource<MovieResponse>

    suspend fun getPopularMovies(page : Int) : Response<MovieResponse>

    suspend fun getMovieDetail(movieId : Int) : Resource<Movie>
}